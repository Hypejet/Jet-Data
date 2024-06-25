package net.hypejet.jet.data.block;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Minecraft built-in block.
 *
 * @since 1.0
 * @author Codestech
 * @see Keyed
 */
public sealed interface VanillaBlock extends Keyed permits VanillaBlockImpl {
    /**
     * Gets an identifier of the block.
     *
     * @return the identifier
     * @since 1.0
     * @see Key
     */
    @Override
    @NotNull Key key();
}