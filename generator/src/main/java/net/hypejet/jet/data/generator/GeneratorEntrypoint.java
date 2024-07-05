package net.hypejet.jet.data.generator;

import com.mojang.logging.LogUtils;
import com.squareup.javapoet.JavaFile;
import net.hypejet.jet.data.generator.generators.BuiltInBlockGenerator;
import net.hypejet.jet.data.generator.generators.BuiltInEntityAttachmentTypeGenerator;
import net.hypejet.jet.data.generator.generators.BuiltInEntityCategoryGenerator;
import net.hypejet.jet.data.generator.generators.BuiltInEntityTypeGenerator;
import net.hypejet.jet.data.generator.generators.BuiltInFeatureFlagsGenerator;
import net.hypejet.jet.data.generator.generators.biome.BuiltInBiomeGenerator;
import net.hypejet.jet.data.generator.generators.biome.BuiltInGrassColorModifierGenerator;
import net.hypejet.jet.data.generator.generators.biome.BuiltInTemperatureModifierGenerator;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.server.Bootstrap;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Set;

/**
 * Represents a class running all {@linkplain Generator vanilla data generators}.
 *
 * @since 1.0
 * @author Codestech
 * @see Generator
 */
public final class GeneratorEntrypoint {

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

        Set<Generator> generators = Set.of(new BuiltInBlockGenerator(), new BuiltInEntityTypeGenerator(),
                new BuiltInEntityCategoryGenerator(), new BuiltInFeatureFlagsGenerator(),
                new BuiltInEntityAttachmentTypeGenerator(), new BuiltInGrassColorModifierGenerator(),
                new BuiltInTemperatureModifierGenerator(),
                new BuiltInBiomeGenerator(lookupProvider.lookupOrThrow(Registries.BIOME)));

        Logger logger = LogUtils.getLogger();
        Path rootGenerationPath = Path.of(args[0]);

        logger.info("Starting generation...");

        for (Generator generator : generators) {
            String generatorName = generator.getClass().getSimpleName();

            logger.info("Generating a java file using \"{}\"...", generatorName);
            JavaFile file = generator.generate(logger);

            try {
                logger.info("Writing the java file...");
                file.writeTo(rootGenerationPath);
            } catch (Throwable throwable) {
                logger.error("An error occurred during generation using: {}", generatorName, throwable);
            }

            logger.info("Generation using \"{}\" has completed successfully!", generatorName);
        }

        logger.info("Generation complete!");
    }
}