package net.hypejet.jet.data.model.api.coordinate;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain Coordinate coordinate} with a view.
 *
 * @param x an {@code X} value of the coordinate
 * @param y an {@code Y} value of the coordinate
 * @param z an {@code Z} value of the coordinate
 * @param yaw a yaw value of the view
 * @param pitch a pitch value of the view
 * @since 1.0
 * @author Codestech
 * @see Coordinate
 */
public record Position(double x, double y, double z, float yaw, float pitch) implements Coordinate<Position> {
    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Position multiply(double x, double y, double z) {
        return new Position(this.x * x, this.y * y, this.z * z, this.yaw, this.pitch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Position divide(double x, double y, double z) {
        return new Position(this.x / x, this.y / y, this.z / z, this.yaw, this.pitch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Position add(double x, double y, double z) {
        return new Position(this.x + x, this.y + y, this.z + z, this.yaw, this.pitch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Position subtract(double x, double y, double z) {
        return new Position(this.x - x, this.y - y, this.z - z, this.yaw, this.pitch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Position withX(double x) {
        return this.withCoordinate(x, this.y, this.z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Position withY(double y) {
        return this.withCoordinate(this.x, y, this.z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Position withZ(double z) {
        return this.withCoordinate(this.x, this.y, z);
    }

    /**
     * Gets a copy of this position with a yaw specified.
     *
     * @param yaw the yaw
     * @return the copy
     * @since 1.0
     */
    public @NonNull Position withYaw(float yaw) {
        return new Position(this.x, this.y, this.z, yaw, this.pitch);
    }

    /**
     * Gets a copy of this position with a pitch specified.
     *
     * @param pitch the pitch
     * @return the copy
     * @since 1.0
     */
    public @NonNull Position withPitch(float pitch) {
        return new Position(this.x, this.y, this.z, this.yaw, pitch);
    }

    /**
     * Gets a copy of this position with a yaw and pitch specified.
     *
     * @param yaw tha yaw
     * @param pitch the pitch
     * @return the copy
     * @since 1.0
     */
    public @NonNull Position withView(float yaw, float pitch) {
        return new Position(this.x, this.y, this.z, yaw, pitch);
    }

    /**
     * Gets a copy of this position with {@code X}, {@code Y} and {@code Z} values
     * of {@linkplain Coordinate a coordinate specified} specified.
     *
     * @param coordinate the coordinate
     * @return the copy
     * @since 1.0
     */
    public @NonNull Position withCoordinate(@NonNull Coordinate<?> coordinate) {
        return this.withCoordinate(coordinate.x(), coordinate.y(), coordinate.z());
    }

    /**
     * Gets a copy of this position with values specified.
     *
     * @param x an {@code X} value that the position should have
     * @param y an {@code Y} value that the position should have
     * @param z an {@code Z} value that the position should have
     * @return the copy
     * @since 1.0
     */
    public @NonNull Position withCoordinate(double x, double y, double z) {
        return new Position(x, y, z, this.yaw, this.pitch);
    }
}