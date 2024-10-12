package net.hypejet.jet.data.model.server.registry.registries.block.state;

import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry}, which holds a {@linkplain BlockState block state}.
 *
 * @since 1.0
 * @author Codestech
 * @see BlockState
 * @see DataRegistryEntry
 */
public final class BlockStateRegistryEntry extends DataRegistryEntry<BlockState> {
    /**
     * Constructs the {@linkplain DataRegistryEntry data registry entry}.
     *
     * @param key an identifier, which the data registry entry should have
     * @param value the block state
     * @param knownPackInfo an information of a feature pack, which enables the data registry entry
     * @since 1.0
     */
    public BlockStateRegistryEntry(@NonNull Key key, @NonNull BlockState value, @Nullable PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}