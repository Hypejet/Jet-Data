package net.hypejet.jet.data.model.registry.registries.painting;

import net.hypejet.jet.data.model.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a variant of a Minecraft painting.
 *
 * @param asset an texture that paintings with this variant should have
 * @param height a height that paintings with this variant should have, in blocks
 * @param width a width that paintings with this variant should have, in blocks
 * @since 1.0
 * @author Codestech
 */
public record PaintingVariant(@NonNull Key asset, int height, int width) {
    /**
     * Constructs the {@linkplain PaintingVariant painting varinat}.
     *
     * @param asset an texture that paintings with this variant should have
     * @param height a height that paintings with this variant should have, in blocks
     * @param width a width that paintings with this variant should have, in blocks
     * @since 1.0
     */
    public PaintingVariant {
        NullabilityUtil.requireNonNull(asset, "asset");
    }
}