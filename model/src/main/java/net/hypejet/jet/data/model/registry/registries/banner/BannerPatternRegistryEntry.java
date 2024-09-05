package net.hypejet.jet.data.model.registry.registries.banner;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntry registry entry} holding a {@linkplain BannerPattern banner pattern}.
 *
 * @since 1.0
 * @author Codestech
 * @see BannerPattern
 * @see RegistryEntry
 */
public final class BannerPatternRegistryEntry extends RegistryEntry<BannerPattern> {
    /**
     * Constructs the {@linkplain BannerPatternRegistryEntry banner pattern registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the banner pattern
     * @since 1.0
     */
    public BannerPatternRegistryEntry(@NonNull Key key, @NonNull DataPack knownPack, @NonNull BannerPattern value) {
        super(key, knownPack, value);
    }
}