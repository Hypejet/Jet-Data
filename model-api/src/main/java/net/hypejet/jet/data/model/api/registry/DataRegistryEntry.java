package net.hypejet.jet.data.model.api.registry;

import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents a {@linkplain Keyed keyed} entry of a Minecraft registry.
 *
 * <p>This was made as an interface, since it would be impossible to create an instance without knowing type
 * of the value while deserializing.</p>
 *
 * @param <V> a type of value that the entry holds
 * @since 1.0
 * @author Codestech
 * @see Keyed
 */
public abstract class DataRegistryEntry<V> implements Keyed {

    private final Key key;
    private final V value;
    private final PackInfo knownPackInfo;

    /**
     * Constructs the {@linkplain DataRegistryEntry data registry entry}.
     *
     * @param key an identifier, which the data registry entry should have
     * @param value the value
     * @param knownPackInfo an information of a feature pack, which enables the data registry entry
     * @since 1.0
     */
    public DataRegistryEntry(@NonNull Key key, @NonNull V value, @Nullable PackInfo knownPackInfo) {
        this.key = NullabilityUtil.requireNonNull(key, "key");
        this.value = NullabilityUtil.requireNonNull(value, "value");
        this.knownPackInfo = knownPackInfo;
    }

    /**
     * Gets an identifier of this registry entry.
     *
     * @return the identifier
     * @since 1.0
     */
    @Override
    public @NonNull Key key() {
        return this.key;
    }

    /**
     * Gets a value of this registry entry.
     *
     * @return the value
     * @since 1.0
     */
    public @NonNull V value() {
        return this.value;
    }

    /**
     * Gets an information of a feature pack, which enables this registry entry.
     *
     * @return the information
     * @since 1.0
     */
    public @Nullable PackInfo knownPackInfo() {
        return this.knownPackInfo;
    }
}