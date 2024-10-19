package net.hypejet.jet.data.generator;

import com.google.gson.Gson;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.StringJoiner;

/**
 * Represents something that generates {@linkplain DataRegistryEntry data registry entries}.
 *
 * @since 1.0
 * @param <V> a type of values of the registry entries
 * @author Codestech
 * @see DataRegistryEntry
 */
public abstract class Generator<V> {

    private final GeneratorName generatorName;

    private final JavaFileSettings javaFileSettings;
    private final ResourceFileSettings resourceFileSettings;

    /**
     * Constructs the {@linkplain Generator generator}.
     *
     * @param generatorName a name of the generator
     * @param resourceFileSettings settings of resource file generation of this generator, {@code null} if this
     *                             generator should not generate a resource file
     * @param javaFileSettings settings of java file generation of this generator, {@code null} if this generator
     *                         should not generate a java file
     * @since 1.0
     */
    public Generator(@NonNull GeneratorName generatorName, @Nullable ResourceFileSettings resourceFileSettings,
                     @Nullable JavaFileSettings javaFileSettings) {
        this.generatorName = NullabilityUtil.requireNonNull(generatorName, "generator name");
        this.javaFileSettings = javaFileSettings;
        this.resourceFileSettings = resourceFileSettings;
    }

    /**
     * Gets a name of the generator.
     *
     * @return the name
     * @since 1.0
     */
    public @NonNull GeneratorName generatorName() {
        return this.generatorName;
    }

    /**
     * Gets settings of resource file generation of this generator.
     *
     * @return the settings, {@code null} if this generator should not generate a resource file
     * @since 1.0
     */
    public @Nullable ResourceFileSettings resourceFileSettings() {
        return this.resourceFileSettings;
    }

    /**
     * Gets settings of java file generation of this generator.
     *
     * @return the settings, {@code null} if this generator should not generate a java file
     * @since 1.0
     */
    public @Nullable JavaFileSettings javaFileSettings() {
        return this.javaFileSettings;
    }

    /**
     * Generates the {@linkplain DataRegistryEntry data registry entries}.
     *
     * <p>The registry entries keep their natural Minecraft order.</p>
     *
     * @return the registry entries
     * @since 1.0
     */
    public abstract @NonNull List<DataRegistryEntry<V>> generate();

    /**
     * Represents settings of a Java file generation of a {@linkplain Generator generator}.
     *
     * @param destination a destination of the file
     * @param className a name of the file/class
     * @since 1.0
     * @author Codestech
     */
    public record JavaFileSettings(ConstantContainer.@NonNull JavaFileDestination destination,
                                   @NonNull String className) {}

    /**
     * Represents settings of a resource file generation of a {@linkplain Generator generator}.
     *
     * @param fileName a name of the file
     * @param gson a gson instance, which serializes entries to a json string, which is put into the file
     * @author Codestech
     * @since 1.0
     */
    public record ResourceFileSettings(@NonNull String fileName, @NonNull Gson gson) {}

    /**
     * Represents a name of a generator.
     *
     * @param parts a separated parts of the name
     * @since 1.0
     * @author Codestech
     */
    public record GeneratorName(@NonNull List<String> parts) {
        /**
         * Constructs the name of a generator.
         *
         * @param parts a separated parts of the name
         * @since 1.0
         */
        public GeneratorName(@NonNull String @NonNull ... parts) {
            this(List.of(parts));
        }

        /**
         * Constructs the name of a generator.
         *
         * @param parts a separated parts of the name
         * @since 1.0
         */
        public GeneratorName {
            parts = List.copyOf(parts);
        }

        /**
         * Collects all parts to a {@linkplain String string} with a delimiter specified.
         *
         * @param delimiter the delimiter
         * @return the string
         * @since 1.0
         */
        public @NonNull String toString(@NonNull String delimiter) {
            StringJoiner joiner = new StringJoiner(delimiter);
            this.parts.forEach(joiner::add);
            return joiner.toString();
        }
    }
}