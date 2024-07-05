package net.hypejet.jet.data.sound;

import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an implementation of {@linkplain SoundEvent sound event}.
 *
 * @param key a key of the sound
 * @param numericId a numeric identifier of the sound event
 * @param range a range within players can receive the sound event
 * @param newSystem whether the sound event should use the new range system
 * @since 1.0
 * @author Codestech
 * @see SoundEvent
 */
record SoundEventImpl(@NonNull Key key, int numericId, float range, boolean newSystem) implements SoundEvent {}