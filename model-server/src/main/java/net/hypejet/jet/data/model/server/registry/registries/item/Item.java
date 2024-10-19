package net.hypejet.jet.data.model.server.registry.registries.item;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a Minecraft item.
 *
 * @param requiredFeatureFlags a collection of feature flags that are required to enable the item
 * @since 1.0
 * @author Codestech
 */
public record Item(@NonNull Collection<Key> requiredFeatureFlags) {
    /**
     * Constructs the {@linkplain Item item}.
     *
     * @param requiredFeatureFlags a collection of feature flags that are required to enable the item
     * @since 1.0
     */
    public Item {
        NullabilityUtil.requireNonNull(requiredFeatureFlags, "required feature flags");
        requiredFeatureFlags = Set.copyOf(requiredFeatureFlags);
    }
}