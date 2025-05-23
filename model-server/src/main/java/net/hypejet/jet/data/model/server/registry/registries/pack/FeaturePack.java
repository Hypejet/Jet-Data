package net.hypejet.jet.data.model.server.registry.registries.pack;

import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Represents a Minecraft feature pack.
 *
 * @param info an information of the feature pack
 * @param requiredFeatureFlags identifiers of feature flags, which are needed to enable the feature pack
 * @since 1.0
 * @author Codestech
 * @see PackInfo
 */
public record FeaturePack(@NonNull PackInfo info, @NonNull Set<Key> requiredFeatureFlags) implements Keyed {
    /**
     * Constructs the {@linkplain FeaturePack feature pack}.
     *
     * @param info an information of the feature pack
     * @param requiredFeatureFlags identifiers of feature flags, which are needed to enable the feature pack
     * @since 1.0
     */
    public FeaturePack {
        NullabilityUtil.requireNonNull(info, "info");
        NullabilityUtil.requireNonNull(requiredFeatureFlags, "required feature flags");
    }

    @Override
    public @NotNull Key key() {
        return this.info().key();
    }
}