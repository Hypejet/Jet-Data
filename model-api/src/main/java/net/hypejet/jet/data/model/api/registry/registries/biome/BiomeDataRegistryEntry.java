package net.hypejet.jet.data.model.api.registry.registries.biome;

import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry}, which holds a {@linkplain Biome biome}.
 *
 * @since 1.0
 * @author Codestech
 * @see Biome
 * @see DataRegistryEntry
 */
public final class BiomeDataRegistryEntry extends DataRegistryEntry<Biome> {
    /**
     * Constructs the {@linkplain BiomeDataRegistryEntry biome data registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param value the biome
     * @param knownPackInfo an information of a feature pack, which enables the data registry entry
     * @since 1.0
     */
    public BiomeDataRegistryEntry(@NonNull Key key, @NonNull Biome value, @NonNull PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}