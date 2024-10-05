package net.hypejet.jet.data.model.registry.registries.damage;

import net.hypejet.jet.data.model.pack.info.PackInfo;
import net.hypejet.jet.data.model.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry} which holds a {@linkplain DamageType damage type}.
 *
 * @since 1.0
 * @author Codestech
 * @see DamageType
 * @see DataRegistryEntry
 */
public final class DamageTypeDataRegistryEntry extends DataRegistryEntry<DamageType> {
    /**
     * Constructs the {@linkplain DamageTypeDataRegistryEntry damage type registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param value the damage type
     * @param knownPackInfo an information of a data pack, which enables the data registry entry
     * @since 1.0
     */
    public DamageTypeDataRegistryEntry(@NonNull Key key, @NonNull DamageType value, @NonNull PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}