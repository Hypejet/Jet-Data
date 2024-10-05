package net.hypejet.jet.data.model.registry.registries.painting;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntryData;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntryData registry entry data} which holds
 * a {@linkplain PaintingVariant painting variant}.
 *
 * @since 1.0
 * @author Codestech
 */
public final class PaintingVariantRegistryEntryData extends RegistryEntryData<PaintingVariant> {
    /**
     * Constructs the {@linkplain PaintingVariant painting-variant registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the painting variant
     * @since 1.0
     */
    public PaintingVariantRegistryEntryData(@NonNull Key key, @NonNull DataPack knownPack, @NonNull PaintingVariant value) {
        super(key, knownPack, value);
    }
}