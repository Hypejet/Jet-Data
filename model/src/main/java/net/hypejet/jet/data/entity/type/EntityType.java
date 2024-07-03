package net.hypejet.jet.data.entity.type;

import net.hypejet.jet.data.block.Block;
import net.hypejet.jet.data.entity.attachment.handler.EntityAttachmentHandler;
import net.hypejet.jet.data.entity.category.EntityCategory;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;

/**
 * Represents a type of Minecraft entity.
 *
 * @since 1.0
 * @author Codestech
 * @see Keyed
 */
public sealed interface EntityType extends Keyed permits EntityTypeImpl {
    /**
     * Gets a {@linkplain Key key} of the entity type.
     *
     * @return the key
     * @since 1.0
     * @see Key
     */
    @Override
    @NonNull Key key();

    /**
     * Gets a numeric identifier of the entity type.
     *
     * @return the numericId
     * @since 1.0
     */
    int numericId();

    /**
     * Gets whether entities with this type can be serialized.
     *
     * @return true if entities with this type can be serialized, false otherwise
     * @since 1.0
     */
    boolean canSerialize();

    /**
     * Gets whether entities with this type can be summoned.
     *
     * @return true if entities with this type can be summoned, false otherwise
     * @since 1.0
     */
    boolean canSummon();

    /**
     * Gets whether entities with this type are immune to fire.
     *
     * @return true if entities with this type are immune with fire, false otherwise
     * @since 1.0
     */
    boolean fireImmune();

    /**
     * Gets whether entities with this type can spawn far from player.
     *
     * @return true if entities with this type can spawn far from player, false otherwise
     * @since 1.0
     */
    boolean canSpawnFarFromPlayer();

    /**
     * Gets how far from a player can entities with this type spawn for.
     *
     * @return the distance, in chunks
     * @since 1.0
     */
    int clientTrackingRange();

    /**
     * Gets a duration of interval between entities with this type can update.
     *
     * @return the duration, in ticks
     * @since 1.0
     */
    int updateInterval();

    /**
     * Gets a multiplier of bounding box dimensions, which is applied to entities with this type.
     *
     * @return the multiplier
     * @since 1.0
     */
    float spawnDimensionsScale();

    /**
     * Gets an {@linkplain EntityDimensions entity dimensions} of entities with this type.
     *
     * @return the entity dimensions
     * @since 1.0
     */
    @NonNull EntityDimensions dimensions();

    /**
     * Gets an {@linkplain EntityCategory entity category} of entities with this type.
     *
     * @return the entity category
     * @since 1.0
     */
    @NonNull EntityCategory category();

    /**
     * Gets blocks that entities with this type are immune to.
     *
     * @return the blocks
     * @since 1.0
     */
    @NonNull Collection<Block> immuneTo();

    /**
     * Gets a translation key of entities with this type.
     *
     * @return the translation key
     * @since 1.0
     */
    @NonNull String translationKey();

    /**
     * Gets required feature flags to enable this entity type.
     *
     * @return the feature flags
     * @since 1.0
     */
    @NonNull Collection<Key> requiredFeatureFlags();

    /**
     * Represents a dimensions of a Minecraft entity.
     *
     * @param width a width of the entity
     * @param height a height of the entity
     * @param eyeHeight a height of eyes of the entity
     * @param fixed whether the dimensions of the entity are fixed
     * @param attachmentHandler a handler of attachments of the entity
     * @since 1.0
     * @author Codestech
     */
    record EntityDimensions(float width, float height, float eyeHeight, boolean fixed,
                            @NonNull EntityAttachmentHandler attachmentHandler) {}
}