package net.hypejet.jet.registry.registries.biome.effects.sound;

import net.hypejet.jet.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;


/**
 * Represents a sound of a {@linkplain net.hypejet.jet.registry.registries.biome.Biome biome}.
 *
 * @param key an identifier of the sound
 * @param range a range of the sound, {@code null} if a volume should be used to calculate it
 * @since 1.0
 * @author Codestech
 * @see net.hypejet.jet.registry.registries.biome.Biome
 * @see Keyed
 */
public record BiomeSoundEvent(@NotNull Key key, @Nullable Float range) implements Keyed {
    /**
     * Constructs the {@linkplain BiomeSoundEvent biome sound event}.
     *
     * @param key an identifier of the sound
     * @param range a range of the sound, {@code null} if a volume should be used to calculate it
     * @since 1.0
     */
    public BiomeSoundEvent {
        NullabilityUtil.requireNonNull(key, "key");
    }
}