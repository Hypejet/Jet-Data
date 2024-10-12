package net.hypejet.jet.data.model.api.pack;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an information of a Minecraft pack.
 *
 * @param key a key of the pack
 * @param version a version of the pack
 * @since 1.0
 * @author Codestech
 */
public record PackInfo(@NonNull Key key, @NonNull String version) implements Keyed {}