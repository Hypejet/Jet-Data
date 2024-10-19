package net.hypejet.jet.data.model.api.entity;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a type of Minecraft entity.
 *
 * @param requiredFeatureFlags a collection of feature flags that are required to enable the entity type
 * @since 1.0
 * @author Codestech
 */
public record EntityType(@NonNull Collection<Key> requiredFeatureFlags) {
    /**
     * Constructs the {@linkplain EntityType entity type}.
     *
     * @param requiredFeatureFlags a collection of feature flags that are required to enable the entity types
     * @since 1.0
     */
    public EntityType {
        NullabilityUtil.requireNonNull(requiredFeatureFlags, "required feature flags");
        requiredFeatureFlags = Set.copyOf(requiredFeatureFlags);
    }
}