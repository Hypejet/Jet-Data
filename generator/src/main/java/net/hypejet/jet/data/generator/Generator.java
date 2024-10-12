package net.hypejet.jet.data.generator;

import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * Represents something that generates {@linkplain DataRegistryEntry data registry entries}.
 *
 * @since 1.0
 * @param <V> a type of values of the registry entries
 * @author Codestech
 * @see DataRegistryEntry
 */
public abstract class Generator<V> {

    private final String resourceFileName;
    private final String className;

    private final boolean createEntryKeyConstants;

    /**
     * Constructs the {@linkplain Generator generator}.
     *
     * @param resourceFileName a name of the resource file
     * @param className a name of a java class, which should be generated using data from this generator
     * @param createEntryKeyConstants whether a constant should be created for each entry generated
     * @since 1.0
     */
    public Generator(@NonNull String resourceFileName, @NonNull String className, boolean createEntryKeyConstants) {
        this.resourceFileName = resourceFileName;
        this.className = className;
        this.createEntryKeyConstants = createEntryKeyConstants;
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
     * Gets whether a constant should be created for each entry generated.
     *
     * @return {@code true} if a constant should be created for each entry generated, {@code false} otherwise
     * @since 1.0
     */
    public boolean createEntryKeyConstants() {
        return this.createEntryKeyConstants;
    }

    /**
     * Generates the {@linkplain DataRegistryEntry data registry entries}.
     *
     * <p>The registry entries keep their natural Minecraft order.</p>
     *
     * @return the registry entries
     * @since 1.0
     */
    public abstract @NonNull List<? extends DataRegistryEntry<V>> generate();
}