package net.hypejet.jet.data.biome.temperature;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an implementation of {@linkplain TemperatureModifier temperature modifier}.
 *
 * @param name a name of the temperature modifier
 * @since 1.0
 * @author Codestech
 * @see TemperatureModifier
 */
record TemperatureModifierImpl(@NonNull String name) implements TemperatureModifier {}