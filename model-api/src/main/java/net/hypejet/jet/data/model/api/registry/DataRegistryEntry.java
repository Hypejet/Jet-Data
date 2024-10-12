package net.hypejet.jet.data.model.api.registry;

import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a {@linkplain Keyed keyed} entry of a Minecraft registry.
 *
 * @param key an identifier, which the data registry entry should have
 * @param value the value
 * @param tags a collection of tags, which the value is tagged with, {@code null} if the value is not tagged
 * @param knownPackInfo an information of a feature pack, which enables the data registry entry, {@code null}
 *                      if no feature pack can enable the entry
 * @since 1.0
 * @param <V> a type of value that the entry holds
 * @author Codestech
 * @see Keyed
 */
public record DataRegistryEntry<V>(@NonNull Key key, @NonNull V value, @Nullable Collection<Key> tags,
                                   @Nullable PackInfo knownPackInfo) implements Keyed {
    /**
     * Constructs the {@linkplain DataRegistryEntry data registry entry}.
     *
     */
    public DataRegistryEntry {
        NullabilityUtil.requireNonNull(key, "key");
        NullabilityUtil.requireNonNull(value, "value");
        if (tags != null) tags = Set.copyOf(tags);
    }
}