package net.hypejet.jet.data.biome.grass;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a modifier of Minecraft grass color.
 *
 * @since 1.0
 * @author Codestech
 */
public sealed interface GrassColorModifier permits GrassColorModifierImpl {
    /**
     * Gets a name of the grass color modifier.
     *
     * @return the name
     * @since 1.0
     */
    @NonNull String name();
}