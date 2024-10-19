package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.server.registry.registries.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.registries.painting.PaintingVariant;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import org.checkerframework.checker.nullness.qual.NonNull;

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
        super(new GeneratorName("Painting", "Variant", "Generator"),
                new ResourceFileSettings("painting-variants", JetDataJson.createPaintingVariantsGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.API, "PaintingVariants"));
        this.registryAccess = NullabilityUtil.requireNonNull(registryAccess, "registry access");
    }

    @Override
    public @NonNull List<DataRegistryEntry<PaintingVariant>> generate() {
        Registry<net.minecraft.world.entity.decoration.PaintingVariant> registry = this.registryAccess
                .lookupOrThrow(Registries.PAINTING_VARIANT);
        return RegistryUtil.createEntries(registry, variant -> new PaintingVariant(
                IdentifierAdapter.convert(variant.assetId()), variant.height(), variant.width()
        ));
    }
}