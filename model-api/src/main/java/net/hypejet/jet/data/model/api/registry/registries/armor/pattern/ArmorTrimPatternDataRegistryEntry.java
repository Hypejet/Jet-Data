package net.hypejet.jet.data.model.api.registry.registries.armor.pattern;

import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry} which holds an {@linkplain ArmorTrimPattern armor
 * trim pattern}.
 *
 * @since 1.0
 * @author Codestech
 * @see ArmorTrimPattern
 * @see DataRegistryEntry
 */
public final class ArmorTrimPatternDataRegistryEntry extends DataRegistryEntry<ArmorTrimPattern> {
    /**
     * Constructs the {@linkplain ArmorTrimPattern armor trim-pattern registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param value the armor trim pattern
     * @param knownPackInfo an information of a feature pack, which enables the data registry entry
     * @since 1.0
     */
    public ArmorTrimPatternDataRegistryEntry(@NonNull Key key, @NonNull ArmorTrimPattern value,
                                             @NonNull PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}