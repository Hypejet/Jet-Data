package net.hypejet.jet.data.model.registry.registries.dimension;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntryData;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntryData registry entry data}, which holds
 * a {@linkplain DimensionType dimension type}.
 *
 * @since 1.0
 * @author Codestech
 * @see DimensionType
 * @see RegistryEntryData
 */
public final class DimensionTypeRegistryEntryData extends RegistryEntryData<DimensionType> {
    /**
     * Constructs the {@linkplain DimensionTypeRegistryEntryData dimension-type registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the value
     * @since 1.0
     */
    public DimensionTypeRegistryEntryData(@NonNull Key key, @NonNull DataPack knownPack, @NonNull DimensionType value) {
        super(key, knownPack, value);
    }
}