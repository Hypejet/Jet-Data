package net.hypejet.jet.data.model.server.registry.registries.pack;

import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.server.pack.FeaturePack;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry}, which holds a {@linkplain FeaturePack feature pack}.
 *
 * @since 1.0
 * @author Codestech
 * @see FeaturePack
 * @see DataRegistryEntry
 */
public final class FeaturePackRegistryEntry extends DataRegistryEntry<FeaturePack> {
    /**
     * Constructs the {@linkplain FeaturePackRegistryEntry feature pack data registry entry}.
     *
     * @param key an identifier, which the data registry entry should have
     * @param value the feature pack
     * @param knownPackInfo an information of a feature pack, which enables the data registry entry
     * @since 1.0
     */
    public FeaturePackRegistryEntry(@NonNull Key key, @NonNull FeaturePack value, @Nullable PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}