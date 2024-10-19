package net.hypejet.jet.data.model.api.registries.banner;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a Minecraft banner pattern.
 *
 * @param asset an identifier of a texture of the pattern
 * @param translationKey a translation key representing the banner pattern
 * @since 1.0
 * @author Codestech
 */
public record BannerPattern(@NonNull Key asset, @NonNull String translationKey) {
    /**
     * Constructs the {@linkplain BannerPattern banner pattern}.
     *
     * @param asset an identifier of a texture of the pattern
     * @param translationKey a translation key representing the banner pattern
     * @since 1.0
     * @author Codestech
     */
    public BannerPattern {
        NullabilityUtil.requireNonNull(asset, "asset");
        NullabilityUtil.requireNonNull(translationKey, "translation key");
    }
}