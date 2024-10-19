package net.hypejet.jet.data.model.api.registries.damage;

/**
 * Represents a type of death messages used when players or pets die.
 *
 * @since 1.0
 * @author Codsetech
 */
public enum DeathMessageType {
    /**
     * A death message type that uses a standard death message logic.
     *
     * @since 1.0
     */
    DEFAULT,
    /**
     * A death message type that uses fall damage messages.
     *
     * @since 1.0
     */
    FALL_VARIANTS,
    /**
     * A death message type that shows the {@code Intentional game design} death message.
     *
     * @since 1.0
     */
    INTENTIONAL_GAME_DESIGN
}