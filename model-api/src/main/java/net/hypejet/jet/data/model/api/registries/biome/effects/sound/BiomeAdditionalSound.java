package net.hypejet.jet.data.model.api.registries.biome.effects.sound;

import net.hypejet.jet.data.model.api.registries.biome.Biome;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an ambient sound effect of a {@linkplain Biome biome} that has a chance
 * of playing randomly every tick.
 *
 * @param event the sound
 * @param tickChance a chance of playing the sound during a tick
 * @since 1.0
 * @author Codestech
 * @see Keyed
 * @see Biome
 */
public record BiomeAdditionalSound(@NonNull BiomeSoundEvent event, double tickChance) {
    /**
     * Constructs the {@linkplain BiomeAdditionalSound biome additional sound}.
     *
     * @param event the sound
     * @param tickChance a chance of playing the sound during a tick
     * @since 1.0
     */
    public BiomeAdditionalSound {
        NullabilityUtil.requireNonNull(event, "event");
    }
}