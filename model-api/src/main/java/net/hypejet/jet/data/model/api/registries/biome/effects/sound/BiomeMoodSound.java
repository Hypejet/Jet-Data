package net.hypejet.jet.data.model.api.registries.biome.effects.sound;

import net.hypejet.jet.data.model.api.registries.biome.Biome;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a mood sound effect settings of a {@linkplain Biome biome}.
 *
 * <p>The sound plays in the direction of the selected block during moodiness calculation, and is magnified by
 * the offset.</p>
 *
 * @param event the sound
 * @param tickDelay a minimum time between plays and a rate, at which the moodiness increase
 * @param blockSearchExtent a radius used for the block search around player during the moodiness calculation
 * @param offset a distance offset from the player during playing the sound
 * @since 1.0
 * @see Keyed
 * @author Codestech
 * @see Biome
 */
public record BiomeMoodSound(@NonNull BiomeSoundEvent event, int tickDelay, int blockSearchExtent, double offset) {
    /**
     * Constructs the {@linkplain BiomeMoodSound biome mood sound}.
     *
     * @param event the sound
     * @param tickDelay a minimum time between plays and a rate, at which the moodiness increase
     * @param blockSearchExtent a radius used for the block search around player during the moodiness calculation
     * @param offset a distance offset from the player during playing the sound
     * @since 1.0
     */
    public BiomeMoodSound {
        NullabilityUtil.requireNonNull(event, "event");
    }
}