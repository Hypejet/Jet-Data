package net.hypejet.jet.registry.registries.dimension;

import net.hypejet.jet.registry.registries.dimension.number.IntegerProvider;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a Minecraft type of dimension.
 *
 * @param fixedTime a fixed time of a day that the dimensions using this type should have, {@code null} if the time
 *                  should be dynamic
 * @param hasSkylight whether the dimensions using this type should have access to skylight
 * @param hasCeiling whether the dimension using this type should have bedrock ceiling, if {@code true} it causes lava
 *                   to spread faster
 * @param ultrawarm whether water placed in the dimensions using this type should immediately evaporate,
 *                  if {@code true} it causes lava to spread thinner
 * @param natural whether nether portals placed in dimensions using this type can spawn zombified piglins,
 *                if {@code false} compasses spin randomly in these dimensions
 * @param coordinateScale a multiplier applied to coordinates when traveling from dimensions using this type to others
 * @param bedWorks whether players in dimensions using this type can use a bed to sleep
 * @param respawnAnchorWorks whether players in dimensions using this type can charge and use respawn anchors
 * @param minY a minimum block height of dimensions using this type, should be a multiply of {@code 16}
 * @param height a maximum block height of dimensions using this type, should be a multiply of {@code 16}
 * @param localHeight a maximum block height of dimensions using this type, within which chorus plants and nether
 *                    portals can bring players
 * @param infiniburn a tag, which makes blocks using it burn forever in dimensions using this type
 * @param effects an identifier of special effects of dimensions using this type, such as cloud level and sky type
 * @param ambientLight a light level of dimensions using this type, used as an interpolation factor during calculation
 *                     of brightness generated from the skylight
 * @param piglinSafe whether piglins should shake and transform to zombified piglins in dimensions using this type
 * @param hasRaids whether players with a Bad Omen effect can cause a raid in dimensions using this type
 * @param monsterSpawnLightLevel a maximum allowed light level for a monster spawn in dimensions using this type
 * @param monsterSpawnBlockLightLimit a maximum allowed block light level for a monster spawn in dimensions using this
 *                                    type
 * @since 1.0
 * @author Codestech
 */
public record DimensionType(@NonNull Long fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultrawarm,
                            boolean natural, double coordinateScale, boolean bedWorks, boolean respawnAnchorWorks,
                            int minY, int height, int localHeight, @NonNull String infiniburn, @NonNull Key effects,
                            float ambientLight, boolean piglinSafe, boolean hasRaids,
                            @NonNull IntegerProvider monsterSpawnLightLevel, int monsterSpawnBlockLightLimit) {}