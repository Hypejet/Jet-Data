package net.hypejet.jet.registry.registries.biome.effects.music;

import net.hypejet.jet.registry.registries.biome.Biome;
import net.hypejet.jet.registry.registries.biome.effects.sound.BiomeSoundEvent;
import net.hypejet.jet.utils.NullabilityUtil;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents music properties of a {@linkplain Biome biome}.
 *
 * @param event a sound to be played as the music
 * @param minimumDelay a minimum time in ticks since the last music finished for this music to be able to play
 * @param maximumDelay a maximum time in ticks since the last music finished for this music to be able to play
 * @param replaceCurrentMusic whether this music can replace the current one
 * @since 1.0
 * @author Codestech
 * @see Keyed
 * @see Biome
 */
public record BiomeMusic(@NonNull BiomeSoundEvent event, int minimumDelay, int maximumDelay,
                         boolean replaceCurrentMusic) {
    /**
     * Constructs the {@linkplain BiomeMusic biome music}.
     *
     * @param event a sound to be played as the music
     * @param minimumDelay a minimum time in ticks since the last music finished for this music to be able to play
     * @param maximumDelay a maximum time in ticks since the last music finished for this music to be able to play
     * @param replaceCurrentMusic whether this music can replace the current one
     * @since 1.0
     */
    public BiomeMusic {
        NullabilityUtil.requireNonNull(event, "sound event");
    }
}