package net.hypejet.jet.data.model.api.utils;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Function;

/**
 * Represents a utility for nullability management.
 *
 * @since 1.0
 * @author Codestech
 */
public final class NullabilityUtil {

    private NullabilityUtil() {}

    /**
     * Checks whether an object is null. Throws an exception if so.
     *
     * @param object the object to check
     * @param name a name of the object, used to throw the exception
     * @return the object
     * @param <T> a type of the object
     * @since 1.0
     */
    public static <T> @NonNull T requireNonNull(@Nullable T object, @NonNull String name) {
        if (object == null)
            throw new NullPointerException(String.format("The %s must not be null", name));
        return object;
    }

    /**
     * Applies a function if an object specified is not null.
     *
     * @param object the object
     * @param function the function
     * @return a result of the function, {@code null} if the object specified is null
     * @param <T> a type of the object
     * @param <R> a type of the function result
     * @since 1.0
     */
    public static <T, R> @Nullable R applyIfNotNull(@Nullable T object, @NonNull Function<T, R> function) {
        requireNonNull(function, "function");
        if (object == null) return null;
        return function.apply(object);
    }
}