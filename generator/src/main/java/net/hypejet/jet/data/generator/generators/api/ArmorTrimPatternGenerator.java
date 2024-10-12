package net.hypejet.jet.data.generator.generators.api;

import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.ComponentAdapter;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.adapter.PackAdapter;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.registry.registries.armor.pattern.ArmorTrimPattern;
import net.hypejet.jet.data.model.registry.registries.armor.pattern.ArmorTrimPatternDataRegistryEntry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@linkplain Generator generator} which generates {@linkplain ArmorTrimPattern armor trim
 * patterns}.
 *
 * @since 1.0
 * @author Codestech
 * @see ArmorTrimPattern
 * @see Generator
 */
public final class ArmorTrimPatternGenerator extends Generator<ArmorTrimPattern> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain ArmorTrimPatternGenerator armor trim pattern generator}.
     *
     * @since 1.0
     */
    public ArmorTrimPatternGenerator(@NonNull RegistryAccess registryAccess) {
        super("armor-trim-patterns", "ArmorTrimPatterns");
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<ArmorTrimPatternDataRegistryEntry> generate(@NonNull Logger logger) {
        List<ArmorTrimPatternDataRegistryEntry> entries = new ArrayList<>();

        Registry<TrimPattern> registry = this.registryAccess.lookupOrThrow(Registries.TRIM_PATTERN);
        Registry<Item> itemRegistry = this.registryAccess.lookupOrThrow(Registries.ITEM);

        registry.forEach(pattern -> {
            ResourceKey<TrimPattern> key = registry.getResourceKey(pattern).orElseThrow();
            KnownPack knownPack = registry.registrationInfo(key)
                    .flatMap(RegistrationInfo::knownPackInfo)
                    .orElseThrow();

            ArmorTrimPattern armorTrimPattern = new ArmorTrimPattern(IdentifierAdapter.convert(pattern.assetId()),
                    RegistryUtil.keyOfHolder(pattern.templateItem(), itemRegistry),
                    ComponentAdapter.convert(pattern.description(), registryAccess),
                    pattern.decal());

            entries.add(new ArmorTrimPatternDataRegistryEntry(IdentifierAdapter.convert(key.location()),
                    armorTrimPattern, PackAdapter.convert(knownPack)));
        });

        return List.copyOf(entries);
    }
}