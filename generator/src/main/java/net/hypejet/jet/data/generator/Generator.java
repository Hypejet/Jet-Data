package net.hypejet.jet.data.generator;

import com.squareup.javapoet.JavaFile;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

/**
 * Represents something that generates a {@linkplain JavaFile java file}.
 *
 * @since 1.0
 * @author Codestech
 * @see JavaFile
 */
public interface Generator {
    /**
     * Generates a {@linkplain JavaFile java file}.
     *
     * @param logger logger to log errors with
     * @return the java file
     * @since 1.0
     */
    @NonNull JavaFile generate(@NonNull Logger logger);
}