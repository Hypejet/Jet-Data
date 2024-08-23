package net.hypejet.jet.registry;

import net.hypejet.jet.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

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
public abstract class RegistryEntry<V> implements Keyed {

    private final Key key;
    private final V value;

    /**
     * Constructs the {@linkplain RegistryEntry registry entry}.
     *
     * @param key an identifier of this registry entry
     * @param value the value
     * @since 1.0
     */
    public RegistryEntry(@NonNull Key key, @NonNull V value) {
        this.key = NullabilityUtil.requireNonNull(key, "key");
        this.value = NullabilityUtil.requireNonNull(value, "value");
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
}