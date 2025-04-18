package net.hypejet.jet.data.model.server.registry.registries.block;

import com.google.common.primitives.ImmutableIntArray;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a Minecraft block.
 *
 * @param requiredFeatureFlags a collection of feature flags that are required to enable the block
 * @param defaultBlockStateId an identifier of a default block state that should be associated with the block
 * @param possibleBlockStateIdentifiers an array of identifiers of block states that should be associated with
 *                                      the block
 * @param blockEntityTypeKey a key of a block entity type that should be associated with the block, {@code null}
 *                           if none
 * @since 1.0
 * @author Codestech
 */
public record Block(@NonNull Collection<Key> requiredFeatureFlags, int defaultBlockStateId,
                    @NonNull ImmutableIntArray possibleBlockStateIdentifiers, @Nullable Key blockEntityTypeKey) {
    /**
     * Constructs the {@linkplain Block block}.
     *
     * @param requiredFeatureFlags a collection of feature flags that are required to enable the block
     * @param defaultBlockStateId an identifier of a default block state that should be associated with the block
     * @param possibleBlockStateIdentifiers an array of identifiers of block states that should be associated with
     *                                      the block
     * @param blockEntityTypeKey a key of a block entity type that should be associated with the block, {@code null}
     *                           if none
     * @since 1.0
     */
    public Block {
        NullabilityUtil.requireNonNull(requiredFeatureFlags, "required feature flags");
        NullabilityUtil.requireNonNull(possibleBlockStateIdentifiers, "possible block state identifiers");
        requiredFeatureFlags = Set.copyOf(requiredFeatureFlags);
    }
}