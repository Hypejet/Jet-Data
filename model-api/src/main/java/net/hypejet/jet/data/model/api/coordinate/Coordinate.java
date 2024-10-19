package net.hypejet.jet.data.model.api.coordinate;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a XYZ coordinate.
 *
 * @param <C> a type of the coordinate
 * @since 1.0
 * @author Codestech
 */
public sealed interface Coordinate<C extends Coordinate<C>> permits BlockPosition, Position, Vector {
    /**
     * Gets an {@code X} value.
     *
     * @return the value
     * @since 1.0
     */
    double x();

    /**
     * Gets an {@code Y} value.
     *
     * @return the value
     * @since 1.0
     */
    double y();

    /**
     * Gets an {@code Z} value.
     *
     * @return the value
     * @since 1.0
     */
    double z();

    /**
     * Gets a copy of the coordinate with multiplied values.
     *
     * @param x an {@code X} value multiplier
     * @param y an {@code Y} value multiplier
     * @param z an {@code Z} value multiplier
     * @return the copy
     * @since 1.0
     */
    @NonNull C multiply(double x, double y, double z);

    /**
     * Gets a copy of the coordinate with divided values.
     *
     * @param x an {@code X} value divider
     * @param y an {@code Y} value divider
     * @param z an {@code Z} value divider
     * @return the copy
     * @since 1.0
     */
    @NonNull C divide(double x, double y, double z);

    /**
     * Gets a copy of the coordinate with added values.
     *
     * @param x an {@code X} value addend
     * @param y an {@code Y} value addend
     * @param z an {@code Z} value addend
     * @return the copy
     * @since 1.0
     */
    @NonNull C add(double x, double y, double z);

    /**
     * Gets a copy of the coordinate with subtracted values.
     *
     * @param x an {@code X} value subtrahend
     * @param y an {@code Y} value subtrahend
     * @param z an {@code Z} value subtrahend
     * @return the copy
     * @since 1.0
     */
    @NonNull C subtract(double x, double y, double z);

    /**
     * Gets a copy of the coordinate with an {@code X} value specified.
     *
     * @param x the {@code X} value
     * @return the copy
     * @since 1.0
     */
    @NonNull C withX(double x);

    /**
     * Gets a copy of the coordinate with an {@code Y} value specified.
     *
     * @param y the {@code Y} value
     * @return the copy
     * @since 1.0
     */
    @NonNull C withY(double y);

    /**
     * Gets a copy of the coordinate with an {@code Z} value specified.
     *
     * @param z the {@code Z} value
     * @return the copy
     * @since 1.0
     */
    @NonNull C withZ(double z);

    /**
     * Gets a floored {@code X} value.
     *
     * @return the floored value
     * @since 1.0
     */
    default int blockX() {
        return (int) Math.floor(this.x());
    }

    /**
     * Gets a floored {@code Y} value.
     *
     * @return the floored value
     * @since 1.0
     */
    default int blockY() {
        return (int) Math.floor(this.y());
    }

    /**
     * Gets a floored {@code Z} value.
     *
     * @return the floored value
     * @since 1.0
     */
    default int blockZ() {
        return (int) Math.floor(this.z());
    }

    /**
     * Gets a copy of the coordinate with multiplied values.
     *
     * @param multiplier a multiplier of the values
     * @return the copy
     * @since 1.0
     */
    default @NonNull C multiply(double multiplier) {
        return this.multiply(multiplier, multiplier, multiplier);
    }

    /**
     * Gets a copy of the coordinate with multiplied values.
     *
     * @param multiplier a coordinate whose values are multipliers
     * @return the copy
     * @since 1.0
     */
    default @NonNull C multiply(@NonNull Coordinate<?> multiplier) {
        NullabilityUtil.requireNonNull(multiplier, "multiplier");
        return this.multiply(multiplier.x(), multiplier.y(), multiplier.z());
    }

    /**
     * Gets a copy of the coordinate with divided values.
     *
     * @param divider a divider of the values
     * @return the copy
     * @since 1.0
     */
    default @NonNull C divide(double divider) {
        return this.divide(divider, divider, divider);
    }

    /**
     * Gets a copy of the coordinate with divided values.
     *
     * @param divider a coordinate whose values are dividers
     * @return the copy
     * @since 1.0
     */
    default @NonNull C divide(@NonNull Coordinate<?> divider) {
        NullabilityUtil.requireNonNull(divider, "divider");
        return this.divide(divider.x(), divider.y(), divider.z());
    }

    /**
     * Gets a copy of the coordinate with added values.
     *
     * @param addend an addend of the values
     * @return the copy
     * @since 1.0
     */
    default @NonNull C add(double addend) {
        return this.add(addend, addend, addend);
    }

    /**
     * Gets a copy of the coordinate with added values.
     *
     * @param addend a coordinate whose values are addends
     * @return the copy
     * @since 1.0
     */
    default @NonNull C add(@NonNull Coordinate<?> addend) {
        NullabilityUtil.requireNonNull(addend, "addend");
        return this.add(addend.x(), addend.y(), addend.z());
    }

    /**
     * Gets a copy of the coordinate with subtracted values.
     *
     * @param subtrahend a subtrahend of the values
     * @return the copy
     * @since 1.0
     */
    default @NonNull C subtract(double subtrahend) {
        return this.subtract(subtrahend, subtrahend, subtrahend);
    }

    /**
     * Gets a copy of the coordinate with subtracted values.
     *
     * @param subtrahend a coordinate whose values are subtrahends
     * @return the copy
     * @since 1.0
     */
    default @NonNull C subtract(@NonNull Coordinate<?> subtrahend) {
        NullabilityUtil.requireNonNull(subtrahend, "subtrahend");
        return this.subtract(subtrahend.x(), subtrahend.y(), subtrahend.z());
    }

    /**
     * Calculates a squared length of this coordinate from origin of the coordinate system via
     * {@link #lengthSquared()} and roots it.
     *
     * @return the square root of the squared length
     * @since 1.0
     */
    default double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Calculates a squared length of this coordinate from origin of the coordinate system.
     *
     * @return the squared length
     * @since 1.0
     */
    default double lengthSquared() {
        return this.x() * this.x() + this.y() * this.y() + this.z() * this.z();
    }
}