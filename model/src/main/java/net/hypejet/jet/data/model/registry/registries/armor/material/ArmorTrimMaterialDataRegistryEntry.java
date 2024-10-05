package net.hypejet.jet.data.model.registry.registries.armor.material;

import net.hypejet.jet.data.model.pack.info.PackInfo;
import net.hypejet.jet.data.model.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry} which contains a {@linkplain ArmorTrimMaterial armor
 * trim material}.
 *
 * @since 1.0
 * @author Codestech
 * @see ArmorTrimMaterial
 * @see DataRegistryEntry
 */
public final class ArmorTrimMaterialDataRegistryEntry extends DataRegistryEntry<ArmorTrimMaterial> {
    /**
     * Constructs the {@linkplain ArmorTrimMaterialDataRegistryEntry armor trim material data registry entry}.
     *
     * @param key an identifier, which the data registry entry should have
     * @param value the armor trim material
     * @param knownPackInfo an information of a data pack, which enables the data registry entry
     * @since 1.0
     */
    public ArmorTrimMaterialDataRegistryEntry(@NonNull Key key, @NonNull ArmorTrimMaterial value,
                                              @NonNull PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}