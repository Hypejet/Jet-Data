package net.hypejet.jet.data.model.registry.registries.wolf;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntryData;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntryData registry entry data} which holds a {@linkplain WolfVariant wolf variant}.
 *
 * @since 1.0
 * @author Codestech
 * @see WolfVariant
 * @see RegistryEntryData
 */
public final class WolfVariantRegistryEntryData extends RegistryEntryData<WolfVariant> {
    /**
     * Constructs the {@linkplain WolfVariantRegistryEntryData wolf-variant registry entry data}.
     *
     * @param key an identifier, which the registry entry data should have
     * @param knownPack a data pack, which should enable the registry entry data
     * @param value the wolf variant
     * @since 1.0
     */
    public WolfVariantRegistryEntryData(@NonNull Key key, @NonNull DataPack knownPack, @NonNull WolfVariant value) {
        super(key, knownPack, value);
    }
}