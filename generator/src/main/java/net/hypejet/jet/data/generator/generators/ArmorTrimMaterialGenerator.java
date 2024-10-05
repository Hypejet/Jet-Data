package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.ComponentAdapter;
import net.hypejet.jet.data.generator.adapter.DataPackAdapter;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.registry.registries.armor.material.ArmorTrimMaterial;
import net.hypejet.jet.data.model.registry.registries.armor.material.ArmorTrimMaterialRegistryEntryData;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.ArrayList;
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
        super("armor-trim-materials", "ArmorTrimMaterials");
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<ArmorTrimMaterialRegistryEntryData> generate(@NonNull Logger logger) {
        List<ArmorTrimMaterialRegistryEntryData> entries = new ArrayList<>();

        Registry<TrimMaterial> registry = this.registryAccess.registryOrThrow(Registries.TRIM_MATERIAL);
        Registry<Item> itemRegistry = this.registryAccess.registryOrThrow(Registries.ITEM);
        Registry<ArmorMaterial> armorMaterialRegistry = this.registryAccess.registryOrThrow(Registries.ARMOR_MATERIAL);

        registry.forEach(material -> {
            ResourceKey<TrimMaterial> key = registry.getResourceKey(material).orElseThrow();
            KnownPack knownPack = registry.registrationInfo(key)
                    .flatMap(RegistrationInfo::knownPackInfo)
                    .orElseThrow();

            Map<Key, Key> overrideArmorMaterials = new HashMap<>();
            for (Map.Entry<Holder<ArmorMaterial>, String> entry : material.overrideArmorMaterials().entrySet()) {
                Key armorMaterialKey = RegistryUtil.keyOfHolder(entry.getKey(), armorMaterialRegistry);
                Key assetKey = Key.key(entry.getValue());
                overrideArmorMaterials.put(armorMaterialKey, assetKey);
            }

            ArmorTrimMaterial convertedMaterial = new ArmorTrimMaterial(Key.key(material.assetName()),
                    RegistryUtil.keyOfHolder(material.ingredient(), itemRegistry),
                    material.itemModelIndex(), Map.copyOf(overrideArmorMaterials),
                    ComponentAdapter.convert(material.description(), registryAccess));

            entries.add(new ArmorTrimMaterialRegistryEntryData(IdentifierAdapter.convert(key.location()),
                    DataPackAdapter.convert(knownPack), convertedMaterial));
        });

        return List.copyOf(entries);
    }
}