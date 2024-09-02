package net.hypejet.jet.data.model.registry.registries.wolf;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntry registry entry} which holds a {@linkplain WolfVariant wolf variant}.
 *
 * @since 1.0
 * @author Codestech
 * @see WolfVariant
 * @see RegistryEntry
 */
public final class WolfVariantRegistryEntry extends RegistryEntry<WolfVariant> {
    /**
     * Constructs the {@linkplain WolfVariantRegistryEntry wolf-variant registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the wolf variant
     * @since 1.0
     */
    public WolfVariantRegistryEntry(@NonNull Key key, @NonNull DataPack knownPack, @NonNull WolfVariant value) {
        super(key, knownPack, value);
    }
}