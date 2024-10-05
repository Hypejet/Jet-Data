package net.hypejet.jet.data.model.registry.registries.datapack;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry}, which holds a {@linkplain DataPack data pack}.
 *
 * @since 1.0
 * @author Codestech
 * @see DataPack
 * @see DataRegistryEntry
 */
public final class DataPackDataRegistryEntry extends DataRegistryEntry<DataPack> {
    /**
     * Constructs the {@linkplain DataPackDataRegistryEntry data pack data registry entry}.
     *
     * @param key an identifier, which the data registry entry should have
     * @param value the data pack
     * @since 1.0
     */
    public DataPackDataRegistryEntry(@NonNull Key key, @NonNull DataPack value) {
        super(key, value, null);
    }
}