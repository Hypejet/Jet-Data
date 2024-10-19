package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.registry.registries.banner.BannerPattern;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import org.checkerframework.checker.nullness.qual.NonNull;

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
        super(new GeneratorName("Banner", "Pattern", "Generator"),
                new ResourceFileSettings("banner-patterns", JetDataJson.createBannerPatternsGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.API, "BannerPatterns"));
        this.registryAccess = NullabilityUtil.requireNonNull(registryAccess, "registry access");
    }

    @Override
    public @NonNull List<DataRegistryEntry<BannerPattern>> generate() {
        Registry<net.minecraft.world.level.block.entity.BannerPattern> registry = this.registryAccess
                .lookupOrThrow(Registries.BANNER_PATTERN);
        return RegistryUtil.createEntries(registry, pattern -> new BannerPattern(
                IdentifierAdapter.convert(pattern.assetId()), pattern.translationKey()
        ));
    }
}