package net.hypejet.jet.utils;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

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
}