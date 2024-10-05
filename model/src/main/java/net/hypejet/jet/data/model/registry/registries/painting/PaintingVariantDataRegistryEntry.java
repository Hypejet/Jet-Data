package net.hypejet.jet.data.model.registry.registries.painting;

import net.hypejet.jet.data.model.pack.info.PackInfo;
import net.hypejet.jet.data.model.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry} which holds
 * a {@linkplain PaintingVariant painting variant}.
 *
 * @since 1.0
 * @author Codestech
 * @see PaintingVariant
 * @see DataRegistryEntry
 */
public final class PaintingVariantDataRegistryEntry extends DataRegistryEntry<PaintingVariant> {
    /**
     * Constructs the {@linkplain PaintingVariant painting-variant registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param value the painting variant
     * @param knownPackInfo an information of a data pack, which enables the data registry entry
     * @since 1.0
     */
    public PaintingVariantDataRegistryEntry(@NonNull Key key, @NonNull PaintingVariant value,
                                            @NonNull PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}