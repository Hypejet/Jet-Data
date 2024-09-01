package net.hypejet.jet.registry.registries.damage;

import net.hypejet.jet.pack.DataPack;
import net.hypejet.jet.registry.RegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntry registry entry} which holds a {@linkplain DamageType damage type}.
 *
 * @since 1.0
 * @author Codestech
 * @see DamageType
 * @see RegistryEntry
 */
public final class DamageTypeRegistryEntry extends RegistryEntry<DamageType> {
    /**
     * Constructs the {@linkplain DamageTypeRegistryEntry damage type registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the damage type
     * @since 1.0
     */
    public DamageTypeRegistryEntry(@NonNull Key key, @NonNull DataPack knownPack, @NonNull DamageType value) {
        super(key, knownPack, value);
    }
}