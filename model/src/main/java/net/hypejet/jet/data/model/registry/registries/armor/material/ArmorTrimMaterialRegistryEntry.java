package net.hypejet.jet.data.model.registry.registries.armor.material;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntry registry entry} which contains a {@linkplain ArmorTrimMaterial armor
 * trim material}.
 *
 * @since 1.0
 * @author Codestech
 * @see ArmorTrimMaterial
 * @see RegistryEntry
 */
public final class ArmorTrimMaterialRegistryEntry extends RegistryEntry<ArmorTrimMaterial> {
    /**
     * Constructs the {@linkplain ArmorTrimMaterialRegistryEntry armor trim material registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the armor trim material
     * @since 1.0
     */
    public ArmorTrimMaterialRegistryEntry(@NonNull Key key, @NonNull DataPack knownPack,
                                          @NonNull ArmorTrimMaterial value) {
        super(key, knownPack, value);
    }
}