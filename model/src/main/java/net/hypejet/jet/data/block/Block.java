package net.hypejet.jet.data.block;

import net.hypejet.jet.data.NumberIdentifiedRegistryEntry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a Minecraft block.
 *
 * @since 1.0
 * @author Codestech
 * @see Keyed
 */
public sealed interface Block extends NumberIdentifiedRegistryEntry permits BlockImpl {
    /**
     * Gets a {@linkplain Key key} of the block.
     *
     * @return the key
     * @since 1.0
     * @see Key
     */
    @Override
    @NonNull Key key();

    /**
     * Gets a numeric identifier of the block.
     *
     * @return the identifier
     * @since 1.0
     */
    int numericId();
}