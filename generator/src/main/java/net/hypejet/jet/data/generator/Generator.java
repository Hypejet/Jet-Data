package net.hypejet.jet.data.generator;

import net.hypejet.jet.registry.RegistryEntry;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.Collection;

/**
 * Represents something that generates {@linkplain RegistryEntry registry entries}.
 *
 * @since 1.0
 * @param <V> a type of values of the registry entries
 * @author Codestech
 * @see RegistryEntry
 */
public abstract class Generator<V> {

    private final String resourceFileName;

    /**
     * Constructs the {@linkplain Generator generator}.
     *
     * @param resourceFileName a name of the resource file
     * @since 1.0
     */
    public Generator(@NonNull String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }

    /**
     * Gets a name of the resource file.
     *
     * @return the name
     * @since 1.0
     */
    public @NonNull String resourceFileName() {
        return resourceFileName;
    }

    /**
     * Generates the {@linkplain RegistryEntry registry entries}.
     *
     * @param logger logger to log events with
     * @return the registry entries
     * @since 1.0
     */
    public abstract @NonNull Collection<? extends RegistryEntry<V>> generate(@NonNull Logger logger);
}