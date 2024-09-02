package net.hypejet.jet.data.model.registry.registries.wolf;

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
                          @NonNull WolfBiomes biomes) {}