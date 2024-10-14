package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.adapter.PackAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.server.registry.registries.pack.FeaturePack;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.flag.FeatureFlags;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a {@linkplain Generator generator}, which generates all built-in Minecraft
 * {@linkplain FeaturePack feature packs}.
 *
 * @since 1.0
 * @author Codestech
 * @see FeaturePack
 * @see Generator
 */
public final class FeaturePackGenerator extends Generator<FeaturePack> {

    private final PackRepository packRepository;

    /**
     * Constructs the {@linkplain Generator generator}.
     *
     * @param packRepository a pack repository, which contains all available Minecraft feature packs
     * @since 1.0
     */
    public FeaturePackGenerator(@NonNull PackRepository packRepository) {
        super(new GeneratorName("Feature", "Pack", "Generator"),
                new ResourceFileSettings("feature-packs", JetDataJson.createPlainGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.SERVER, "FeaturePacks"));
        this.packRepository = packRepository;
    }

    @Override
    public @NonNull List<DataRegistryEntry<FeaturePack>> generate() {
        List<DataRegistryEntry<FeaturePack>> entries = new ArrayList<>();

        for (Pack pack : this.packRepository.getAvailablePacks()) {
            KnownPack knownPack = pack.location().knownPackInfo().orElseThrow();
            Set<Key> requiredFeatures = new HashSet<>();

            for (ResourceLocation name : FeatureFlags.REGISTRY.toNames(pack.getRequestedFeatures()))
                requiredFeatures.add(IdentifierAdapter.convert(name));

            PackInfo packInfo = PackAdapter.convert(knownPack);
            FeaturePack featurePack = new FeaturePack(packInfo, Set.copyOf(requiredFeatures));

            entries.add(new DataRegistryEntry<>(packInfo.key(), featurePack, null, null));
        }

        return List.copyOf(entries);
    }
}