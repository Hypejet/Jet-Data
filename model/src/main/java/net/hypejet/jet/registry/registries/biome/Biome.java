package net.hypejet.jet.registry.registries.biome;

import net.hypejet.jet.registry.registries.biome.effects.BiomeEffectSettings;
import net.hypejet.jet.registry.registries.biome.temperature.BiomeTemperatureModifier;
import net.hypejet.jet.utils.NullabilityUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents a Minecraft biome.
 *
 * @param hasPrecipitation whether the biome has precipitation
 * @param temperature a temperature of the biome
 * @param temperatureModifier a modifier of temperature of the biome, {@code null} if none
 * @param downfall a downfall factor of the biome, affects foliage and grass color if they are not explicitly set
 * @param effects effects of the biome
 * @since 1.0
 * @author Codestech
 */
public record Biome(boolean hasPrecipitation, float temperature, @Nullable BiomeTemperatureModifier temperatureModifier,
                    float downfall, @NonNull BiomeEffectSettings effects) {
    /**
     * Constructs the {@linkplain Biome biome}.
     *
     * @param hasPrecipitation whether the biome has precipitation
     * @param temperature a temperature of the biome
     * @param temperatureModifier a modifier of temperature of the biome, {@code null} if none
     * @param effects effects of the biome
     * @since 1.0
     */
    public Biome {
        NullabilityUtil.requireNonNull(effects, "biome effects");
    }

    /**
     * Creates a new {@linkplain Builder biome builder}.
     *
     * @return the new biome builder created
     * @since 1.0
     */
    public static @NonNull Builder builder() {
        return new Builder();
    }

    /**
     * Represents a builder of a {@linkplain Biome biome}.
     *
     * @since 1.0
     * @author Codestech
     * @see Biome
     */
    public static final class Builder {

        private boolean hasPrecipitation;

        private float temperature;
        private float downfall;

        private @Nullable BiomeTemperatureModifier temperatureModifier;
        private @Nullable BiomeEffectSettings effectSettings;

        private Builder() {}

        /**
         * Sets whether the {@linkplain Biome biome} should have precipitation.
         *
         * @param hasPrecipitation {@code true} if the biome should have precipitation, {@code false} otherwise
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder hasPrecipitation(boolean hasPrecipitation) {
            this.hasPrecipitation = hasPrecipitation;
            return this;
        }

        /**
         * Sets a temperature that {@linkplain Biome biome} should have.
         *
         * @param temperature the temperature
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * Sets a {@linkplain BiomeTemperatureModifier temperature modifier} that the {@linkplain Biome biome} should have.
         *
         * @param temperatureModifier the temperature modifier, {@code null} if none
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder temperatureModifier(@Nullable BiomeTemperatureModifier temperatureModifier) {
            this.temperatureModifier = temperatureModifier;
            return this;
        }

        /**
         * Sets a downfall factor that the {@linkplain Biome biome} should have.
         *
         * @param downfall the downfall factor
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder downfall(float downfall) {
            this.downfall = downfall;
            return this;
        }

        /**
         * Sets {@linkplain BiomeEffectSettings biome effect settings} that the {@linkplain Biome biome} should have.
         *
         * @param effectSettings the effect settings
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder effectSettings(@NonNull BiomeEffectSettings effectSettings) {
            this.effectSettings = NullabilityUtil.requireNonNull(effectSettings, "effect settings");
            return this;
        }

        /**
         * Builds a {@linkplain Biome biome} with settings specified in this {@linkplain Builder builder}.
         *
         * @return the biome
         * @since 1.0
         */
        public @NonNull Biome build() {
            return new Biome(this.hasPrecipitation, this.temperature, this.temperatureModifier, this.downfall,
                    NullabilityUtil.requireNonNull(this.effectSettings, "biome effect settings"));
        }
    }
}