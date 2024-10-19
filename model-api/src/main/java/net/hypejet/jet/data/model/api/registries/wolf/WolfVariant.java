package net.hypejet.jet.data.model.api.registries.wolf;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a variant of a Minecraft wolf.
 *
 * @param wildTexture a texture of a wild version of wolves with this variant
 * @param tameTexture a texture of a tame version of wolves with this variant
 * @param angryTexture a texture of an angry version of wolves with this variant
 * @param biomes biomes on which wolves with this type can spawn
 * @since 1.0
 * @author Codestech
 */
public record WolfVariant(@NonNull Key wildTexture, @NonNull Key tameTexture, @NonNull Key angryTexture,
                          @NonNull WolfBiomes biomes) {
    /**
     * Constructs the {@linkplain WolfVariant wolf variant};
     *
     * @param wildTexture a texture of a wild version of wolves with this variant
     * @param tameTexture a texture of a tame version of wolves with this variant
     * @param angryTexture a texture of an angry version of wolves with this variant
     * @param biomes biomes on which wolves with this type can spawn
     * @since 1.0
     */
    public WolfVariant {
        NullabilityUtil.requireNonNull(wildTexture, "wild texture");
        NullabilityUtil.requireNonNull(tameTexture, "tame texture");
        NullabilityUtil.requireNonNull(angryTexture, "angry texture");
        NullabilityUtil.requireNonNull(biomes, "biomes");
    }
}