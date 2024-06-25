package net.hypejet.jet.data.generator;

import com.mojang.logging.LogUtils;
import com.squareup.javapoet.JavaFile;
import net.hypejet.jet.data.generator.generators.VanillaBlockGenerator;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

public final class GeneratorEntrypoint {

    private static final Collection<Generator> GENERATORS = Set.of(new VanillaBlockGenerator());

    public static void main(String[] args) {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();

        Logger logger = LogUtils.getLogger();
        Path rootGenerationPath = Path.of(args[0]);

        logger.info("Starting generation...");

        for (Generator generator : GENERATORS) {
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