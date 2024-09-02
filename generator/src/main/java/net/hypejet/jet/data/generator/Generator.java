package net.hypejet.jet.data.generator;

import net.hypejet.jet.data.model.registry.RegistryEntry;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.List;

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
    private final String className;

    /**
     * Constructs the {@linkplain Generator generator}.
     *
     * @param resourceFileName a name of the resource file
     * @param className a name of class, which contains identifiers of registry entries generated using the generator
     * @since 1.0
     */
    public Generator(@NonNull String resourceFileName, @NonNull String className) {
        this.resourceFileName = resourceFileName;
        this.className = className;
    }

    /**
     * Gets a name of the resource file.
     *
     * @return the name
     * @since 1.0
     */
    public @NonNull String resourceFileName() {
        return this.resourceFileName;
    }

    /**
     * Gets a name of class, which contains identifiers of registry entries generated using the generator.
     *
     * @return the name
     * @since 1.0
     */
    public @NonNull String className() {
        return this.className;
    }

    /**
     * Generates the {@linkplain RegistryEntry registry entries}.
     *
     * <p>The registry entries keep their natural Minecraft order.</p>
     *
     * @param logger logger to log events with
     * @return the registry entries
     * @since 1.0
     */
    public abstract @NonNull List<? extends RegistryEntry<V>> generate(@NonNull Logger logger);
}