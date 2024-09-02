package net.hypejet.jet.data.model.registry.registries.armor.pattern;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntry registry entry} which holds an {@linkplain ArmorTrimPattern armor trim
 * pattern}.
 *
 * @since 1.0
 * @author Codestech
 * @see ArmorTrimPattern
 * @see RegistryEntry
 */
public final class ArmorTrimPatternRegistryEntry extends RegistryEntry<ArmorTrimPattern> {
    /**
     * Constructs the {@linkplain ArmorTrimPattern armor trim-pattern registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the armor trim pattern
     * @since 1.0
     */
    public ArmorTrimPatternRegistryEntry(@NonNull Key key, @NonNull DataPack knownPack,
                                         @NonNull ArmorTrimPattern value) {
        super(key, knownPack, value);
    }
}