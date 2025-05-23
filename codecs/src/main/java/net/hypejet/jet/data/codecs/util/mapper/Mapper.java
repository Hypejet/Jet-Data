package net.hypejet.jet.data.codecs.util.mapper;

import com.google.common.collect.ImmutableMap;
import com.google.gson.internal.Primitives;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents something that maps two values to each other.
 *
 * @param <R> a type of the first values
 * @param <W> a type of the second values
 * @since 1.0
 * @author Codestech
 */
public final class Mapper<R, W> {

    private final Map<R, W> firstToSecondMap;
    private final Map<W, R> secondToFirstMap;

    private final Class<R> firstClass;
    private final Class<W> secondClass;

    private Mapper(@NonNull Class<R> firstClass, @NonNull Class<W> secondClass,
                   @NonNull Map<R, W> firstToSecondMap) {
        this.firstClass = NullabilityUtil.requireNonNull(firstClass, "first class");
        this.secondClass = NullabilityUtil.requireNonNull(secondClass, "second class");
        this.firstToSecondMap = ImmutableMap.copyOf(firstToSecondMap);

        Map<W, R> secondToFirstMap = new HashMap<>();
        this.firstToSecondMap.forEach((first, second) -> secondToFirstMap.put(second, first));
        this.secondToFirstMap = ImmutableMap.copyOf(secondToFirstMap);
    }

    /**
     * Converts a {@linkplain W second value} to a {@linkplain R first value}.
     *
     * @param object the second value
     * @return the first value, {@code null} if the first value has not been mapped to any second value
     * @since 1.0
     */
    public @Nullable R read(@NonNull W object) {
        NullabilityUtil.requireNonNull(object, "object");
        return this.secondToFirstMap.get(checkAssignable(this.secondClass, object));
    }

    /**
     * Converts a {@linkplain R first value} to a {@linkplain W second value}.
     *
     * @param object the first value
     * @return the second value, {@code null} if the second value has not been mapped to any first value
     * @since 1.0
     */
    public @Nullable W write(@NonNull R object) {
        NullabilityUtil.requireNonNull(object, "object");
        return this.firstToSecondMap.get(checkAssignable(this.firstClass, object));
    }

    /**
     * Gets a {@linkplain Class class} of the read type.
     *
     * @return the class
     * @since 1.0
     */
    public @NonNull Class<R> readTypeClass() {
        return this.firstClass;
    }

    /**
     * Gets a {@linkplain Class class} of the written type.
     *
     * @return the class
     * @since 1.0
     */
    public @NonNull Class<W> writtenTypeClass() {
        return this.secondClass;
    }

    /**
     * Creates a new {@linkplain Builder mapper builder}.
     *
     * @param firstClass a class of the first value
     * @param secondClass a class of the second value
     * @return the new created converted builder
     * @param <R> a type of the first values
     * @param <W> a type of the second values
     * @since 1.0
     */
    public static <R, W> @NonNull Builder<R, W> builder(@NonNull Class<R> firstClass, @NonNull Class<W> secondClass) {
        NullabilityUtil.requireNonNull(firstClass, "first class");
        NullabilityUtil.requireNonNull(secondClass, "second class");
        return new Builder<>(firstClass, secondClass);
    }

    private static <T> @NonNull T checkAssignable(@NonNull Class<T> clazz, @NonNull T object) {
        NullabilityUtil.requireNonNull(clazz, "class");
        NullabilityUtil.requireNonNull(object, "object");

        clazz = Primitives.wrap(clazz);
        if (!clazz.isAssignableFrom(object.getClass()))
            throw new IllegalArgumentException("The value specified is not assignable from the class required");

        return object;
    }

    /**
     * Represents a builder of a {@linkplain Mapper mapper}.
     *
     * @param <R> a type of the first values
     * @param <W> a type of the second values
     * @since 1.0
     */
    public static final class Builder<R, W> {

        private final Map<R, W> map = new HashMap<>();

        private final Class<R> firstClass;
        private final Class<W> secondClass;

        private Builder(@NonNull Class<R> firstClass, @NonNull Class<W> secondClass) {
            this.firstClass = firstClass;
            this.secondClass = secondClass;
        }

        /**
         * Registers a {@linkplain Mapper mapper} value mapping.
         *
         * @param first the first value
         * @param second the second value
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder<R, W> register(@NonNull R first, @NonNull W second) {
            this.map.put(checkAssignable(this.firstClass, first), checkAssignable(this.secondClass, second));
            return this;
        }

        /**
         * Builds a {@linkplain Mapper mapper} based on mappings specified in this {@linkplain Builder builder}.
         *
         * @return the mapper
         * @since 1.0
         */
        public @NonNull Mapper<R, W> build() {
            return new Mapper<>(this.firstClass, this.secondClass, Map.copyOf(this.map));
        }
    }
}