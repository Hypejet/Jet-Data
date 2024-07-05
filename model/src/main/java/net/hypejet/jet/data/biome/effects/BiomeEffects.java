package net.hypejet.jet.data.biome.effects;

import net.hypejet.jet.color.Color;
import net.hypejet.jet.data.biome.grass.GrassColorModifier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents effects of a {@linkplain net.hypejet.jet.data.biome.Biome biome}.
 *
 * @param fogColor a fog color of the biome
 * @param waterColor a water color of the biome
 * @param waterFogColor a water fog color of the biome
 * @param skyColor a sky color of the biome
 * @param foliageColorOverride a foliage color of the biome, {@code null} if not set
 * @param grassColorOverride a grass color of the biome, {@code null} if not set
 * @param grassColorModifier a modifier of grass color of the biome
 * @since 1.0
 * @author Codestech
 * @see net.hypejet.jet.data.biome.Biome
 */
public record BiomeEffects(@NonNull Color fogColor, @NonNull Color waterColor, @NonNull Color waterFogColor,
                           @NonNull Color skyColor, @Nullable Color foliageColorOverride,
                           @Nullable Color grassColorOverride, @NonNull GrassColorModifier grassColorModifier) {}