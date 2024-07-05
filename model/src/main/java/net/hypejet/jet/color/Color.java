package net.hypejet.jet.color;

import net.kyori.adventure.util.RGBLike;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.value.qual.IntRange;

/**
 * Represents an RGB color.
 *
 * @param red a red value
 * @param green a green value
 * @param blue a blue value
 * @since 1.0
 * @author Codestech
 * @see RGBLike
 */
public record Color(@IntRange(from = 0, to = 255) int red, @IntRange(from = 0, to = 255) int green,
                    @IntRange(from = 0, to = 255) int blue) implements RGBLike {

    private static final int RGB_VALUE_BYTE = 0xff;

    /**
     * Creates a {@linkplain Color color} from an RGB value.
     *
     * @param value the RGB value
     * @return the color
     * @since 1.0
     */
    public static @NonNull Color color(int value) {
        return new Color((value >> 16) & RGB_VALUE_BYTE, (value >> 8) & RGB_VALUE_BYTE, value & RGB_VALUE_BYTE);
    }

    /**
     * Creates a {@linkplain Color color} from an {@linkplain RGBLike RGB-like}.
     * <p>Returns the value provided if it is an instance of {@linkplain Color color}.</p>
     *
     * @param other the RGB-like
     * @return the color
     * @since 1.0
     */
    public static @NonNull Color color(@NonNull RGBLike other) {
        if (other instanceof Color color) return color;
        return new Color(other.red(), other.green(), other.blue());
    }
}