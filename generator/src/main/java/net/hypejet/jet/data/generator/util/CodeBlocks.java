package net.hypejet.jet.data.generator.util;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import net.hypejet.jet.color.Color;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;

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

    private static final CodeBlock NULL_CODE_BLOCK = CodeBlock.of("null");

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
     * Creates a code block creating a {@linkplain Set set} using {@link Set#of(Object[])}.
     *
     * @param blocks the code blocks defining contents of the set
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock setCreator(@NonNull Object @NonNull ... blocks) {
        return staticMethodInvocation(Set.class, "of", blocks);
    }

    /**
     * Creates a code block creating a {@linkplain List list} using {@link List#of(Object[])}.
     * 
     * @param objects the code blocks defining contents of the list
     * @return the code block
     * @implSpec 1.0
     */
    public static @NonNull CodeBlock list(@NonNull Object @NonNull ... objects) {
        return staticMethodInvocation(List.class, "of", objects);
    }

    /**
     * Creates a code block creating a {@linkplain Map map} using {@link Map#ofEntries(Map.Entry[])}.
     *
     * @param entries a code blocks defining entries of the map
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock map(@NonNull Object @NonNull ... entries) {
        return staticMethodInvocation(Map.class, "ofEntries", entries);
    }

    /**
     * Creates a code block creating a {@linkplain Map.Entry map entry} using {@link Map#entry(Object, Object)}.
     *
     * @param key a key of the entry
     * @param value a value of the entry
     * @return the code block
     * @since 1.0
     */
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

    /**
     * Creates a code block using value provided, or using {@code null} when it is null.
     *
     * @param object the value
     * @param blockSupplier a function creating a code block when the value provided is not {@code null}
     * @return the code block
     * @param <T> a type of the value
     * @since 1.0
     */
    public static <T> @NonNull CodeBlock nullable(@Nullable T object, @NonNull Function<T, CodeBlock> blockSupplier) {
        if (object == null) return NULL_CODE_BLOCK;
        return blockSupplier.apply(object);
    }

    /**
     * Creates a code block creating a {@linkplain Color color}, using {@link Color#color(int)}.
     *
     * @param value an RGB value of the color
     * @return the code block
     * @since 1.0
     */
    public static @NonNull CodeBlock color(int value) {
        return CodeBlocks.staticMethodInvocation(Color.class, "color", value);
    }
}