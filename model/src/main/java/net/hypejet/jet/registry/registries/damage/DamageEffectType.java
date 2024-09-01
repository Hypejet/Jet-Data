package net.hypejet.jet.registry.registries.damage;

/**
 * Represents a type of effect played when a player suffers a damage, including a sound which is being played.
 *
 * @since 1.0
 * @author Codestech
 */
public enum DamageEffectType {
    /**
     * An effect type that plays a default hurt sound.
     *
     * @since 1.0
     */
    HURT,
    /**
     * An effect type that plays a thorns hurt sound.
     *
     * @since 1.0
     */
    THORNS,
    /**
     * An effect type that plays a drowning sound.
     *
     * @since 1.0
     */
    DROWNING,
    /**
     * An effect type that plays a single tick of burning sound.
     *
     * @since 1.0
     */
    BURNING,
    /**
     * An effect type that plays a berry bush poke sound.
     *
     * @since 1.0
     */
    POKING,
    /**
     * An effect type that plays a freezing tick sound.
     *
     * @since 1.0
     */
    FREEZING
}