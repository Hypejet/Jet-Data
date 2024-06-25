package net.hypejet.jet.data.generator.util;

import com.squareup.javapoet.CodeBlock;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a utility generating {@linkplain CodeBlock code block} containing javadocs.
 *
 * @since 1.0
 * @author Codestech
 * @see CodeBlock
 */
public final class JavaDocBuilder {
    
    private final List<CodeBlock> codeBlocks = new ArrayList<>();
    
    private JavaDocBuilder() {}

    /**
     * Appends an empty line.
     *
     * @return this builder
     * @since 1.0
     */
    public @NonNull JavaDocBuilder emptyLine() {
        return this.line("");
    }

    /**
     * Appends a paragraph.
     *
     * @param paragraph the paragraph
     * @return this builder
     * @since 1.0
     */
    public @NonNull JavaDocBuilder paragraph(@NonNull String paragraph) {
        return this.line("<p>" + paragraph + "</p>");
    }

    /**
     * Appends a line.
     *
     * @param line the line
     * @return this builder
     * @since 1.0
     */
    public @NonNull JavaDocBuilder line(@NonNull String line) {
        this.codeBlocks.add(CodeBlock.of(line));
        return this;
    }

    /**
     * Appends a block containing a version, since which something that this javadoc should define is available.
     *
     * @param version the version
     * @return this builder
     * @since 1.0
     */
    public @NonNull JavaDocBuilder since(@NonNull String version) {
        this.codeBlocks.add(CodeBlock.of("@since " + version));
        return this;
    }

    /**
     * Appends a {@code see} block referencing to a type.
     *
     * @param type the type
     * @return this builder
     * @since 1.0
     */
    public @NonNull JavaDocBuilder see(@NonNull Type type) {
        this.codeBlocks.add(CodeBlock.of("@see " + CodeBlocks.TYPE_SPEC, type));
        return this;
    }

    /**
     * Appends a {@code return} block.
     *
     * @param what a description of the return value
     * @return this builder
     * @since 1.0
     */
    public @NonNull JavaDocBuilder returns(@NonNull String what) {
        this.codeBlocks.add(CodeBlock.of("@return " + what));
        return this;
    }

    /**
     * Builds a {@linkplain CodeBlock code block} using data from this builder.
     *
     * @return the code block
     * @since 1.0
     */
    public @NonNull CodeBlock build() {
        return CodeBlock.join(this.codeBlocks, "\n");
    }

    /**
     * Creates the {@linkplain JavaDocBuilder javadoc builder}.
     *
     * @return the javadoc builder
     * @since 1.0
     */
    public static @NonNull JavaDocBuilder builder() {
        return new JavaDocBuilder();
    }
}