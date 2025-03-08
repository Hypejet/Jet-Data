package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.ReflectionUtil;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.hypejet.jet.data.model.server.registry.registries.block.entity.BlockEntityType;
import net.hypejet.jet.data.model.server.registry.registries.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents {@linkplain Generator a generator}, which generates {@linkplain BlockEntityType block entity types}.
 *
 * @since 1.0
 * @see BlockEntityType
 * @see Generator
 */
public final class BlockEntityTypeGenerator extends Generator<BlockEntityType> {

    private static final Field VALID_BLOCKS_FIELD;

    private final RegistryAccess registryAccess;

    static {
        try {
            Class<?> blockEntityTypeClass = net.minecraft.world.level.block.entity.BlockEntityType.class;
            VALID_BLOCKS_FIELD = blockEntityTypeClass.getDeclaredField("validBlocks");
        } catch (NoSuchFieldException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Constructs the {@linkplain BlockEntityTypeGenerator block entity type generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public BlockEntityTypeGenerator(@NonNull RegistryAccess registryAccess) {
        super(new GeneratorName("Block", "Entity", "Type", "Generator"),
                new ResourceFileSettings("block-entity-types", JetDataJson.createBlockEntityTypeGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.SERVER, "BlockEntityTypes"));
        this.registryAccess = NullabilityUtil.requireNonNull(registryAccess, "registry access");
    }

    @Override
    public @NonNull List<DataRegistryEntry<BlockEntityType>> generate() {
        Registry<Block> blockRegistry = this.registryAccess.lookupOrThrow(Registries.BLOCK);
        return RegistryUtil.createEntries(
                this.registryAccess.lookupOrThrow(Registries.BLOCK_ENTITY_TYPE),
                blockEntityType -> {
                    Set<?> validBlocks = (Set<?>) ReflectionUtil.access(VALID_BLOCKS_FIELD, blockEntityType);
                    Set<Key> validBlockKeys = new HashSet<>();

                    for (Object validBlock : validBlocks) {
                        if (!(validBlock instanceof Block block))
                            throw new IllegalArgumentException("The element is not a valid block");

                        ResourceLocation location = blockRegistry.getKey(block);
                        if (location == null)
                            throw new IllegalArgumentException("Could not find a key of the block");

                        validBlockKeys.add(IdentifierAdapter.convert(location));
                    }

                    return new BlockEntityType(validBlockKeys);
                }
        );
    }
}