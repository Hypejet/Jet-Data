package net.hypejet.jet.data.generator;

import com.mojang.logging.LogUtils;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.constant.Constant;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.generators.api.ArmorTrimMaterialGenerator;
import net.hypejet.jet.data.generator.generators.api.ArmorTrimPatternGenerator;
import net.hypejet.jet.data.generator.generators.api.BannerPatternGenerator;
import net.hypejet.jet.data.generator.generators.api.BiomeGenerator;
import net.hypejet.jet.data.generator.generators.api.ChatTypeGenerator;
import net.hypejet.jet.data.generator.generators.api.DamageTypeGenerator;
import net.hypejet.jet.data.generator.generators.api.DimensionTypeGenerator;
import net.hypejet.jet.data.generator.generators.api.PaintingVariantGenerator;
import net.hypejet.jet.data.generator.generators.api.WolfVariantGenerator;
import net.hypejet.jet.data.generator.generators.server.BlockGenerator;
import net.hypejet.jet.data.generator.generators.server.BlockStateGenerator;
import net.hypejet.jet.data.generator.generators.server.DataPackGenerator;
import net.hypejet.jet.data.generator.util.CodeBlocks;
import net.hypejet.jet.data.model.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import net.minecraft.SharedConstants;
import net.minecraft.WorldVersion;
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
import net.minecraft.tags.TagLoader;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.validation.DirectoryValidator;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
    private static final String JAVA_FILE_INDENT = "    "; // 4 spaces

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
        PackRepository repository = new PackRepository(new ServerPacksSource(new DirectoryValidator(path -> false)));

        MinecraftServer.configurePackRepository(repository,
                new WorldDataConfiguration(DataPackConfig.DEFAULT, FeatureFlags.REGISTRY.allFlags()),
                false, true);

        List<PackResources> packResources = repository.openAllSelected();
        CloseableResourceManager resourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, packResources);

        LayeredRegistryAccess<RegistryLayer> layeredAccess = rootAccess.replaceFrom(
                RegistryLayer.WORLDGEN,
                RegistryDataLoader.load(resourceManager,
                        TagLoader.buildUpdatedLookups(
                                rootAccess.getAccessForLoading(RegistryLayer.WORLDGEN),
                                TagLoader.loadTagsForExistingRegistries(
                                        resourceManager,
                                        rootAccess.getLayer(RegistryLayer.STATIC)
                                )),
                        RegistryDataLoader.WORLDGEN_REGISTRIES)
        );

        RegistryAccess.Frozen frozenAccess = layeredAccess.compositeAccess();

        Logger logger = LogUtils.getLogger();
        logger.info("Starting generation...");

        Set<ConstantContainer> serverConstantContainers = new HashSet<>();

        WorldVersion versionInfo = SharedConstants.getCurrentVersion();
        serverConstantContainers.add(new ConstantContainer(
                "MinecraftVersionInfo",
                CodeBlock.of("Represents a holder of Minecraft version information."),
                List.of(new Constant("VERSION_NAME", String.class,
                                CodeBlock.of("$S", versionInfo.getName()),
                                CodeBlock.of("A name of the Minecraft version.")),
                        new Constant("PROTOCOL_VERSION", int.class,
                                CodeBlock.of("$L", versionInfo.getProtocolVersion()),
                                CodeBlock.of("A numeric version of the Minecraft protocol.")))
        ));

        Set<Generator<?>> apiGenerators = Set.of(new BiomeGenerator(frozenAccess),
                new DimensionTypeGenerator(frozenAccess), new ChatTypeGenerator(frozenAccess),
                new DamageTypeGenerator(frozenAccess), new WolfVariantGenerator(frozenAccess),
                new PaintingVariantGenerator(frozenAccess), new ArmorTrimPatternGenerator(frozenAccess),
                new ArmorTrimMaterialGenerator(frozenAccess), new BannerPatternGenerator(frozenAccess));
        Set<Generator<?>> serverGenerators = Set.of(new DataPackGenerator(repository), new BlockStateGenerator(),
                new BlockGenerator());

        generate(apiGenerators, Set.of(), Path.of(args[0]), Path.of(args[1]), "api");
        generate(serverGenerators, serverConstantContainers, Path.of(args[2]), Path.of(args[3]), "server");

        logger.info("Generation complete!");
    }

    private static void generate(@NonNull Collection<Generator<?>> generators,
                                 @NonNull Collection<ConstantContainer> defaultConstantContainers,
                                 @NonNull Path resourcePath, @NonNull Path javaPath, @NonNull String subpackage) {
        Set<ConstantContainer> constantContainers = new HashSet<>(defaultConstantContainers);

        generators.forEach(generator -> {
            List<? extends DataRegistryEntry<?>> entries = generator.generate();
            String json = JetDataJson.serialize(entries);

            try {
                Files.createDirectories(resourcePath);

                String resourceFileName = generator.resourceFileName() + JSON_FILE_SUFFIX;
                Path resourceFilePath = resourcePath.resolve(resourceFileName);

                Files.deleteIfExists(resourceFilePath);
                Files.writeString(resourceFilePath, json, StandardOpenOption.CREATE);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            String className = generator.className();
            if (className == null) return;

            String generatorName = generator.getClass().getSimpleName();
            List<Constant> constants = new ArrayList<>();

            entries.forEach(entry -> {
                Key key = entry.key();
                constants.add(new Constant(
                        createFieldName(key), Key.class, CodeBlocks.key(key),
                        CodeBlock.of(String.format("Represents an identifier of \"%s\" registry entry.", key.value()))
                ));
            });

            constantContainers.add(new ConstantContainer(className,
                    CodeBlock.of(String.format("Represents a holder of keys of registry entries generated" +
                            " by a \"%s\" generator.", generatorName)),
                    List.copyOf(constants)));
        });

        for (ConstantContainer container : constantContainers) {
            try {
                JavaFile.builder("net.hypejet.jet.data.generated." + subpackage, container.toTypeSpec())
                        .indent(JAVA_FILE_INDENT)
                        .build()
                        .writeTo(javaPath);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
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