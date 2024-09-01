package net.hypejet.jet.data.generator.adapter;

import net.hypejet.jet.pack.DataPack;
import net.kyori.adventure.key.Key;
import net.minecraft.server.packs.repository.KnownPack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents something that converts a {@linkplain KnownPack known pack} into a {@linkplain DataPack data pack}.
 *
 * @since 1.0
 * @author Codestech
 * @see DataPack
 * @see KnownPack
 */
public final class DataPackAdapter {

    private DataPackAdapter() {}

    /**
     * Converts a {@linkplain KnownPack known pack} into a {@linkplain DataPack data pack}.
     *
     * @param knownPack the known pack
     * @return the data pack
     * @since 1.0
     */
    public static @NonNull DataPack dataPack(@NonNull KnownPack knownPack) {
        return new DataPack(Key.key(knownPack.namespace(), knownPack.id()), knownPack.version());
    }
}