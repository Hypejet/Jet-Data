package net.hypejet.jet.registry.registries.dimension;

import net.hypejet.jet.registry.registries.dimension.number.IntegerProvider;
import net.hypejet.jet.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents a Minecraft type of dimension.
 *
 * @param fixedTime a fixed time of a day that dimensions using this type should have, {@code null} if the time
 *                  should be dynamic
 * @param hasSkylight whether dimensions using this type should have access to skylight
 * @param hasCeiling whether dimensions using this type should have bedrock ceiling, if {@code true} it causes lava
 *                   to spread faster
 * @param ultrawarm whether water placed in dimensions using this type should immediately evaporate, if {@code true}
 *                  it causes lava to spread thinner
 * @param natural whether nether portals placed in dimensions using this type can spawn zombified piglins,
 *                if {@code false} compasses spin randomly in these dimensions
 * @param coordinateScale a multiplier applied to coordinates when traveling from dimensions using this type to others
 * @param bedWorks whether players in dimensions using this type can use a bed to sleep
 * @param respawnAnchorWorks whether players in dimensions using this type can charge and use respawn anchors
 * @param minY a minimum block height of dimensions using this type, should be a multiplication of {@code 16}
 * @param height a maximum block height of dimensions using this type, should be a multiplication of {@code 16}
 * @param localHeight a maximum block height of dimensions using this type, within which chorus plants and nether
 *                    portals can bring players, should be a multiplication of {@code 16}
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
                            @NonNull IntegerProvider monsterSpawnLightLevel, int monsterSpawnBlockLightLimit) {
    /**
     * Creates a new {@linkplain Builder dimension type builder}.
     *
     * @return the builder
     * @since 1.0
     */
    public static @NonNull Builder builder() {
        return new Builder();
    }

    /**
     * Represents a builder of a {@linkplain DimensionType dimension type}.
     *
     * @since 1.0
     * @author Codestech
     * @see DimensionType
     */
    public static final class Builder {

        private Long fixedTime;

        private boolean hasSkylight;
        private boolean hasCeiling;
        private boolean ultrwarm;
        private boolean natural;

        private double coordinateScale;

        private boolean bedWorks;
        private boolean respawnAnchorWorks;

        private int minY;
        private int height;
        private int localHeight;

        private String infiniburn;
        private Key effects;

        private float ambientLight;

        private boolean piglinSafe;
        private boolean hasRaids;

        private IntegerProvider monsterSpawnLightLevel;
        private int monsterSpawnBlockLightLimit;

        private Builder() {}

        /**
         * Sets a fixed time of a day that dimensions using this type should have.
         *
         * @param fixedTime the fixed time, {@code null} if the time should be dynamic
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder fixedTime(@Nullable Long fixedTime) {
            this.fixedTime = fixedTime;
            return this;
        }

        /**
         * Sets whether dimensions using this type should have access to skylight.
         *
         * @param hasSkylight {@code true} if dimensions using
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder hasSkylight(boolean hasSkylight) {
            this.hasSkylight = hasSkylight;
            return this;
        }

        /**
         * Sets whether dimensions using this type should have bedrock ceiling, if {@code true} it causes lava
         * to spread faster.
         *
         * @param hasCeiling {@code true} if dimensions using this type should have bedrock ceiling,
         *                   {@code false} otherwise
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder hasCeiling(boolean hasCeiling) {
            this.hasCeiling = hasCeiling;
            return this;
        }

        /**
         * Sets whether water placed in dimensions using this type should immediately evaporate, if {@code true}
         * it causes lava to spread thinner.
         *
         * @param ultrawarm {@code true} if water placed in dimensions using this type should immediately evaporate,
         *                  {@code false} otherwise
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder ultrawarm(boolean ultrawarm) {
            this.ultrwarm = ultrawarm;
            return this;
        }

        /**
         * Sets whether nether portals placed in dimensions using this type can spawn zombified piglins,
         * if {@code false} compasses spin randomly in these dimensions.
         *
         * @param natural {@code true} if nether portals placed in dimensions using this type can spawn
         *                zombified piglins, {@code false} otherwise
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder natural(boolean natural) {
            this.natural = natural;
            return this;
        }

        /**
         * Sets a multiplier applied to coordinates when traveling from dimensions using this type to others.
         *
         * @param coordinateScale the multiplier
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder coordinateScale(double coordinateScale) {
            this.coordinateScale = coordinateScale;
            return this;
        }

        /**
         * Sets whether players in dimensions using this type can use a bed to sleep.
         *
         * @param bedWorks {@code true} if players in dimensions using this type can use a bed to sleep, {@code false}
         *                  otherwise
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder bedWorks(boolean bedWorks) {
            this.bedWorks = bedWorks;
            return this;
        }

        /**
         * Sets whether players in dimensions using this type can charge and use respawn anchors.
         *
         * @param respawnAnchorWorks {@code} true if players in dimensions using this type can charge and use respawn
         *                           anchors, {@code false} otherwise
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder respawnAnchorWorks(boolean respawnAnchorWorks) {
            this.respawnAnchorWorks = respawnAnchorWorks;
            return this;
        }

        /**
         * Sets a minimum block height of dimensions using this type, should be a multiplication of {@code 16}.
         *
         * @param minY the minimum block height
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder minY(int minY) {
            this.minY = minY;
            return this;
        }

        /**
         * Sets a maximum block height of dimensions using this type, should be a multiplication of {@code 16}.
         *
         * @param height the maximum block height
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder height(int height) {
            this.height = height;
            return this;
        }

        /**
         * Sets a maximum block height of dimensions using this type, within which chorus plants and nether
         * portals can bring players, should be a multiplication of {@code 16}.
         *
         * @param localHeight the block height
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder localHeight(int localHeight) {
            this.localHeight = localHeight;
            return this;
        }

        /**
         * Sets a tag, which makes blocks using it burn forever in dimensions using this type.
         *
         * @param infiniburn the tag
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder infiniburn(@NonNull String infiniburn) {
            this.infiniburn = NullabilityUtil.requireNonNull(infiniburn, "infiniburn");
            return this;
        }

        /**
         * Sets an identifier of special effects of dimensions using this type, such as cloud level and sky type.
         *
         * @param effects the identifier
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder effects(@NonNull Key effects) {
            this.effects = NullabilityUtil.requireNonNull(effects, "effects");
            return this;
        }

        /**
         * Sets a light level of dimensions using this type, used as an interpolation factor during calculation
         * of brightness generated from the skylight.
         *
         * @param ambientLight the light level
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder ambientLight(float ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * Sets whether piglins should shake and transform to zombified piglins in dimensions using this type.
         *
         * @param piglinSafe {@code true} if piglins should shake and transform to zombified piglins in dimensions
         *                   using this type, {@code false} otherwise
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder piglinSafe(boolean piglinSafe) {
            this.piglinSafe = piglinSafe;
            return this;
        }

        /**
         * Sets whether players with a Bad Omen effect can cause a raid in dimensions using this type.
         *
         * @param hasRaids {@code true} if players with a Bad Omen effect can cause a raid in dimensions using this
         *                 type, {@code false} otherwise
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder hasRaids(boolean hasRaids) {
            this.hasRaids = hasRaids;
            return this;
        }

        /**
         * Sets a maximum allowed light level for a monster spawn in dimensions using this type.
         *
         * @param monsterSpawnLightLevel the maximum allowed light
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder monsterSpawnLightLevel(@NonNull IntegerProvider monsterSpawnLightLevel) {
            this.monsterSpawnLightLevel = NullabilityUtil.requireNonNull(monsterSpawnLightLevel,
                    "monster-spawn light level");
            return this;
        }

        /**
         * Sets a maximum allowed block light level for a monster spawn in dimensions using this type.
         *
         * @param monsterSpawnBlockLightLimit the maximum allowed block light
         * @return this builder
         * @since 1.0
         */
        public @NonNull Builder monsterSpawnBlockLightLimit(int monsterSpawnBlockLightLimit) {
            this.monsterSpawnBlockLightLimit = monsterSpawnBlockLightLimit;
            return this;
        }

        /**
         * Creates a {@linkplain DimensionType dimension type} using properties set in this {@linkplain Builder
         * builder}.
         *
         * @return the created dimension type
         * @since 1.0
         */
        public @NonNull DimensionType build() {
            return new DimensionType(this.fixedTime, this.hasSkylight, this.hasCeiling, this.ultrwarm, this.natural,
                    this.coordinateScale, this.bedWorks, this.respawnAnchorWorks, this.minY, this.height,
                    this.localHeight, NullabilityUtil.requireNonNull(this.infiniburn, "infiniburn"),
                    NullabilityUtil.requireNonNull(this.effects, "effects"), this.ambientLight,
                    this.piglinSafe, this.hasRaids,
                    NullabilityUtil.requireNonNull(this.monsterSpawnLightLevel, "monster-spawn light level"),
                    this.monsterSpawnBlockLightLimit);
        }
    }
}