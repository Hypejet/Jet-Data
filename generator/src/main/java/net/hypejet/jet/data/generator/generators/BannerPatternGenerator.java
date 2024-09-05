package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.DataPackAdapter;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.model.registry.registries.banner.BannerPattern;
import net.hypejet.jet.data.model.registry.registries.banner.BannerPatternRegistryEntry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.repository.KnownPack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@linkplain Generator generator} which generates {@linkplain BannerPattern banner patterns}.
 *
 * @since 1.0
 * @author Codestech
 * @see BannerPattern
 * @see Generator
 */
public final class BannerPatternGenerator extends Generator<BannerPattern> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain BannerPatternGenerator banner pattern generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public BannerPatternGenerator(@NonNull RegistryAccess registryAccess) {
        super("banner-patterns", "BannerPatterns");
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<BannerPatternRegistryEntry> generate(@NonNull Logger logger) {
        List<BannerPatternRegistryEntry> entries = new ArrayList<>();
        Registry<net.minecraft.world.level.block.entity.BannerPattern> registry = this.registryAccess
                .registryOrThrow(Registries.BANNER_PATTERN);

        registry.forEach(pattern -> {
            ResourceKey<net.minecraft.world.level.block.entity.BannerPattern> key = registry.getResourceKey(pattern)
                    .orElseThrow();
            KnownPack knownPack = registry.registrationInfo(key)
                    .flatMap(RegistrationInfo::knownPackInfo)
                    .orElseThrow();

            BannerPattern bannerPattern = new BannerPattern(IdentifierAdapter.convert(pattern.assetId()),
                    pattern.translationKey());
            entries.add(new BannerPatternRegistryEntry(IdentifierAdapter.convert(key.location()),
                    DataPackAdapter.convert(knownPack), bannerPattern));
        });

        return List.copyOf(entries);
    }
}