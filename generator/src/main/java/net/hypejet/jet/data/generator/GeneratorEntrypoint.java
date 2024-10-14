package net.hypejet.jet.data.generator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mojang.logging.LogUtils;
import com.squareup.javapoet.CodeBlock;
import net.hypejet.jet.data.generator.constant.Constant;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.generators.*;
import net.hypejet.jet.data.generator.util.CodeBlocks;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import net.minecraft.SharedConstants;
import net.minecraft.WorldVersion;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
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

    private static final String API_PACKAGE = "net.hypejet.jet.data.autogenerated.api";
    private static final String SERVER_SUBPACKAGE = "net.hypejet.jet.data.autogenerated.server";

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

        Logger logger = LogUtils.getLogger();
        logger.info("Starting generation...");

        Set<ConstantContainer> defaultConstantContainers = new HashSet<>();

        WorldVersion versionInfo = SharedConstants.getCurrentVersion();
        defaultConstantContainers.add(new ConstantContainer(
                "MinecraftVersionInfo",
                CodeBlock.of("Represents a holder of Minecraft version information."),
                List.of(new Constant("VERSION_NAME", String.class,
                                CodeBlocks.string(versionInfo.getName()),
                                CodeBlock.of("A name of the Minecraft version.")),
                        new Constant("PROTOCOL_VERSION", int.class,
                                CodeBlock.of(String.valueOf(versionInfo.getProtocolVersion())),
                                CodeBlock.of("A numeric version of the Minecraft protocol."))),
                ConstantContainer.JavaFileDestination.API
        ));

        PackRepository packs = new PackRepository(new ServerPacksSource(new DirectoryValidator(path -> false)));
        RegistryAccess registryAccess = createRegistryAccess(packs);

        Set<Generator<?>> generators = Set.of(new BiomeGenerator(registryAccess),
                new DimensionTypeGenerator(registryAccess), new ChatTypeGenerator(registryAccess),
                new DamageTypeGenerator(registryAccess), new WolfVariantGenerator(registryAccess),
                new PaintingVariantGenerator(registryAccess), new ArmorTrimPatternGenerator(registryAccess),
                new ArmorTrimMaterialGenerator(registryAccess), new BannerPatternGenerator(registryAccess),
                new FeaturePackGenerator(packs), new BlockGenerator(registryAccess), new ItemGenerator(registryAccess),
                new GameEventGenerator(registryAccess), new BlockStateGenerator());

        Path resourceDirectoryPath = Path.of(args[0]);
        Path apiJavaDirectoryPath = Path.of(args[1]);
        Path serverJavaDirectoryPath = Path.of(args[2]);

        Set<ConstantContainer> constantContainers = new HashSet<>(defaultConstantContainers);
        Set<Constant> resourceFileNamesConstants = new HashSet<>();

        generators.forEach(generator -> {
            List<? extends DataRegistryEntry<?>> entries = generator.generate();

            Generator.ResourceFileSettings resourceFileSettings = generator.resourceFileSettings();
            if (resourceFileSettings != null) {
                Gson gson = resourceFileSettings.gson();
                JsonArray array = new JsonArray();
                entries.forEach(element -> array.add(gson.toJsonTree(element)));

                String json = gson.toJson(array);
                String resourceFileName;

                try {
                    Files.createDirectories(resourceDirectoryPath);

                    resourceFileName = resourceFileSettings.fileName() + JSON_FILE_SUFFIX;
                    Path resourceFilePath = resourceDirectoryPath.resolve(resourceFileName);

                    Files.deleteIfExists(resourceFilePath);
                    Files.writeString(resourceFilePath, json, StandardOpenOption.CREATE);
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }

                resourceFileNamesConstants.add(new Constant(
                        generator.generatorName().toString(UNALLOWED_CHARACTER_REPLACEMENT).toUpperCase(),
                        String.class, CodeBlocks.string(resourceFileName),
                        CodeBlock.of(String.format(
                                "A name of a resource file, which contains entries generated by \"%s\" generator",
                                generator.generatorName().toString(" "))
                        ))
                );
            }

            Generator.JavaFileSettings javaFileSettings = generator.javaFileSettings();
            if (javaFileSettings != null) {
                List<Constant> constants = new ArrayList<>();

                entries.forEach(entry -> {
                    Key key = entry.key();
                    constants.add(new Constant(
                            createFieldName(key.value()), Key.class, CodeBlocks.key(key),
                            CodeBlock.of(String.format(
                                    "Represents an identifier of \"%s\" registry entry.", key.value()
                            ))
                    ));
                });

                constantContainers.add(new ConstantContainer(
                        javaFileSettings.className(),
                        CodeBlock.of(String.format("Represents a holder of keys of registry entries generated by" +
                                " a \"%s\" generator.", generator.generatorName().toString(" "))),
                        List.copyOf(constants),
                        javaFileSettings.destination()
                ));
            }
        });

        constantContainers.add(new ConstantContainer(
                "ResourceFileNames",
                CodeBlock.of("Represents a holder of names of resource files, which contain entries generated by" +
                        " generators."),
                resourceFileNamesConstants,
                ConstantContainer.JavaFileDestination.SERVER)
        );

        for (ConstantContainer container : constantContainers) {
            try {
                String packageName = null;
                Path javaPath = null;

                switch (container.destination()) {
                    case API -> {
                        packageName = API_PACKAGE;
                        javaPath = apiJavaDirectoryPath;
                    }
                    case SERVER -> {
                        packageName = SERVER_SUBPACKAGE;
                        javaPath = serverJavaDirectoryPath;
                    }
                }

                container.toJavaFile(packageName).writeTo(javaPath);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }

        logger.info("Generation complete!");
    }

    private static @NonNull RegistryAccess createRegistryAccess(@NonNull PackRepository packRepository) {
        LayeredRegistryAccess<RegistryLayer> access = RegistryLayer.createRegistryAccess();
        MinecraftServer.configurePackRepository(packRepository,
                new WorldDataConfiguration(DataPackConfig.DEFAULT, FeatureFlags.REGISTRY.allFlags()),
                false, true);

        List<PackResources> packResources = packRepository.openAllSelected();
        CloseableResourceManager resourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, packResources);

        List<Registry.PendingTags<?>> pendingTags = TagLoader.loadTagsForExistingRegistries(resourceManager,
                access.getLayer(RegistryLayer.STATIC));

        // Apply pending tags that have not been already applied
        pendingTags.forEach(Registry.PendingTags::apply);

        RegistryAccess.Frozen loadedRegistryData = RegistryDataLoader.load(resourceManager,
                TagLoader.buildUpdatedLookups(access.getAccessForLoading(RegistryLayer.WORLDGEN), pendingTags),
                RegistryDataLoader.WORLDGEN_REGISTRIES);
        return access.replaceFrom(RegistryLayer.WORLDGEN, loadedRegistryData).compositeAccess();
    }

    private static @NonNull String createFieldName(@NonNull String string) {
        String upperCaseValue = string.toUpperCase();
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < upperCaseValue.length(); index++) {
            char character = upperCaseValue.charAt(index);
            boolean javaIdentifierPart = Character.isJavaIdentifierPart(character);

            String name;

            if (index == 0 && !Character.isJavaIdentifierStart(character) && javaIdentifierPart)
                name = UNALLOWED_CHARACTER_REPLACEMENT + character;
            else if (!javaIdentifierPart)
                name = UNALLOWED_CHARACTER_REPLACEMENT;
            else
                name = String.valueOf(character);

            builder.append(name);
        }

        return builder.toString();
    }
}