package net.hypejet.jet.data.generator.generators.api;

import com.mojang.datafixers.util.Either;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.adapter.PackAdapter;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.registry.registries.wolf.WolfBiomes;
import net.hypejet.jet.data.model.api.registry.registries.wolf.WolfVariant;
import net.hypejet.jet.data.model.api.registry.registries.wolf.WolfVariantDataRegistryEntry;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a {@linkplain Generator generator} which generates {@linkplain WolfVariant wolf variants}.
 *
 * @since 1.0
 * @author Codestech
 * @see WolfVariant
 * @see Generator
 */
public final class WolfVariantGenerator extends Generator<WolfVariant> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain WolfVariant wolf-variant generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public WolfVariantGenerator(@NonNull RegistryAccess registryAccess) {
        super("wolf-variants", "WolfVariants", true);
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<WolfVariantDataRegistryEntry> generate() {
        List<WolfVariantDataRegistryEntry> entries = new ArrayList<>();

        Registry<net.minecraft.world.entity.animal.WolfVariant> registry = this.registryAccess
                .lookupOrThrow(Registries.WOLF_VARIANT);
        Registry<Biome> biomeRegistry = this.registryAccess.lookupOrThrow(Registries.BIOME);

        registry.forEach(wolfVariant -> {
            ResourceKey<net.minecraft.world.entity.animal.WolfVariant> key = registry.getResourceKey(wolfVariant)
                    .orElseThrow();
            KnownPack knownPack = registry.registrationInfo(key)
                    .flatMap(RegistrationInfo::knownPackInfo)
                    .orElseThrow();

            Either<TagKey<Biome>, List<Holder<Biome>>> unwrappedBiomes = wolfVariant.biomes().unwrap();

            Optional<TagKey<Biome>> optionalTagKey = unwrappedBiomes.left();
            Optional<List<Holder<Biome>>> optionalBiomeHolderList = unwrappedBiomes.right();

            WolfBiomes biomes;

            if (optionalTagKey.isPresent()) {
                TagKey<Biome> biomeTagKey = optionalTagKey.get();
                biomes = new WolfBiomes.TaggedBiomes(IdentifierAdapter.convert(biomeTagKey.location()));
            } else if (optionalBiomeHolderList.isPresent()) {
                List<Holder<Biome>> biomeHolderList = optionalBiomeHolderList.get();

                if (biomeHolderList.size() == 1) {
                    Key biomeKey = RegistryUtil.keyOfHolder(biomeHolderList.getFirst(), biomeRegistry);
                    biomes = new WolfBiomes.SingleBiome(biomeKey);
                } else {
                    Set<Key> biomeKeys = new HashSet<>();
                    for (Holder<Biome> biomeHolder : biomeHolderList)
                        biomeKeys.add(RegistryUtil.keyOfHolder(biomeHolder, biomeRegistry));
                    biomes = new WolfBiomes.Biomes(Set.copyOf(biomeKeys));
                }
            } else {
                throw new IllegalStateException("None of values of \"either\" is present");
            }

            WolfVariant convertedWolfVariant = new WolfVariant(IdentifierAdapter.convert(wolfVariant.angryTexture()),
                    IdentifierAdapter.convert(wolfVariant.tameTexture()),
                    IdentifierAdapter.convert(wolfVariant.angryTexture()),
                    biomes);

            entries.add(new WolfVariantDataRegistryEntry(IdentifierAdapter.convert(key.location()),
                    convertedWolfVariant, PackAdapter.convert(knownPack)));
        });

        return List.copyOf(entries);
    }
}