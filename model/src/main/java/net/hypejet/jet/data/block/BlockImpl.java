package net.hypejet.jet.data.block;

import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an implementation of the {@linkplain Block block}.
 *
 * @param key a key of the block
 * @param identifier a numeric identifier of the block
 * @since 1.0
 * @author Codestech
 * @see Block
 */
record BlockImpl(@NonNull Key key, int identifier) implements Block {}