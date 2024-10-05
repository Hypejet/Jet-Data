package net.hypejet.jet.data.model.registry.registries.armor.pattern;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntryData;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntryData registry entry data} which holds an {@linkplain ArmorTrimPattern armor
 * trim pattern}.
 *
 * @since 1.0
 * @author Codestech
 * @see ArmorTrimPattern
 * @see RegistryEntryData
 */
public final class ArmorTrimPatternRegistryEntryData extends RegistryEntryData<ArmorTrimPattern> {
    /**
     * Constructs the {@linkplain ArmorTrimPattern armor trim-pattern registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the armor trim pattern
     * @since 1.0
     */
    public ArmorTrimPatternRegistryEntryData(@NonNull Key key, @NonNull DataPack knownPack,
                                             @NonNull ArmorTrimPattern value) {
        super(key, knownPack, value);
    }
}