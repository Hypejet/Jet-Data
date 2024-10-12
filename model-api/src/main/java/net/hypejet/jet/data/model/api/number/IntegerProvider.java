package net.hypejet.jet.data.model.api.number;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.List;

/**
 * Represents a type of providing an integer.
 *
 * @since 1.0
 * @author Codestech
 */
public sealed interface IntegerProvider {
    /**
     * Represents an {@linkplain IntegerProvider integer provider}, which provides a constant value.
     *
     * @param value the constant value
     * @since 1.0
     * @author Codestech
     * @see IntegerProvider
     */
    record ConstantInteger(int value) implements IntegerProvider {}

    /**
     * Represents an {@linkplain IntegerProvider integer provider}, which provides a random integer in range
     * specified.
     *
     * @param minimum a minimum integer of the range, inclusive
     * @param maximum a maximum integer of the range, inclusive
     * @since 1.0
     * @author Codstech
     * @see IntegerProvider
     */
    record Uniform(int minimum, int maximum) implements IntegerProvider {}

    /**
     * Represents an {@linkplain IntegerProvider integer provider}, which provides a random integer in range specified
     * with a bias toward the lower end.
     *
     * @param minimum a minimum integer of the range, inclusive
     * @param maximum a maximum integer of the range, inclusive
     * @since 1.0
     * @author Codestech
     * @see IntegerProvider
     */
    record BiasedToBottom(int minimum, int maximum) implements IntegerProvider {}

    /**
     * Represents an {@linkplain IntegerProvider integer provider}, which provides an integer from
     * another {@linkplain IntegerProvider integer provider} specified, which is clamped to fit between
     * minimum and maximum integers specified.
     *
     * @param minimum the minimum integer, inclusive
     * @param maximum the maximum integer, inclusive
     * @param source the other integer provider
     * @since 1.0
     * @author Codstech
     * @see IntegerProvider
     */
    record Clamped(int minimum, int maximum, @NonNull IntegerProvider source) implements IntegerProvider {}

    /**
     * Represents an {@linkplain IntegerProvider integer provider}, which provides an integer randomly generated
     * using gaussian distribution, which is clamped to fit between minimum and maximum integers specified.
     *
     * @param mean a central value where the peak distribution occurs
     * @param deviation a "width" of the distribution
     * @param minimum the minimum integer, inclusive
     * @param maximum the maximum integer, inclusive
     * @since 1.0
     * @author Codestech
     * @see IntegerProvider
     */
    record ClampedNormal(float mean, float deviation, int minimum, int maximum) implements IntegerProvider {}

    /**
     * Represents an {@linkplain IntegerProvider integer provider}, which provides an integer provided from
     * a random integer providers from a pool.
     *
     * @param entries the pool
     * @since 1.0
     * @author Codestech
     * @see IntegerProvider
     */
    record WeightedList(@NonNull Collection<Entry> entries) implements IntegerProvider {
        /**
         * Constructs the {@linkplain WeightedList weighted list}.
         *
         * @param entries the pool
         * @since 1.0
         */
        public WeightedList {
            entries = List.copyOf(entries);
        }

        /**
         * Represents an entry of the pool of a {@linkplain WeightedList weighted list}.
         *
         * @param source an integer provider, which provides an integer when the entry was selected
         * @param weight a weight of this entry, higher weight means that the entry has higher chance to be selected
         * @since 1.0
         * @author Codestech
         */
        public record Entry(@NonNull IntegerProvider source, int weight) {}
    }
}