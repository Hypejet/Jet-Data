package net.hypejet.jet.data;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an entry of a Minecraft registry.
 *
 * @since 1.0
 * @author Codestech
 * @see Keyed
 */
public interface RegistryEntry extends Keyed {
    /**
     * Gets an extracted numeric identifier of the entry from a registry.
     *
     * @return the extracted identifier
     * @since 1.0
     */
    int numericId();

    /**
     * Gets an extracted {@linkplain Key key} of the entry from a registry.
     *
     * @return the extracted key
     * @since 1.0
     */
    @Override
    @NotNull Key key();
}