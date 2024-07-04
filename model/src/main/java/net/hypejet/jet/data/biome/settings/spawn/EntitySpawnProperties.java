package net.hypejet.jet.data.biome.settings.spawn;

import net.hypejet.jet.data.entity.type.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents properties of spawning an entity - with a {@linkplain EntityType type} specified - on
 * a {@linkplain net.hypejet.jet.data.biome.Biome biome}.
 *
 * @param type the type of the entity
 * @param minCount a minimum count of the entity per chunk
 * @param maxCount a maximum count of the entity per chunk
 * @param weight a priority of this properties
 * @since 1.0
 * @author Codestech
 * @see EntityType
 * @see net.hypejet.jet.data.biome.Biome
 */
public record EntitySpawnProperties(@NonNull EntityType type, int minCount, int maxCount, int weight) {}