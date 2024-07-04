package net.hypejet.jet.data;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an entry of a Minecraft registry, which is identified with a {@linkplain Key key}.
 *
 * @since 1.0
 * @author Codestech
 * @see Key
 * @see Keyed
 */
public interface IdentifiedRegistryEntry extends Keyed {
    /**
     * Gets an extracted {@linkplain Key key} of the entry from a registry.
     *
     * @return the extracted key
     * @since 1.0
     */
    @Override
    @NonNull Key key();
}