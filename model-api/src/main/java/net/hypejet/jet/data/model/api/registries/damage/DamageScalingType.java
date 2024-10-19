package net.hypejet.jet.data.model.api.registries.damage;

/**
 * Represents a type of difficulty-based damage scaling.
 *
 * @since 1.0
 * @author Codestech
 */
public enum DamageScalingType {
    /**
     * A type of scaling that never scales the damage.
     *
     * @since 1.0
     */
    NEVER,
    /**
     * A type of scaling that scales damage when it was caused by a living entity which is not a player.
     *
     * @since 1.0
     */
    WHEN_CAUSED_BY_LIVING_NON_PLAYER,
    /**
     * A type of scaling that always scales the damage.
     *
     * @since 1.0
     */
    ALWAYS
}