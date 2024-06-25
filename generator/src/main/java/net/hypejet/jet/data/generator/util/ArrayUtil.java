package net.hypejet.jet.data.generator.util;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.IntFunction;

/**
 * Represents a utility managing arrays.
 *
 * @since 1.0
 * @author Codestech
 */
public final class ArrayUtil {
    
    private ArrayUtil() {}

    /**
     * Concatenates an object with an array, creating a new array.
     *
     * @param arrayCreator a function creating the array
     * @param object the object
     * @param objects the array
     * @return the new array
     * @param <T> the type of contents of the new array
     * @since 1.0
     */
    @SafeVarargs
    public static <T> @NonNull T @NonNull [] concat(@NonNull IntFunction<T[]> arrayCreator, @NonNull T object,
                                                    @NonNull T @NonNull ... objects) {
        T[] array = arrayCreator.apply(objects.length + 1);
        array[0] = object;
        System.arraycopy(objects, 0, array, 1, objects.length);
        return array;
    }
}