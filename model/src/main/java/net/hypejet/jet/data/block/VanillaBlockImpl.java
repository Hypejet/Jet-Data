package net.hypejet.jet.data.block;

import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an implementation of the {@linkplain VanillaBlock vanilla block}.
 *
 * @param key an identifier of the block
 * @since 1.0
 * @author Codestech
 * @see VanillaBlock
 */
record VanillaBlockImpl(@NonNull Key key) implements VanillaBlock {}