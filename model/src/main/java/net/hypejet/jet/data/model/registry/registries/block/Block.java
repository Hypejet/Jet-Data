package net.hypejet.jet.data.model.registry.registries.block;

import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a Minecraft block.
 *
 * @param requiredFeatureFlags a collection of feature flags that are required to enable the Block
 * @since 1.0
 * @author Codestech
 */
public record Block(@NonNull Collection<Key> requiredFeatureFlags) {
    /**
     * Constructs the {@linkplain Block block}.
     *
     * @param requiredFeatureFlags a collection of feature flags that are required to enable the Block
     * @since 1.0
     */
    public Block {
        requiredFeatureFlags = Set.copyOf(requiredFeatureFlags);
    }
}