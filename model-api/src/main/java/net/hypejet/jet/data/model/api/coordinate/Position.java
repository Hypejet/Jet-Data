package net.hypejet.jet.data.model.api.coordinate;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain Coordinate coordinate} with a view.
 *
 * @param x an {@code X} value of the coordinate
 * @param y an {@code Y} value of the coordinate
 * @param z an {@code Z} value of the coordinate
 * @param yaw an angle of a yaw of the view, in degrees
 * @param pitch an angle of a pitch of the view, in degrees
 * @since 1.0
 * @author Codestech
 * @see Coordinate
 */
public record Position(double x, double y, double z, float yaw, float pitch) implements Coordinate<Position> {
    /**
     * Constructs the {@linkplain Position position}.
     *
     * @param x an {@code X} value of the coordinate
     * @param y an {@code Y} value of the coordinate
     * @param z an {@code Z} value of the coordinate
     * @param yaw an angle of a yaw of the view
     * @param pitch an angle of a pitch of the view
     * @since 1.0
     */
    public Position {
        yaw = wrapDegrees(yaw);
        pitch = Math.clamp(wrapDegrees(pitch), -90f, 90f);
    }

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
    public @NonNull Position withValues(double x, double y, double z) {
        return new Position(x, y, z, this.yaw(), this.pitch());
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

    private static float wrapDegrees(float value) {
        value %= 360f;

        if (value >= 180) value -= 360;
        else value += 360;

        return value;
    }
}