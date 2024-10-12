package net.hypejet.jet.data.generator.generators.server;

import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.model.registry.registries.block.state.BlockState;
import net.hypejet.jet.data.model.registry.registries.block.state.BlockStateRegistryEntry;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a {@linkplain Generator generator} of {@linkplain BlockState block states}.
 *
 * @since 1.0
 * @author Codestech
 * @see BlockState
 * @see Generator
 */
public final class BlockStateGenerator extends Generator<BlockState> {

    /**
     * Constructs the {@linkplain BlockStateGenerator block state generator}.
     *
     * @since 1.0
     */
    public BlockStateGenerator() {
        super("block-states", null);
    }

    @Override
    public @NonNull List<BlockStateRegistryEntry> generate(@NonNull Logger logger) {
        List<BlockStateRegistryEntry> entries = new ArrayList<>();

        Registry<Block> registry = BuiltInRegistries.BLOCK;
        for (Block block : registry) {
            ResourceLocation location = registry.getKey(block);
            Key key = IdentifierAdapter.convert(location);

            block.getStateDefinition().getPossibleStates().forEach(state -> {
                Map<String, String> properties = new HashMap<>();
                for (Map.Entry<Property<?>, Comparable<?>> entry : state.getValues().entrySet())
                    properties.put(entry.getKey().getName(), entry.getValue().toString());
                entries.add(Block.getId(state), new BlockStateRegistryEntry(key, new BlockState(properties), null));
            });
        }

        return List.copyOf(entries);
    }
}