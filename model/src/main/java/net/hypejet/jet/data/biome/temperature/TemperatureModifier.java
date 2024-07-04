package net.hypejet.jet.data.biome.temperature;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a Minecraft biome temperature modifier.
 *
 * @since 1.0
 * @author Codestech
 * @see net.hypejet.jet.data.biome.Biome
 */
public sealed interface TemperatureModifier permits TemperatureModifierImpl {
    /**
     * Gets a name of the temperature modifier.
     *
     * @return the name
     * @since 1.0
     */
    @NonNull String name();
}