package net.hypejet.jet.data.generator;

import com.mojang.logging.LogUtils;
import net.hypejet.jet.data.generator.generators.VanillaBiomeGenerator;
import net.hypejet.jet.data.json.JetDataJson;
import net.hypejet.jet.registry.RegistryEntry;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.server.Bootstrap;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Set;

/**
 * Represents a class running all {@linkplain Generator vanilla data generators}.
 *
 * @since 1.0
 * @author Codestech
 * @see Generator
 */
public final class GeneratorEntrypoint {

    private static final String JSON_FILE_SUFFIX = ".json";

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

        HolderLookup.Provider lookupProvider = VanillaRegistries.createLookup();
        Set<Generator<?>> generators = Set.of(new VanillaBiomeGenerator(lookupProvider));

        Logger logger = LogUtils.getLogger();
        Path resourcesPath = Path.of(args[0]);

        logger.info("Starting generation...");

        for (Generator<?> generator : generators) {
            String generatorName = generator.getClass().getSimpleName();

            try {
                logger.info("Generating registry entries using \"{}\"...", generatorName);
                Collection<? extends RegistryEntry<?>> entries = generator.generate(logger);

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