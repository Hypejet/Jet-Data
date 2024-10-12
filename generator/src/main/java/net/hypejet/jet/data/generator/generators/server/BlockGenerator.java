package net.hypejet.jet.data.generator.generators.server;

import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.model.server.registry.registries.block.Block;
import net.hypejet.jet.data.model.server.registry.registries.block.BlockRegistryEntry;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a {@linkplain Generator generator} of {@linkplain Block blocks}.
 *
 * @since 1.0
 * @author Codestech
 * @see Block
 * @see Generator
 */
public final class BlockGenerator extends Generator<Block> {
    /**
     * Constructs the {@linkplain BlockGenerator block generator}.
     *
     * @since 1.0
     */
    public BlockGenerator() {
        super("blocks", "Blocks", true);
    }

    @Override
    public @NonNull List<BlockRegistryEntry> generate() {
        List<BlockRegistryEntry> entries = new ArrayList<>();

        Registry<net.minecraft.world.level.block.Block> registry = BuiltInRegistries.BLOCK;
        registry.forEach(block -> {
            ResourceLocation location = registry.getKey(block);
            Key key = IdentifierAdapter.convert(location);

            Set<Key> requiredFeatureFlags = new HashSet<>();
            for (ResourceLocation name : FeatureFlags.REGISTRY.toNames(block.requiredFeatures()))
                requiredFeatureFlags.add(IdentifierAdapter.convert(name));

            entries.add(new BlockRegistryEntry(key, new Block(requiredFeatureFlags), null));
        });

        return List.copyOf(entries);
    }
}