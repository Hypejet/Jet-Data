package net.hypejet.jet.data.generator.util;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * Represents a utility with helpers for java reflection.
 *
 * @since 1.0
 * @author Codestech
 */
public final class ReflectionUtil {

    private ReflectionUtil() {}

    /**
     * Invokes a {@linkplain Method method}.
     *
     * @param method the method
     * @param object an object to invoke the method on
     * @param args arguments of the method invocation
     * @return a value returned by the method
     * @since 1.0
     */
    public static @NonNull Object invoke(@NonNull Method method, @NonNull Object object,
                                         @NonNull Object @NonNull ... args) {
        return access(method, object, (m, o) -> m.invoke(o, args));
    }

    /**
     * Accesses a value from an object by granting access to it and then taking it.
     *
     * @param accessible a type of accessible object to access
     * @param object the value
     * @param accessor an accessor, which accesses the accessible object
     * @return the value
     * @param <A> a type of the accessible object
     * @param <O> a type of the object
     * @param <R> a type of the value
     * @since 1.0
     */
    public static <A extends AccessibleObject, O, R> @NonNull R access(@NonNull A accessible, @NonNull O object,
                                                                       @NonNull Accessor<A, O, R> accessor) {
        boolean wasAccessible = accessible.canAccess(object);
        if (!wasAccessible) accessible.setAccessible(true);

        try {
            return accessor.access(accessible, object);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            if (!wasAccessible) accessible.setAccessible(false);
        }
    }

    /**
     * Represents a function that accesses something.
     *
     * @param <A> a type of the accessible object that this accessor accesses
     * @param <O> a type of the object that this accessor accesses from
     * @param <R> a type of the value that this accessor accesses
     * @since 1.0
     */
    @FunctionalInterface
    public interface Accessor<A extends AccessibleObject, O, R> {
        /**
         * Accesses a result.
         *
         * @param accessible the accessible object
         * @param object the object
         * @return the result
         * @throws Throwable when an error occurred during the operation
         * @since 1.0
         */
        @NonNull R access(@NonNull A accessible, @NonNull O object) throws Throwable;
    }
}