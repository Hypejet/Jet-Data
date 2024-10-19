package net.hypejet.jet.data.model.server.registry.registries.block;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a Minecraft block.
 *
 * @param requiredFeatureFlags a collection of feature flags that are required to enable the block
 * @since 1.0
 * @author Codestech
 */
public record Block(@NonNull Collection<Key> requiredFeatureFlags) {
    /**
     * Constructs the {@linkplain Block block}.
     *
     * @param requiredFeatureFlags a collection of feature flags that are required to enable the block
     * @since 1.0
     */
    public Block {
        NullabilityUtil.requireNonNull(requiredFeatureFlags, "required feature flags");
        requiredFeatureFlags = Set.copyOf(requiredFeatureFlags);
    }
}