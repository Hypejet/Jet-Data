package net.hypejet.jet.registry.registries.biome.effects.particle;

import net.hypejet.jet.registry.registries.biome.Biome;
import net.hypejet.jet.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.nbt.BinaryTag;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents settings of a Minecraft particle of a {@linkplain Biome biome}.
 *
 * @param key an identifier of the particle
 * @param data additional data of the particle, {@code null} if none
 * @param probability a probability of the particle
 * @since 1.0
 * @author Codestech
 * @see Biome
 */
public record BiomeParticleSettings(@NonNull Key key, @Nullable BinaryTag data, float probability) implements Keyed {
    /**
     * Constructs the {@linkplain BiomeParticleSettings biome particle settings}.
     *
     * @param key an identifier of the particle
     * @param data additional data of the particle, {@code null} if none
     * @param probability a probability of the particle
     * @since 1.0
     */
    public BiomeParticleSettings {
        NullabilityUtil.requireNonNull(key, "key");
    }
}