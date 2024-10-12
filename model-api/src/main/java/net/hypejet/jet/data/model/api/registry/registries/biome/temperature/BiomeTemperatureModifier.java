package net.hypejet.jet.data.model.api.registry.registries.biome.temperature;

import net.hypejet.jet.data.model.api.registry.registries.biome.Biome;

/**
 * Represents a modifier of temperature of a {@linkplain Biome biome}.
 *
 * @since 1.0
 * @author Codestech
 * @see Biome
 */
public enum BiomeTemperatureModifier {
    /**
     * Represents a temperature modifier, which does not do any additional modifications of the temperature.
     *
     * @since 1.0
     */
    NONE,
    /**
     * Represents a temperature modifier, which randomly distributes pockets of warm temperature throughout the
     * {@linkplain Biome biome}.
     *
     * @since 1.0
     */
    FROZEN
}