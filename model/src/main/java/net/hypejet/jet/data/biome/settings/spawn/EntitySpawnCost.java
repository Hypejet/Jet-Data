package net.hypejet.jet.data.biome.settings.spawn;

/**
 * Represents a cost of a Minecraft entity spawn.
 *
 * @param energyBudget a minimum energy required to spawn the entity
 * @param charge an energy that the entity spawn charges
 * @since 1.0
 * @author Codestech
 */
public record EntitySpawnCost(double energyBudget, double charge) {}