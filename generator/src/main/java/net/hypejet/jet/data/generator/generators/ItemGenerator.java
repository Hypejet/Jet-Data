package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.server.registry.registries.item.Item;
import net.kyori.adventure.key.Key;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a {@linkplain Generator generator} of {@linkplain Item items}.
 *
 * @since 1.0
 * @author Codestech
 * @see Item
 * @see net.minecraft.world.entity.animal.Panda.Gene
 */
public final class ItemGenerator extends Generator<Item> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain ItemGenerator item generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public ItemGenerator(@NonNull RegistryAccess registryAccess) {
        super(new GeneratorName("Item", "Generator"),
                new ResourceFileSettings("items", JetDataJson.createItemsGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.API, "Items"));
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<DataRegistryEntry<Item>> generate() {
        return RegistryUtil.createEntries(this.registryAccess.lookupOrThrow(Registries.ITEM),item -> {
            Set<Key> requiredFeatureFlags = new HashSet<>();
            for (ResourceLocation name : FeatureFlags.REGISTRY.toNames(item.requiredFeatures()))
                requiredFeatureFlags.add(IdentifierAdapter.convert(name));
            return new Item(Set.copyOf(requiredFeatureFlags));
        });
    }
}