package net.hypejet.jet.data.model.api.event;

/**
 * Represents a Minecraft game event.
 *
 * @param notificationRadius a block radius within listeners of the game event should be called
 * @since 1.0
 * @author Codestech
 */
public record GameEvent(int notificationRadius) {}