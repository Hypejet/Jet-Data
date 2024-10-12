package net.hypejet.jet.data.model.api.coordinate;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain Coordinate coordinate} with no additional features.
 *
 * @param x an {@code X} value of the coordinate
 * @param y an {@code Y} value of the coordinate
 * @param z an {@code Z} value of the coordinate
 * @since 1.0
 * @author Codestech
 * @see Coordinate
 */
public record Vector(double x, double y, double z) implements Coordinate<Vector> {

    private static final Vector ZERO = new Vector(0, 0, 0);

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Vector multiply(double x, double y, double z) {
        return vector(this.x * x, this.y * y, this.z * z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Vector divide(double x, double y, double z) {
        return vector(this.x / x, this.y / y, this.z / z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Vector add(double x, double y, double z) {
        return vector(this.x + x, this.y + y, this.z + z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Vector subtract(double x, double y, double z) {
        return vector(this.z - z, this.y - y, this.z - z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Vector withX(double x) {
        return vector(x, this.y, this.z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Vector withY(double y) {
        return vector(this.x, y, this.z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Vector withZ(double z) {
        return vector(this.x, this.y, z);
    }

    /**
     * Creates a {@linkplain Vector vector}.
     * <p>If all values provided are {@code 0}, the {@link #zero()} value is returned.</p>
     *
     * @param x a {@code X} value of the vector
     * @param y a {@code Y} value of the vector
     * @param z a {@code Z} value of the vector
     * @return the vec
     * @since 1.0
     */
    public static @NonNull Vector vector(double x, double y, double z) {
        if (x == 0 && y == 0 && z == 0) return ZERO;
        return new Vector(x, y, z);
    }

    /**
     * Gets a {@linkplain Vector vector} whose values are all {@code 0}.
     *
     * @return the vector
     * @since 1.0
     */
    public static @NonNull Vector zero() {
        return ZERO;
    }
}