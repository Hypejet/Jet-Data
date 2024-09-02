package net.hypejet.jet.data.generator;

import com.mojang.logging.LogUtils;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import net.hypejet.jet.data.generator.generators.ArmorTrimPatternGenerator;
import net.hypejet.jet.data.generator.generators.BiomeGenerator;
import net.hypejet.jet.data.generator.generators.ChatTypeGenerator;
import net.hypejet.jet.data.generator.generators.DamageTypeGenerator;
import net.hypejet.jet.data.generator.generators.DimensionTypeGenerator;
import net.hypejet.jet.data.generator.generators.PaintingVariantGenerator;
import net.hypejet.jet.data.generator.generators.WolfVariantGenerator;
import net.hypejet.jet.data.generator.util.CodeBlocks;
import net.hypejet.jet.data.generator.util.JavaDocBuilder;
import net.hypejet.jet.data.generator.util.JavaFileUtil;
import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.model.registry.RegistryEntry;
import net.kyori.adventure.key.Key;
import net.minecraft.SharedConstants;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.validation.DirectoryValidator;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import javax.lang.model.element.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a class running all {@linkplain Generator vanilla data generators}.
 *
 * @author Codestech
 * @see Generator
 * @since 1.0
 */
public final class GeneratorEntrypoint {

    private static final String JSON_FILE_SUFFIX = ".json";
    private static final String UNALLOWED_CHARACTER_REPLACEMENT = "_";

    private GeneratorEntrypoint() {}

    /**
     * Runs all registered {@linkplain Generator vanilla data generators}.
     *
     * @param args arguments of the application execution
     * @since 1.0
     */
    public static void main(String[] args) {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();

        LayeredRegistryAccess<RegistryLayer> rootAccess = RegistryLayer.createRegistryAccess();
        PackRepository repository = new PackRepository(new ServerPacksSource(new DirectoryValidator(path -> true)));

        MinecraftServer.configurePackRepository(repository,
                new WorldDataConfiguration(DataPackConfig.DEFAULT, FeatureFlags.REGISTRY.allFlags()),
                false, true);

        List<PackResources> packResources = repository.openAllSelected();
        CloseableResourceManager resourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, packResources);

        LayeredRegistryAccess<RegistryLayer> layeredAccess = rootAccess.replaceFrom(
                RegistryLayer.WORLDGEN,
                RegistryDataLoader.load(resourceManager,
                        rootAccess.getAccessForLoading(RegistryLayer.WORLDGEN),
                        RegistryDataLoader.WORLDGEN_REGISTRIES)
        );

        RegistryAccess.Frozen frozenAccess = layeredAccess.compositeAccess();
        Set<Generator<?>> generators = Set.of(new BiomeGenerator(frozenAccess),
                new DimensionTypeGenerator(frozenAccess), new ChatTypeGenerator(frozenAccess),
                new DamageTypeGenerator(frozenAccess), new WolfVariantGenerator(frozenAccess),
                new PaintingVariantGenerator(frozenAccess), new ArmorTrimPatternGenerator(frozenAccess));

        Logger logger = LogUtils.getLogger();

        Path resourcesPath = Path.of(args[0]);
        Path javaPath = Path.of(args[1]);

        logger.info("Starting generation...");

        for (Generator<?> generator : generators) {
            String generatorName = generator.getClass().getSimpleName();

            try {
                logger.info("Generating registry entries using \"{}\"...", generatorName);
                List<? extends RegistryEntry<?>> entries = generator.generate(logger);

                logger.info("Converting the generated registry entries to a string...");
                String json = JetDataJson.serialize(entries);

                logger.info("Writing the resource file...");
                Files.createDirectories(resourcesPath);

                Path resourceFilePath = resourcesPath.resolve(generator.resourceFileName() + JSON_FILE_SUFFIX);
                Files.deleteIfExists(resourceFilePath);
                Files.writeString(resourceFilePath, json, StandardOpenOption.CREATE);

                logger.info("Creating a java class with identifiers of registry entries...");

                List<FieldSpec> specs = new ArrayList<>();
                for (RegistryEntry<?> entry : entries) {
                    Key key = entry.key();
                    specs.add(FieldSpec.builder(Key.class, createFieldName(key))
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            .initializer(CodeBlocks.key(entry.key()))
                            .addJavadoc(JavaDocBuilder.builder()
                                    .append(String.format("Represents an identifier of a \"%s\" registry entry.",
                                            key.value()))
                                    .appendEmpty()
                                    .build())
                            .build());
                }

                TypeSpec spec = TypeSpec.classBuilder(generator.className())
                        .addFields(specs)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addJavadoc(JavaDocBuilder.builder()
                                .append(String.format("Represents a holder of keys of registry entries" +
                                        " generated by a \"%s\" generator.", generatorName))
                                .appendEmpty()
                                .build())
                        .addMethod(MethodSpec.constructorBuilder()
                                .addModifiers(Modifier.PRIVATE)
                                .build())
                        .build();

                logger.info("Writing the created java class...");

                JavaFile file = JavaFile.builder("net.hypejet.jet.data.generated", spec)
                        .indent(JavaFileUtil.INDENT)
                        .build();
                file.writeTo(javaPath);

                logger.info("Generation using \"{}\" has completed successfully!", generatorName);
            } catch (Throwable throwable) {
                logger.error("An error occurred during generation using: {}", generatorName, throwable);
            }
        }

        logger.info("Generation complete!");
    }

    private static @NonNull String createFieldName(@NonNull Key key) {
        String upperCaseKeyValue = key.value().toUpperCase();
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < upperCaseKeyValue.length(); index++) {
            char character = upperCaseKeyValue.charAt(index);
            boolean javaIdentifierPart = Character.isJavaIdentifierPart(character);

            String string;

            if (index == 0 && !Character.isJavaIdentifierStart(character) && javaIdentifierPart)
                string = UNALLOWED_CHARACTER_REPLACEMENT + character;
            else if (!javaIdentifierPart)
                string = UNALLOWED_CHARACTER_REPLACEMENT;
            else
                string = String.valueOf(character);

            builder.append(string);
        }

        return builder.toString();
    }
}