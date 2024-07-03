package net.hypejet.jet.data.entity.type;

import net.hypejet.jet.data.block.Block;
import net.hypejet.jet.data.entity.category.EntityCategory;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Set;

/**
 * Represents an implementation of the {@linkplain EntityType entity type}.
 *
 * @param key a key of the entity type
 * @param numericId a numeric identifier of the block
 * @param canSerialize whether entities with this type can be serialized
 * @param canSummon whether entities with this type can be summoned
 * @param fireImmune whether entities with this type are immune to fire
 * @param canSpawnFarFromPlayer whether entities with this type can spawn far from player
 * @param clientTrackingRange how far from a player can entities with this type spawn for
 * @param updateInterval a duration of interval between entities with this type can update
 * @param spawnDimensionsScale a multiplier of bounding box dimensions, which is applied to entities with this type
 * @param dimensions an {@linkplain EntityDimensions entity dimension} of entities with this type
 * @param category an {@linkplain EntityCategory entity category} of entities with this type
 * @param immuneTo blocks that entities with this type are immune to
 * @param translationKey a translation key of entities with this type
 * @param requiredFeatureFlags required feature flags to enable this entity type
 * @since 1.0
 * @author Codestech
 */
record EntityTypeImpl(@NonNull Key key, int numericId, boolean canSerialize, boolean canSummon, boolean fireImmune,
                      boolean canSpawnFarFromPlayer, int clientTrackingRange, int updateInterval,
                      float spawnDimensionsScale, @NonNull EntityDimensions dimensions,
                      @NonNull EntityCategory category, @NonNull Collection<Block> immuneTo,
                      @NonNull String translationKey, @NonNull Collection<Key> requiredFeatureFlags)
        implements EntityType {
    /**
     * Constructs the {@linkplain EntityTypeImpl entity type implementation}.
     *
     * @param key a key of the entity type
     * @param numericId a numeric identifier of the block
     * @param canSerialize whether entities with this type can be serialized
     * @param canSummon whether entities with this type can be summoned
     * @param fireImmune whether entities with this type are immune to fire
     * @param canSpawnFarFromPlayer whether entities with this type can spawn far from player
     * @param clientTrackingRange how far from a player can entities with this type spawn for
     * @param updateInterval a duration of interval between entities with this type can update
     * @param spawnDimensionsScale a multiplier of bounding box dimensions, which is applied to entities with this type
     * @param dimensions an {@linkplain EntityDimensions entity dimension} of entities with this type
     * @param category an {@linkplain EntityCategory entity category} of entities with this type
     * @param immuneTo blocks that entities with this type are immune to
     * @param translationKey a translation key of entities with this type
     * @param requiredFeatureFlags required feature flags to enable this entity type
     * @since 1.0
     */
    EntityTypeImpl {
        immuneTo = Set.copyOf(immuneTo);
        requiredFeatureFlags = Set.copyOf(requiredFeatureFlags);
    }
}