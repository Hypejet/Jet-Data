package net.hypejet.jet.data.model.api.registry.registries.banner;

import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry} holding a {@linkplain BannerPattern banner pattern}.
 *
 * @since 1.0
 * @author Codestech
 * @see BannerPattern
 * @see DataRegistryEntry
 */
public final class BannerPatternDataRegistryEntry extends DataRegistryEntry<BannerPattern> {
    /**
     * Constructs the {@linkplain BannerPatternDataRegistryEntry banner pattern registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param value the banner pattern
     * @param knownPackInfo an information of a feature pack, which enables the data registry entry
     * @since 1.0
     */
    public BannerPatternDataRegistryEntry(@NonNull Key key, @NonNull BannerPattern value,
                                          @NonNull PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}