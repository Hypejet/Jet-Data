package net.hypejet.jet.data.generator.util;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Represents a utility for creating various {@linkplain CodeBlock code blocks}.
 *
 * @since 1.0
 * @author Codestech
 * @see CodeBlock
 */
public final class CodeBlocks {

    private static final String TYPE_REFERENCE = "$T";
    private static final String STRING = "$S";
    private static final String LITERAL = "$L";

    private CodeBlocks() {}

    /**
     * Creates a {@linkplain CodeBlock code block}, which creates a {@linkplain Key key} using data in a key specified.
     *
     * @param key the key
     * @return the code block
     */
    public static @NonNull CodeBlock key(@NonNull Key key) {
        NullabilityUtil.requireNonNull(key, "key");
        return staticMethodInvocation(Key.class, "key", string(key.asString()));
    }

    /**
     * Creates a {@linkplain CodeBlock code block} invoking a static method.
     *
     * @param type a type, which own the method
     * @param name a name of the method
     * @param arguments arguments, which should be applied to the method
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock staticMethodInvocation(@NonNull Type type, @NonNull String name,
                                                            @NonNull CodeBlock @NonNull ... arguments) {
        return staticMethodInvocation(TypeName.get(NullabilityUtil.requireNonNull(type, "type")), name, arguments);
    }

    /**
     * Creates a {@linkplain CodeBlock code block} invoking a static method.
     *
     * @param type a name of type, which own the method
     * @param name a name of the method
     * @param arguments arguments, which should be applied to the method
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock staticMethodInvocation(@NonNull TypeName type, @NonNull String name,
                                                            @NonNull CodeBlock @NonNull ... arguments) {
        NullabilityUtil.requireNonNull(type, "type");
        NullabilityUtil.requireNonNull(name, "name");
        NullabilityUtil.requireNonNull(arguments, "arguments");
        CodeBlock argumentBlock = CodeBlock.join(Arrays.asList(arguments), ", ");
        return CodeBlock.of(TYPE_REFERENCE + "." + name + "(" + LITERAL + ")", type, argumentBlock);
    }

    /**
     * Creates a {@linkplain CodeBlock code block} creating a {@linkplain String string}.
     *
     * @param string the string
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock string(@NonNull String string) {
        return CodeBlock.of(STRING, NullabilityUtil.requireNonNull(string, "string"));
    }
}