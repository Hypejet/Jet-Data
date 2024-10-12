package net.hypejet.jet.data.generator.generators.api;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.ComponentAdapter;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.registry.registries.armor.material.ArmorTrimMaterial;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ArmorTrimMaterialGenerator extends Generator<ArmorTrimMaterial> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain ArmorTrimMaterialGenerator armor trim material generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public ArmorTrimMaterialGenerator(@NonNull RegistryAccess registryAccess) {
        super("armor-trim-materials", "ArmorTrimMaterials", true, JetDataJson.createTrimMaterialsGson());
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<DataRegistryEntry<ArmorTrimMaterial>> generate() {
        Registry<TrimMaterial> registry = this.registryAccess.lookupOrThrow(Registries.TRIM_MATERIAL);
        Registry<Item> itemRegistry = this.registryAccess.lookupOrThrow(Registries.ITEM);

        return RegistryUtil.createEntries(registry, material -> {
            Map<Key, Key> overrideArmorMaterials = new HashMap<>();
            for (Map.Entry<ResourceLocation, String> entry : material.overrideArmorMaterials().entrySet())
                overrideArmorMaterials.put(IdentifierAdapter.convert(entry.getKey()), Key.key(entry.getValue()));

            return new ArmorTrimMaterial(Key.key(material.assetName()),
                    RegistryUtil.keyOfHolder(material.ingredient(), itemRegistry),
                    material.itemModelIndex(), Map.copyOf(overrideArmorMaterials),
                    ComponentAdapter.convert(material.description(), registryAccess));
        });
    }
}