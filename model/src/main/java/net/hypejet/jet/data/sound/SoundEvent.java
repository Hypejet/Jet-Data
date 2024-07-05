package net.hypejet.jet.data.sound;

import net.hypejet.jet.data.NumberIdentifiedRegistryEntry;

/**
 * Represents a Minecraft sound event.
 *
 * @since 1.0
 * @author Codestech
 * @see NumberIdentifiedRegistryEntry
 */
public sealed interface SoundEvent extends NumberIdentifiedRegistryEntry permits SoundEventImpl {
    /**
     * Gets a range within players can receive this sound event.
     *
     * @return the range
     * @since 1.0
     */
    float range();

    /**
     * Gets whether this sound event uses new range system.
     *
     * @return true if this sound event uses new range system, false otherwise
     * @since 1.0
     */
    boolean newSystem();
}