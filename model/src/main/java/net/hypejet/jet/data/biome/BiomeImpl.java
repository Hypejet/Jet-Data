package net.hypejet.jet.data.biome;

import net.hypejet.jet.data.biome.effects.BiomeEffects;
import net.hypejet.jet.data.biome.settings.ClimateSettings;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents an implementation of the {@linkplain Biome biome}.
 *
 * @param key a key of the biome
 * @param climate climate settings of the biome
 * @param effects effects of the biome
 * @since 1.0
 * @author Codestech
 * @see Biome
 */
record BiomeImpl(@NonNull Key key, @NonNull ClimateSettings climate, @NonNull BiomeEffects effects) implements Biome {}