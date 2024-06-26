package net.hypejet.jet.data.entity.category;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an implementation of the {@linkplain EntityCategory entity category}.
 *
 * @param maxInstancesPerChunk a count of max instances of entity with this category per chunk
 * @param isFriendly whether entities with this category are friendly
 * @param isPersistent whether entities with this category are persistent
 * @param noDespawnDistance a maximum distance between nearest player and an entity with this category that does
 *                          not despawn the entity after inactivity
 * @param despawnDistance a maximum distance between nearest player and an entity with this category that does
 *                        not despawn the entity
 * @param name a name of this category
 * @since 1.0
 * @author Codestech
 * @see EntityCategory
 */
record EntityCategoryImpl(int maxInstancesPerChunk, boolean isFriendly, boolean isPersistent, int noDespawnDistance,
                          int despawnDistance, @NonNull String name) implements EntityCategory {}