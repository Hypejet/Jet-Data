package net.hypejet.jet.data.biome.grass;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an implementation of {@linkplain GrassColorModifier grass color modifier}.
 *
 * @param name a name of the color modifier
 * @since 1.0
 * @author Codestech
 * @see GrassColorModifier
 */
record GrassColorModifierImpl(@NonNull String name) implements GrassColorModifier {}