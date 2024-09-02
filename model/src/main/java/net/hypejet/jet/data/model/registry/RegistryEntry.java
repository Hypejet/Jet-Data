package net.hypejet.jet.data.model.registry;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

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
    private final DataPack knownPack;
    private final V value;

    /**
     * Constructs the {@linkplain RegistryEntry registry entry}.
     *
     * @param key an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the value
     * @since 1.0
     */
    public RegistryEntry(@NonNull Key key, @NonNull DataPack knownPack, @NonNull V value) {
        this.key = NullabilityUtil.requireNonNull(key, "key");
        this.knownPack = NullabilityUtil.requireNonNull(knownPack, "known pack");
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
     * Gets a {@linkplain DataPack data pack}, which enables this registry entry.
     *
     * @return the data pack
     * @since 1.0
     */
    public @NonNull DataPack knownPack() {
        return this.knownPack;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistryEntry<?> that)) return false;
        return Objects.equals(this.key, that.key)
                && Objects.equals(this.knownPack, that.knownPack)
                && Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.knownPack, this.value);
    }

    @Override
    public String toString() {
        return "RegistryEntry{" +
                "key=" + this.key +
                ", knownPack=" + this.knownPack +
                ", value=" + this.value +
                '}';
    }
}