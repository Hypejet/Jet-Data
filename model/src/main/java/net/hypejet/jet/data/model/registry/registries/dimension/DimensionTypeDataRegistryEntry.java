package net.hypejet.jet.data.model.registry.registries.dimension;

import net.hypejet.jet.data.model.pack.info.PackInfo;
import net.hypejet.jet.data.model.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry}, which holds
 * a {@linkplain DimensionType dimension type}.
 *
 * @since 1.0
 * @author Codestech
 * @see DimensionType
 * @see DimensionType
 */
public final class DimensionTypeDataRegistryEntry extends DataRegistryEntry<DimensionType> {
    /**
     * Constructs the {@linkplain DimensionTypeDataRegistryEntry dimension-type registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param value the value
     * @param knownPackInfo an information of a data pack, which enables the data registry entry
     * @since 1.0
     */
    public DimensionTypeDataRegistryEntry(@NonNull Key key, @NonNull DimensionType value,
                                          @NonNull PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}