package net.hypejet.jet.data.generator.adapter;

import net.kyori.adventure.key.Key;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a utility for converting {@linkplain ResourceLocation resource locations} into {@linkplain Key keys}.
 *
 * @since 1.0
 * @author Codestech
 * @see Key
 * @see ResourceLocation
 */
public final class IdentifierAdapter {

    private IdentifierAdapter() {}

    /**
     * Converts a {@linkplain ResourceLocation resource location} into a {@linkplain Key key}.
     *
     * @param location the resource location to convert
     * @return the converted key
     * @since 1.0
     */
    public static @NonNull Key convert(@NonNull ResourceLocation location) {
        return Key.key(location.getNamespace(), location.getPath());
    }
}