package net.hypejet.jet.data.generator.generators.api;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.registry.registries.painting.PaintingVariant;
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
        super("painting-variants", "PaintingVariants", true, JetDataJson.createPaintingVariantsGson());
        this.registryAccess = registryAccess;
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