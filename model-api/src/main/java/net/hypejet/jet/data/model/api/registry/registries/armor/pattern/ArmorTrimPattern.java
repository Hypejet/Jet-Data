package net.hypejet.jet.data.model.api.registry.registries.armor.pattern;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a Minecraft armor trim pattern.
 *
 * @param asset a texture of a trim pattern model to be rendered on top of an armor
 * @param templateItem an identifier of an item that should be used as a template of the trim
 * @param description a name of the trim pattern that should be displayed on the armor tooltip
 * @param decal whether the trim should be a decal
 * @since 1.0
 * @author Codestech
 */
public record ArmorTrimPattern(@NonNull Key asset, @NonNull Key templateItem, @NonNull Component description,
                               boolean decal) {
    /**
     * Constructs the {@linkplain ArmorTrimPattern armor trim pattern}.
     *
     * @param asset a texture of a trim pattern model to be rendered on top of an armor
     * @param templateItem an item template that should be used for the trim
     * @param description a name of the trim pattern that should be displayed on the armor tooltip
     * @param decal whether the trim should be a decal
     * @since 1.0
     */
    public ArmorTrimPattern {
        NullabilityUtil.requireNonNull(asset, "asset");
        NullabilityUtil.requireNonNull(templateItem, "template item");
        NullabilityUtil.requireNonNull(description, "description");
    }
}