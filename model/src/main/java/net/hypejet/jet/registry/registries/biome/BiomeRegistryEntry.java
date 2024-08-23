package net.hypejet.jet.registry.registries.biome;

import net.hypejet.jet.registry.RegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntry registry entry}, which holds a {@linkplain Biome biome}.
 *
 * @since 1.0
 * @author Codestech
 * @see Biome
 * @see RegistryEntry
 */
public final class BiomeRegistryEntry extends RegistryEntry<Biome> {
    /**
     * Constructs the {@linkplain BiomeRegistryEntry biome registry entry}.
     *
     * @param key an identifier of this registry entry
     * @param value the value
     * @since 1.0
     */
    public BiomeRegistryEntry(@NonNull Key key, @NonNull Biome value) {
        super(key, value);
    }
}