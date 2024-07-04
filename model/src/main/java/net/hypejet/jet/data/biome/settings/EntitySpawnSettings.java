package net.hypejet.jet.data.biome.settings;

import net.hypejet.jet.data.biome.settings.spawn.EntitySpawnCost;
import net.hypejet.jet.data.biome.settings.spawn.EntitySpawnPropertiesHandler;
import net.hypejet.jet.data.entity.category.EntityCategory;
import net.hypejet.jet.data.entity.type.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

/**
 * Represents settings of spawning entities on a {@linkplain net.hypejet.jet.data.biome.Biome biome}.
 *
 * @param entitySpawnProbability a probability of spawning an entity
 * @param spawners a map of specified spawn settings for entity categories
 * @param spawnCosts a map of specified spawn costs for entity types
 * @since 1.0
 * @author Codestech
 * @see net.hypejet.jet.data.biome.Biome
 */
public record EntitySpawnSettings(float entitySpawnProbability,
                                  @NonNull Map<EntityCategory, EntitySpawnPropertiesHandler> spawners,
                                  @NonNull Map<EntityType, EntitySpawnCost> spawnCosts) {
    /**
     * Constructs the {@linkplain EntitySpawnSettings entity spawn settings}.
     *
     * @param entitySpawnProbability a probability of spawning an entity
     * @param spawners a map of specified spawn settings for entity categories
     * @param spawnCosts a map of specified spawn costs for entity types
     * @since 1.0
     */
    public EntitySpawnSettings {
        spawners = Map.copyOf(spawners);
        spawnCosts = Map.copyOf(spawnCosts);
    }
}