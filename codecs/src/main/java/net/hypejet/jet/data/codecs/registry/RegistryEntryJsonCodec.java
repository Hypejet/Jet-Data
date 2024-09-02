package net.hypejet.jet.data.codecs.registry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntry;
import net.hypejet.jet.data.model.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which deserializes and serializes a {@linkplain RegistryEntry
 * registry entry}.
 *
 * @param <V> a type of value held by the registry entries, which this codec deserializes and serializes
 * @since 1.0
 * @author Codestech
 * @see RegistryEntry
 * @see JsonCodec
 */
public final class RegistryEntryJsonCodec<V> implements JsonCodec<RegistryEntry<V>> {

    private static final String KEY = "key";
    private static final String KNOWN_PACK = "known-pack";
    private static final String VALUE = "value";

    private final Class<V> valueClass;
    private final RegistryEntrySupplier<V> supplier;

    /**
     * Constructs the {@linkplain RegistryEntryJsonCodec registry entry json codec}.
     *
     * @param valueClass a class of the type of value held by the registry entries, which this
     *                   codec deserializes and serializes
     * @param supplier a supplier used to create instances of the registry entries
     * @since 1.0
     */
    public RegistryEntryJsonCodec(@NonNull Class<V> valueClass, @NonNull RegistryEntrySupplier<V> supplier) {
        this.valueClass = NullabilityUtil.requireNonNull(valueClass, "value class");
        this.supplier = NullabilityUtil.requireNonNull(supplier, "supplier");
    }

    @Override
    public RegistryEntry<V> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");

        Key key = JsonUtil.read(KEY, Key.class, object, context);
        DataPack dataPack = JsonUtil.read(KNOWN_PACK, DataPack.class, object, context);
        V value = JsonUtil.read(VALUE, this.valueClass, object, context);

        if (!this.valueClass.isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException("The value class is not assignable from the class of value specified");
        return this.supplier.create(key, dataPack, value);
    }

    @Override
    public JsonElement serialize(RegistryEntry<V> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        V value = src.value();
        if (!this.valueClass.isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException("The value class is not assignable from the class of value specified");

        JsonUtil.write(KEY, src.key(), Key.class, object, context);
        JsonUtil.write(KNOWN_PACK, src.knownPack(), object, context);
        JsonUtil.write(VALUE, src.value(), object, context);

        return object;
    }

    /**
     * Represents a supplier of {@linkplain RegistryEntry registry entries}.
     *
     * @param <V> a type of values of the registry entries
     * @since 1.0
     * @author Codestech
     */
    public interface RegistryEntrySupplier<V> {
        /**
         * Creates a {@linkplain RegistryEntry registry entry} with value provided.
         *
         * @param key an identifier that the registry entry should have
         * @param knownPack a data pack, which should enable the registry entry
         * @param value a value that the registry entry should have
         * @return the registry entry created
         * @since 1.0
         */
        @NonNull RegistryEntry<V> create(@NonNull Key key, @NonNull DataPack knownPack, @NonNull V value);
    }
}