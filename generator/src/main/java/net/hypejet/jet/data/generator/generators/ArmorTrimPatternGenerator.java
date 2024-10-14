package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.ComponentAdapter;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.registry.registries.armor.pattern.ArmorTrimPattern;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import org.checkerframework.checker.nullness.qual.NonNull;

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
        super(new GeneratorName("Armor", "Trim", "Pattern", "Generator"),
                new ResourceFileSettings("armor-trim-patterns", JetDataJson.createTrimPatternsGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.API, "ArmorTrimPatterns"));
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<DataRegistryEntry<ArmorTrimPattern>> generate() {
        Registry<TrimPattern> registry = this.registryAccess.lookupOrThrow(Registries.TRIM_PATTERN);
        Registry<Item> itemRegistry = this.registryAccess.lookupOrThrow(Registries.ITEM);
        return RegistryUtil.createEntries(registry, pattern ->
                new ArmorTrimPattern(IdentifierAdapter.convert(pattern.assetId()),
                        RegistryUtil.keyOfHolder(pattern.templateItem(), itemRegistry),
                        ComponentAdapter.convert(pattern.description(), registryAccess),
                        pattern.decal())
        );
    }
}