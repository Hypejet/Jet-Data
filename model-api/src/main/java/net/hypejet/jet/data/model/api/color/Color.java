package net.hypejet.jet.data.model.api.color;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.util.RGBLike;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Range;

/**
 * Represents an RGB color.
 *
 * @param value a "packed" value of the color
 * @since 1.0
 * @author Codestech
 * @see RGBLike
 */
public record Color(int value) implements RGBLike {

    private static final int MAX_RGB_FIELD_VALUE = 255;

    private static final int RED_VALUE_SHIFTER = 16;
    private static final int GREEN_VALUE_SHIFTER = 8;

    /**
     * Creates a {@linkplain Color color} from an {@linkplain RGBLike RGB-like}.
     *
     * <p>Returns the value provided if it is an instance of {@linkplain Color color}.</p>
     *
     * @param other the RGB-like
     * @return the color
     * @since 1.0
     */
    public static @NonNull Color color(@NonNull RGBLike other) {
        NullabilityUtil.requireNonNull(other, "other RGB-like");
        if (other instanceof Color color) return color;
        return color((short) other.red(), (short) other.green(), (short) other.blue());
    }

    /**
     * Creates a {@linkplain Color color} from {@code red}, {@code green} and {@code blue} values specified.
     *
     * @param red the {@code red} value that the color should have
     * @param green the {@code green} value that the color should have
     * @param blue the {@code blue} value that the color should have
     * @return the color
     * @since 1.0
     */
    public static @NonNull Color color(short red, short green, short blue) {
        return new Color(blue | (green & MAX_RGB_FIELD_VALUE) << GREEN_VALUE_SHIFTER
                | (red & MAX_RGB_FIELD_VALUE) << RED_VALUE_SHIFTER);
    }

    @Override
    public @Range(from = 0L, to = 255L) int red() {
        return this.value >> RED_VALUE_SHIFTER & MAX_RGB_FIELD_VALUE;
    }

    @Override
    public @Range(from = 0L, to = 255L) int green() {
        return this.value >> GREEN_VALUE_SHIFTER & MAX_RGB_FIELD_VALUE;
    }

    @Override
    public @Range(from = 0L, to = 255L) int blue() {
        return this.value & MAX_RGB_FIELD_VALUE;
    }
}