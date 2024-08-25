package net.hypejet.jet.registry.registries.dimension;

import net.hypejet.jet.pack.DataPack;
import net.hypejet.jet.registry.RegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntry registry entry}, which holds a {@linkplain DimensionType dimension type}.
 *
 * @since 1.0
 * @author Codestech
 * @see DimensionType
 * @see RegistryEntry
 */
public final class DimensionTypeRegistryEntry extends RegistryEntry<DimensionType> {
    /**
     * Constructs the {@linkplain DimensionTypeRegistryEntry dimension-type registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the value
     * @since 1.0
     */
    public DimensionTypeRegistryEntry(@NonNull Key key, @NonNull DataPack knownPack, @NonNull DimensionType value) {
        super(key, knownPack, value);
    }
}