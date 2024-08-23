package net.hypejet.jet.registry.registries.biome.effects.particle;

import net.hypejet.jet.registry.registries.biome.Biome;
import net.hypejet.jet.utils.NullabilityUtil;
import net.kyori.adventure.nbt.BinaryTag;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents settings of a Minecraft particle of a {@linkplain Biome biome}.
 *
 * @param name a name of the particle
 * @param data additional data of the particle, {@code null} if none
 * @param probability a probability of the particle
 * @since 1.0
 * @author Codestech
 * @see Biome
 */
public record BiomeParticleSettings(@NonNull String name, @Nullable BinaryTag data, float probability) {
    /**
     * Constructs the {@linkplain BiomeParticleSettings biome particle settings}.
     *
     * @param name a name of the particle
     * @param data additional data of the particle, {@code null} if none
     * @param probability a probability of the particle
     * @since 1.0
     */
    public BiomeParticleSettings {
        NullabilityUtil.requireNonNull(name, "name");
    }
}