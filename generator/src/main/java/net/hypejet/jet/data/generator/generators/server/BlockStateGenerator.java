package net.hypejet.jet.data.generator.generators.server;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.server.registry.registries.block.state.BlockState;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import org.checkerframework.checker.nullness.qual.NonNull;

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
        super("block-states", "BlockStates", false, JetDataJson.createBlockStatesGson());
    }

    @Override
    public @NonNull List<DataRegistryEntry<BlockState>> generate() {
        List<DataRegistryEntry<BlockState>> entries = new ArrayList<>();

        Registry<Block> registry = BuiltInRegistries.BLOCK;
        for (Block block : registry) {
            Key key = IdentifierAdapter.convert(registry.getKey(block));
            block.getStateDefinition().getPossibleStates().forEach(state -> {
                Map<String, String> properties = new HashMap<>();
                for (Map.Entry<Property<?>, Comparable<?>> entry : state.getValues().entrySet())
                    properties.put(entry.getKey().getName(), entry.getValue().toString());
                entries.add(Block.getId(state), new DataRegistryEntry<>(key, new BlockState(properties), null, null));
            });
        }

        return List.copyOf(entries);
    }
}