package net.hypejet.jet.data.generator.util;

import com.squareup.javapoet.CodeBlock;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a builder of a javadoc.
 *
 * @since 1.0
 * @author Codestech
 */
public final class JavaDocBuilder {

    private final List<CodeBlock> blocks = new ArrayList<>();

    private JavaDocBuilder() {}

    /**
     * Sets a value of a field of the javadoc.
     *
     * @param field the field
     * @param value the value
     * @return this builder
     * @since 1.0
     */
    public @NonNull JavaDocBuilder field(@NonNull String field, @NonNull String value) {
        return this.append(String.format("@%s %s", field, value));
    }

    /**
     * Appends an empty line to the javadoc.
     *
     * @return this builder
     * @since 1.0
     */
    public @NonNull JavaDocBuilder appendEmpty() {
        return this.append("");
    }

    /**
     * Appends a plain text to the javadoc.
     *
     * @param text the text
     * @param args arguments to be applied to the text
     * @return this builder
     * @since 1.0
     */
    public @NonNull JavaDocBuilder append(@NonNull String text, @NonNull Object @NonNull ... args) {
        this.blocks.add(CodeBlock.of(text, args));
        return this;
    }

    /**
     * Builds a {@linkplain CodeBlock code block} containing javadoc made using this builder.
     *
     * @return the code block
     * @since 1.0
     */
    public @NonNull CodeBlock build() {
        return CodeBlock.join(this.blocks, "\n");
    }

    /**
     * Creates a {@linkplain JavaDocBuilder javadoc builder}.
     *
     * @return the javadoc builder
     * @since 1.0
     */
    public static @NonNull JavaDocBuilder builder() {
        return new JavaDocBuilder();
    }
}