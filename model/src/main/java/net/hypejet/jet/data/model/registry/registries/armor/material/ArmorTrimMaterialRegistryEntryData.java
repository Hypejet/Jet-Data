package net.hypejet.jet.data.model.registry.registries.armor.material;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntryData;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntryData registry entry data} which contains a {@linkplain ArmorTrimMaterial armor
 * trim material}.
 *
 * @since 1.0
 * @author Codestech
 * @see ArmorTrimMaterial
 * @see RegistryEntryData
 */
public final class ArmorTrimMaterialRegistryEntryData extends RegistryEntryData<ArmorTrimMaterial> {
    /**
     * Constructs the {@linkplain ArmorTrimMaterialRegistryEntryData armor trim material registry entry data}.
     *
     * @param key an identifier, which the registry entry data should have
     * @param knownPack a data pack, which should enable the registry entry data
     * @param value the armor trim material
     * @since 1.0
     */
    public ArmorTrimMaterialRegistryEntryData(@NonNull Key key, @NonNull DataPack knownPack,
                                              @NonNull ArmorTrimMaterial value) {
        super(key, knownPack, value);
    }
}