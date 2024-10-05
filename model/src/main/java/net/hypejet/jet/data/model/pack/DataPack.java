package net.hypejet.jet.data.model.pack;

import net.hypejet.jet.data.model.pack.info.PackInfo;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Represents a Minecraft data pack.
 *
 * @param info an information of the data pack
 * @param requiredFeatureFlags identifiers of feature flags, which are needed to enable the data pack
 * @since 1.0
 * @author Codestech
 * @see PackInfo
 */
public record DataPack(@NonNull PackInfo info, @NonNull Set<Key> requiredFeatureFlags) implements Keyed {
    @Override
    public @NotNull Key key() {
        return this.info().key();
    }
}