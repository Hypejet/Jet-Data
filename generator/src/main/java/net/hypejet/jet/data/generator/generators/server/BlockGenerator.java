package net.hypejet.jet.data.generator.generators.server;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.server.registry.registries.block.Block;
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
        super("blocks", "Blocks", true, JetDataJson.createBlocksGson());
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<DataRegistryEntry<Block>> generate() {
        return RegistryUtil.createEntries(this.registryAccess.lookupOrThrow(Registries.BLOCK), block -> {
            Set<Key> requiredFeatureFlags = new HashSet<>();
            for (ResourceLocation name : FeatureFlags.REGISTRY.toNames(block.requiredFeatures()))
                requiredFeatureFlags.add(IdentifierAdapter.convert(name));
            return new Block(Set.copyOf(requiredFeatureFlags));
        });
    }
}