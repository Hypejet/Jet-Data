package net.hypejet.jet.data.model.registry.registries.damage;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntryData;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntryData registry entry data} which holds a {@linkplain DamageType damage type}.
 *
 * @since 1.0
 * @author Codestech
 * @see DamageType
 * @see RegistryEntryData
 */
public final class DamageTypeRegistryEntryData extends RegistryEntryData<DamageType> {
    /**
     * Constructs the {@linkplain DamageTypeRegistryEntryData damage type registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the damage type
     * @since 1.0
     */
    public DamageTypeRegistryEntryData(@NonNull Key key, @NonNull DataPack knownPack, @NonNull DamageType value) {
        super(key, knownPack, value);
    }
}