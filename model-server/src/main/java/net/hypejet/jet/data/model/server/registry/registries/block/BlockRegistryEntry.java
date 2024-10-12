package net.hypejet.jet.data.model.server.registry.registries.block;

import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry}, which holds a {@linkplain Block block}.
 *
 * @since 1.0
 * @author Codestech
 * @see Block
 * @see DataRegistryEntry
 */
public final class BlockRegistryEntry extends DataRegistryEntry<Block> {
    /**
     * Constructs the {@linkplain DataRegistryEntry data registry entry}.
     *
     * @param key an identifier, which the data registry entry should have
     * @param value the block
     * @param knownPackInfo an information of a feature pack, which enables the data registry entry
     * @since 1.0
     */
    public BlockRegistryEntry(@NonNull Key key, @NonNull Block value, @Nullable PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}
