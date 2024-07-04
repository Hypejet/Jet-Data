package net.hypejet.jet.data.generator.util;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Represents a utility for creating {@linkplain CodeBlock code blocks}.
 *
 * @since 1.0
 * @author Codestech
 * @see CodeBlock
 */
public final class CodeBlocks {

    static final String TYPE_SPEC = "$T";
    static final String LITERAL_SPEC = "$L";
    static final String STRING_SPEC = "$S";

    private CodeBlocks() {}

    /**
     * Creates a code block creating a {@linkplain Key key} using data from a {@linkplain ResourceLocation resource
     * location}.
     *
     * @param location the resource location
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock keyCreator(@NonNull ResourceLocation location) {
        return staticMethodInvocation(Key.class, "key", string(location.getNamespace()), string(location.getPath()));
    }

    /**
     * Creates a code block creating a {@linkplain Set set} using {@linkplain Set#of(Object[])}.
     *
     * @param blocks the code blocks defining contents of the set
     * @return the code blocks
     * @since 1.0
     */
    public static @NonNull CodeBlock setCreator(@NonNull Object @NonNull ... blocks) {
        return staticMethodInvocation(Set.class, "of", blocks);
    }

    public static @NonNull CodeBlock list(@NonNull Object @NonNull ... objects) {
        return staticMethodInvocation(List.class, "of", objects);
    }

    public static @NonNull CodeBlock map(@NonNull Object @NonNull ... entries) {
        return staticMethodInvocation(Map.class, "ofEntries", entries);
    }

    public static @NonNull CodeBlock mapEntry(@NonNull Object key, @NonNull Object value) {
        return staticMethodInvocation(Map.class, "entry", key, value);
    }

    /**
     * Creates a code block containing a constructor call.
     *
     * @param clazz a class of the instance that the constructor constructs
     * @param blocks code blocks defining arguments of the constructor call
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock constructor(@NonNull Class<?> clazz, @NonNull Object @NonNull ... blocks) {
        return constructor(ClassName.get(clazz), blocks);
    }

    /**
     * Creates a code block containing a constructor call.
     *
     * @param name a name of the class of the instance that the constructor constructs
     * @param blocks code blocks defining arguments of the constructor call
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock constructor(@NonNull ClassName name, @NonNull Object @NonNull ... blocks) {
        return CodeBlock.of("new " + TYPE_SPEC + "(" + LITERAL_SPEC + ")", name, arguments(blocks));
    }

    /**
     * Creates a code block invoking a static method.
     *
     * @param clazz a class, which contains the method
     * @param method a name of the method
     * @param blocks code blocks defining arguments of the method call
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock staticMethodInvocation(@NonNull Class<?> clazz, @NonNull String method,
                                                            @NonNull Object @NonNull ... blocks) {
        return staticMethodInvocation(ClassName.get(clazz), method, blocks);
    }

    /**
     * Creates a code block invoking a static method.
     *
     * @param className a name of the class, which contains the method
     * @param method a name of the method
     * @param blocks code blocks defining arguments of the method call
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock staticMethodInvocation(@NonNull ClassName className, @NonNull String method,
                                                            @NonNull Object @NonNull ... blocks) {
        return CodeBlock.of(TYPE_SPEC + "." + method + "(" + LITERAL_SPEC + ")", className, arguments(blocks));
    }

    /**
     * Creates a code block referencing a static field.
     *
     * @param className a name of the class, which contains the method
     * @param fieldName a name of the field
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock staticFieldReference(@NonNull ClassName className, @NonNull String fieldName) {
        return CodeBlock.of(TYPE_SPEC + "." + fieldName, className);
    }

    /**
     * Creates a code block returning something.
     *
     * @param block a code block defining a value to return
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock returning(@NonNull Object block) {
        return CodeBlock.of("return " + LITERAL_SPEC, block);
    }

    /**
     * Creates a code block joining other code blocks, which are separated with a comma.
     *
     * @param blocks the code blocks to join
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock arguments(@NonNull Object @NonNull ... blocks) {
        StringJoiner formatJoiner = new StringJoiner(", ");
        for (int i = 0; i < blocks.length; i++)
            formatJoiner.add(LITERAL_SPEC);
        CodeBlock.of("");
        return CodeBlock.of(formatJoiner.toString(), blocks);
    }

    /**
     * Creates a code block defining a string.
     *
     * @param string the string
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock string(@NonNull String string) {
        return CodeBlock.of(STRING_SPEC, string);
    }

    /**
     * Creates a code block defining a float.
     *
     * @param value the float
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock floatValue(float value) {
        return CodeBlock.of(LITERAL_SPEC, value + "f");
    }

    public static @NonNull CodeBlock optionalInt(@Nullable Integer integer) {
        if (integer == null) return staticMethodInvocation(OptionalInt.class, "empty");
        return staticMethodInvocation(OptionalInt.class, "of", integer);
    }
}