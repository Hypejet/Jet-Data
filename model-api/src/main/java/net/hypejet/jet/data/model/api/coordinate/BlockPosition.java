package net.hypejet.jet.data.model.api.coordinate;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain Coordinate coordinate}, which values are integers.
 *
 * @param blockX an {@code X} value of the coordinate
 * @param blockY an {@code Y} value of the coordinate
 * @param blockZ an {@code Z} value of the coordinate
 * @since 1.0
 * @author Codestech
 * @see Coordinate
 */
public record BlockPosition(int blockX, int blockY, int blockZ) implements Coordinate<BlockPosition> {
    /**
     * {@inheritDoc}
     */
    @Override
    public double x() {
        return this.blockX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double y() {
        return this.blockY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double z() {
        return this.blockZ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull BlockPosition multiply(double x, double y, double z) {
        return blockPosition(this.blockX * x, this.blockY * y, this.blockZ * z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull BlockPosition divide(double x, double y, double z) {
        return blockPosition(this.blockX / x, this.blockY / y, this.blockZ / z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull BlockPosition add(double x, double y, double z) {
        return blockPosition(this.blockX + x, this.blockY + y, this.blockZ + z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull BlockPosition subtract(double x, double y, double z) {
        return blockPosition(this.blockX - x, this.blockY - y, this.blockZ - z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull BlockPosition withX(double x) {
        return blockPosition(x, this.blockY, this.blockZ);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull BlockPosition withY(double y) {
        return blockPosition(this.blockX, y, this.blockZ);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull BlockPosition withZ(double z) {
        return blockPosition(this.blockX, this.blockY, z);
    }

    /**
     * Creates a {@linkplain BlockPosition block position}.
     * <p>All values provided will be {@linkplain Math#floor(double) floored}.</p>
     *
     * @param x an {@code X} value of the coordinate
     * @param y an {@code Y} value of the coordinate
     * @param z an {@code Z} value of the coordinate
     * @return the block position
     * @since 1.0
     */
    public static @NonNull BlockPosition blockPosition(double x, double y, double z) {
        return new BlockPosition((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }
}