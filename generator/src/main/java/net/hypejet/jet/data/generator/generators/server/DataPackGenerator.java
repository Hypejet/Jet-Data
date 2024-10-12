package net.hypejet.jet.data.generator.generators.server;

import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.adapter.PackAdapter;
import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.pack.info.PackInfo;
import net.hypejet.jet.data.model.registry.registries.datapack.DataPackDataRegistryEntry;
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
 * Represents a {@linkplain Generator generator}, which generates all built-in Minecraft {@linkplain DataPack data
 * packs}.
 *
 * @since 1.0
 * @author Codestech
 * @see DataPack
 * @see Generator
 */
public final class DataPackGenerator extends Generator<DataPack> {

    private final PackRepository packRepository;

    /**
     * Constructs the {@linkplain Generator generator}.
     *
     * @param packRepository a pack repository, which contains all available Minecraft data packs
     * @since 1.0
     */
    public DataPackGenerator(@NonNull PackRepository packRepository) {
        super("data-packs", "DataPacks");
        this.packRepository = packRepository;
    }

    @Override
    public @NonNull List<DataPackDataRegistryEntry> generate() {
        List<DataPackDataRegistryEntry> entries = new ArrayList<>();

        for (Pack pack : this.packRepository.getAvailablePacks()) {
            KnownPack knownPack = pack.location().knownPackInfo().orElseThrow();
            Set<Key> requiredFeatures = new HashSet<>();

            for (ResourceLocation name : FeatureFlags.REGISTRY.toNames(pack.getRequestedFeatures()))
                requiredFeatures.add(IdentifierAdapter.convert(name));

            PackInfo packInfo = PackAdapter.convert(knownPack);
            DataPack dataPack = new DataPack(packInfo, Set.copyOf(requiredFeatures));

            entries.add(new DataPackDataRegistryEntry(packInfo.key(), dataPack));
        }

        return List.copyOf(entries);
    }
}