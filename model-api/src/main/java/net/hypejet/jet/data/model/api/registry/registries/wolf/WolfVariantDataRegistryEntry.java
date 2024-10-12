package net.hypejet.jet.data.model.api.registry.registries.wolf;

import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry} which holds a {@linkplain WolfVariant wolf variant}.
 *
 * @since 1.0
 * @author Codestech
 * @see WolfVariant
 * @see DataRegistryEntry
 */
public final class WolfVariantDataRegistryEntry extends DataRegistryEntry<WolfVariant> {
    /**
     * Constructs the {@linkplain WolfVariantDataRegistryEntry wolf-variant data registry entry}.
     *
     * @param key an identifier, which the data registry entry should have
     * @param value the wolf variant
     * @param knownPackInfo an information of a feature pack, which enables the data registry entry
     * @since 1.0
     */
    public WolfVariantDataRegistryEntry(@NonNull Key key, @NonNull WolfVariant value,
                                        @NonNull PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}