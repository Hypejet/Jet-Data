package net.hypejet.jet.data.generator.generators;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.ImmutableIntArray;
import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.server.registry.registries.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.hypejet.jet.data.model.server.registry.registries.block.Block;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain BlockGenerator block generator}.
     *
     * @since 1.0
     */
    public BlockGenerator(@NonNull RegistryAccess registryAccess) {
        super(new GeneratorName("Block", "Generator"),
                new ResourceFileSettings("blocks", JetDataJson.createBlocksGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.API, "Blocks"));
        this.registryAccess = NullabilityUtil.requireNonNull(registryAccess, "registry access");
    }

    @Override
    public @NonNull List<DataRegistryEntry<Block>> generate() {
        Map<Key, Key> blockToBlockEntityTypeMap = new HashMap<>();

        Registry<net.minecraft.world.level.block.Block> registry = this.registryAccess.lookupOrThrow(Registries.BLOCK);
        Registry<BlockEntityType<?>> entityRegistry = this.registryAccess.lookupOrThrow(Registries.BLOCK_ENTITY_TYPE);

        for (BlockEntityType<?> entityType : entityRegistry) {
            ResourceLocation resourceLocation = entityRegistry.getKey(entityType);
            if (resourceLocation == null)
                throw new IllegalStateException("Could not find a resource location of the block entity type");

            Key key = IdentifierAdapter.convert(resourceLocation);
            for (Key blockKey : BlockEntityTypeGenerator.validBlockKeys(entityType, registry))
                blockToBlockEntityTypeMap.put(blockKey, key);
        }

        return RegistryUtil.createEntries(registry, block -> {
            ResourceLocation resourceLocation = registry.getKey(block);
            if (resourceLocation == null)
                throw new IllegalStateException("Could not find a resource location of the block");

            Set<Key> requiredFeatureFlags = new HashSet<>();
            for (ResourceLocation name : FeatureFlags.REGISTRY.toNames(block.requiredFeatures()))
                requiredFeatureFlags.add(IdentifierAdapter.convert(name));

            int defaultBlockStateId = net.minecraft.world.level.block.Block.getId(block.defaultBlockState());

            ImmutableList<BlockState> possibleBlockStates = block.getStateDefinition().getPossibleStates();
            int[] possibleStateIdentifiers = new int[possibleBlockStates.size()];

            for (int index = 0; index < possibleStateIdentifiers.length; index++) {
                BlockState blockState = possibleBlockStates.get(index);
                possibleStateIdentifiers[index] = net.minecraft.world.level.block.Block.getId(blockState);
            }

            Key blockKey = IdentifierAdapter.convert(resourceLocation);
            Key blockEntityTypeKey = blockToBlockEntityTypeMap.get(blockKey);

            return new Block(
                    Set.copyOf(requiredFeatureFlags), defaultBlockStateId,
                    ImmutableIntArray.copyOf(possibleStateIdentifiers),
                    blockEntityTypeKey
            );
        });
    }
}