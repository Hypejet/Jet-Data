package net.hypejet.jet.data.generator.adapter;

import net.hypejet.jet.data.model.pack.info.PackInfo;
import net.kyori.adventure.key.Key;
import net.minecraft.server.packs.repository.KnownPack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents something that converts Minecraft packs into Jet data packs.
 *
 * @since 1.0
 * @author Codestech
 */
public final class PackAdapter {

    private PackAdapter() {}

    /**
     * Converts a {@linkplain KnownPack known pack} into a {@linkplain PackInfo pack info}.
     *
     * @param knownPack the known pack
     * @return the pack info
     * @since 1.0
     */
    public static @NonNull PackInfo convert(@NonNull KnownPack knownPack) {
        return new PackInfo(Key.key(knownPack.namespace(), knownPack.id()), knownPack.version());
    }
}