package net.hypejet.jet.data.biome.settings;

import net.hypejet.jet.data.biome.temperature.TemperatureModifier;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents settings of climate of a {@linkplain net.hypejet.jet.data.biome.Biome biome}.
 *
 * @param hasPrecipitation whether the biome should have precipitation
 * @param temperature a temperature factor of the biome
 * @param temperatureModifier modifier that affects resulting temperature
 * @param downfall a downfall factor of the biome
 * @since 1.0
 * @author Codestech
 * @see net.hypejet.jet.data.biome.Biome
 */
public record ClimateSettings(boolean hasPrecipitation, float temperature,
                              @NonNull TemperatureModifier temperatureModifier, float downfall) {}