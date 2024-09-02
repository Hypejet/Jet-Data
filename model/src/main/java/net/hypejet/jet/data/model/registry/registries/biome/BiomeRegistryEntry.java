package net.hypejet.jet.data.model.registry.registries.biome;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntry;
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
     * Constructs the {@linkplain RegistryEntry registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the value
     * @since 1.0
     */
    public BiomeRegistryEntry(@NonNull Key key, @NonNull DataPack knownPack, @NonNull Biome value) {
        super(key, knownPack, value);
    }
}