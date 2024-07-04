package net.hypejet.jet.data.biome;

import net.hypejet.jet.data.IdentifiedRegistryEntry;
import net.hypejet.jet.data.biome.effects.BiomeEffects;
import net.hypejet.jet.data.biome.settings.ClimateSettings;
import net.hypejet.jet.data.biome.settings.EntitySpawnSettings;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;

/**
 * Represents a Minecraft biome.
 *
 * @since 1.0
 * @author Codestech
 * @see IdentifiedRegistryEntry
 */
public sealed interface Biome extends IdentifiedRegistryEntry permits BiomeImpl {
    /**
     * Gets a {@linkplain ClimateSettings climate settings} of the biome.
     *
     * @return the climate settings
     * @since 1.0
     * @see ClimateSettings
     */
    @NonNull ClimateSettings climate();

    /**
     * Gets {@linkplain BiomeEffects effects} of the biome.
     *
     * @return the effects
     * @since 1.0
     * @see BiomeEffects
     */
    @NonNull BiomeEffects effects();

    /**
     * Gets an {@linkplain EntitySpawnSettings entity spawn settings} of the biome.
     *
     * @return the entity spawn settings
     * @since 1.0
     * @see EntitySpawnSettings
     */
    @Nullable EntitySpawnSettings entitySpawnSettings();

    /**
     * Creates a {@linkplain Biome biome}.
     *
     * @param key a key of the biome
     * @param climate a climate settings of the biome
     * @param effects effects of the biome
     * @return the biome
     * @since 1.0
     */
    static @NonNull Biome biome(@NonNull Key key, @NonNull ClimateSettings climate, @NonNull BiomeEffects effects) {
        return biome(key, climate, effects, null);
    }

    /**
     * Creates a {@linkplain Biome biome}.
     *
     * @param key a key of the biome
     * @param climate a climate settings of the biome
     * @param effects effects of the biome
     * @param entitySpawnSettings an entity spawn settings of the biome
     * @return the biome
     * @since 1.0
     */
    static @NonNull Biome biome(@NonNull Key key, @NonNull ClimateSettings climate, @NonNull BiomeEffects effects,
                                @Nullable EntitySpawnSettings entitySpawnSettings) {
        Objects.requireNonNull(key, "The key must not be null");
        Objects.requireNonNull(climate, "The climate settings must not be null");
        Objects.requireNonNull(effects, "The biome settings must not be null");
        return new BiomeImpl(key, climate, effects, entitySpawnSettings);
    }
}