package net.hypejet.jet.data.generator;

import com.mojang.logging.LogUtils;
import net.hypejet.jet.data.generator.generators.BiomeGenerator;
import net.hypejet.jet.data.json.JetDataJson;
import net.hypejet.jet.registry.RegistryEntry;
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
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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

    private GeneratorEntrypoint() {
    }

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
        Set<Generator<?>> generators = Set.of(new BiomeGenerator(frozenAccess));

        Logger logger = LogUtils.getLogger();
        Path resourcesPath = Path.of(args[0]);

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
                logger.info("Generation using \"{}\" has completed successfully!", generatorName);
            } catch (Throwable throwable) {
                logger.error("An error occurred during generation using: {}", generatorName, throwable);
            }
        }

        logger.info("Generation complete!");
    }
}