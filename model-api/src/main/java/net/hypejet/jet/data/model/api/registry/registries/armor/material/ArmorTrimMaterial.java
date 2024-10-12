package net.hypejet.jet.data.model.api.registry.registries.armor.material;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;

/**
 * Represents a Minecraft armor trim material.
 *
 * @param asset an identifier of a texture of trim color model that should be rendered on top of armors using
 *              the trim material
 * @param ingredient an identifier of an item that should represent the trim material
 * @param itemModelIndex a color index of the trim that should be displayed on the armor item using the trim material
 * @param overrideArmorMaterials assets for different types of armor materials, which override the asset specified
 * @param description a name that should be displayed on the armor tooltip
 * @since 1.0
 * @author Codestech
 */
public record ArmorTrimMaterial(@NonNull Key asset, @NonNull Key ingredient, float itemModelIndex,
                                @Nullable Map<Key, Key> overrideArmorMaterials, @NonNull Component description) {
    /**
     * Constructs the {@linkplain ArmorTrimMaterial armor trim material}.
     *
     * @param asset an identifier of a texture of trim color model that should be rendered on top of armors using
     *              the trim material
     * @param ingredient an identifier of an item that should represent the trim material
     * @param itemModelIndex a color index of the trim that should be displayed on the armor item using the trim
     *                       material
     * @param overrideArmorMaterials assets for different types of armor materials, which override the asset specified
     * @param description a name that should be displayed on the armor tooltip
     * @since 1.0
     */
    public ArmorTrimMaterial {
        NullabilityUtil.requireNonNull(asset, "asset");
        NullabilityUtil.requireNonNull(ingredient, "ingredient");
        NullabilityUtil.requireNonNull(description, "description");

        if (overrideArmorMaterials != null)
            overrideArmorMaterials = Map.copyOf(overrideArmorMaterials);
    }
}
