package net.hypejet.jet.data.entity.category;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a category of a Minecraft entity.
 *
 * @since 1.0
 * @author Codestech
 */
public sealed interface EntityCategory permits EntityCategoryImpl {
    /**
     * Gets a count of max instances of entity with this category per chunk.
     *
     * @return the count
     * @since 1.0
     */
    int maxInstancesPerChunk();

    /**
     * Gets whether entities with this category are friendly.
     *
     * @return {@code true} if entities with this category are friendly, {@code false} otherwise
     * @since 1.0
     */
    boolean isFriendly();

    /**
     * Gets whether entities with this category are persistent.
     *
     * @return {@code true} if entities with this category are persistent, {@code false} otherwise
     * @since 1.0
     */
    boolean isPersistent();

    /**
     * Gets a maximum distance between nearest player and an entity with this category that does not despawn the
     * entity after inactivity.
     *
     * @return the distance, in blocks
     * @since 1.0
     */
    int noDespawnDistance();


    /**
     * Gets a maximum distance between nearest player and an entity with this category that does not despawn the
     * entity.
     *
     * @return the distance, in blocks
     * @since 1.0
     */
    int despawnDistance();

    /**
     * Gets a name of this category.
     *
     * @return the name
     * @since 1.0
     */
    @NonNull String name();
}