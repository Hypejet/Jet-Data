package net.hypejet.jet.data.generator.generators.api;

import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.adapter.PackAdapter;
import net.hypejet.jet.data.model.registry.registries.painting.PaintingVariant;
import net.hypejet.jet.data.model.registry.registries.painting.PaintingVariantDataRegistryEntry;
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
 * Represents a {@linkplain Generator generator} which generates {@linkplain PaintingVariant painting variants}.
 *
 * @since 1.0
 * @author Codestech
 * @see PaintingVariant
 * @see Generator
 */
public final class PaintingVariantGenerator extends Generator<PaintingVariant> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain PaintingVariantGenerator painting variant generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public PaintingVariantGenerator(@NonNull RegistryAccess registryAccess) {
        super("painting-variants", "PaintingVariants");
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<PaintingVariantDataRegistryEntry> generate(@NonNull Logger logger) {
        List<PaintingVariantDataRegistryEntry> entries = new ArrayList<>();
        Registry<net.minecraft.world.entity.decoration.PaintingVariant> registry = this.registryAccess
                .registryOrThrow(Registries.PAINTING_VARIANT);

        registry.forEach(variant -> {
            ResourceKey<net.minecraft.world.entity.decoration.PaintingVariant> key = registry.getResourceKey(variant)
                    .orElseThrow();
            KnownPack knownPack = registry.registrationInfo(key)
                    .flatMap(RegistrationInfo::knownPackInfo)
                    .orElseThrow();

            PaintingVariant convertedVariant = new PaintingVariant(IdentifierAdapter.convert(variant.assetId()),
                    variant.height(), variant.width());
            entries.add(new PaintingVariantDataRegistryEntry(IdentifierAdapter.convert(key.location()),
                    convertedVariant, PackAdapter.convert(knownPack)));
        });

        return List.copyOf(entries);
    }
}