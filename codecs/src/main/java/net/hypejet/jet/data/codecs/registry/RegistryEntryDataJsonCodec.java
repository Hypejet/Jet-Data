package net.hypejet.jet.data.codecs.registry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which deserializes and serializes a {@linkplain DataRegistryEntry
 * data registry entry}.
 *
 * @param <V> a type of value held by the data registry entry, which this codec deserializes and serializes
 * @since 1.0
 * @author Codestech
 * @see DataRegistryEntry
 * @see JsonCodec
 */
public final class RegistryEntryDataJsonCodec<V> implements JsonCodec<DataRegistryEntry<V>> {

    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final String KNOWN_PACK_INFO = "known-pack-info";

    private final Class<V> valueClass;
    private final PackRegistryEntrySupplier<V> supplier;

    /**
     * Constructs the {@linkplain RegistryEntryDataJsonCodec registry entry json codec}.
     *
     * @param valueClass a class of the type of value held by the registry entries, which this
     *                   codec deserializes and serializes
     * @param supplier a supplier used to create instances of the registry entries
     * @since 1.0
     */
    public RegistryEntryDataJsonCodec(@NonNull Class<V> valueClass,
                                      @NonNull PackRegistryEntrySupplier<V> supplier) {
        this.valueClass = NullabilityUtil.requireNonNull(valueClass, "value class");
        this.supplier = NullabilityUtil.requireNonNull(supplier, "supplier");
    }

    @Override
    public DataRegistryEntry<V> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");

        Key key = JsonUtil.read(KEY, Key.class, object, context);
        V value = JsonUtil.read(VALUE, this.valueClass, object, context);
        PackInfo knownPackInfo = JsonUtil.readOptional(KNOWN_PACK_INFO, PackInfo.class, object, context);

        if (!this.valueClass.isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException("The value class is not assignable from the class of value specified");
        return this.supplier.create(key, value, knownPackInfo);
    }

    @Override
    public JsonElement serialize(DataRegistryEntry<V> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        V value = src.value();
        if (!this.valueClass.isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException("The value class is not assignable from the class of value specified");

        JsonUtil.write(KEY, src.key(), Key.class, object, context);
        JsonUtil.write(VALUE, src.value(), object, context);
        JsonUtil.writeOptional(KNOWN_PACK_INFO, src.knownPackInfo(), object, context);

        return object;
    }

    /**
     * Represents a supplier of a {@linkplain DataRegistryEntry data registry entry}.
     *
     * @param <V> a type of values of the registry entry
     * @since 1.0
     * @author Codestech
     */
    public interface PackRegistryEntrySupplier<V> {
        /**
         * Creates a {@linkplain DataRegistryEntry data registry entry} with value provided.
         *
         * @param key an identifier that the data registry entry should have
         * @param value a value that the data registry entry should have
         * @param knownPackInfo an information of a feature pack, which enables the data registry entry
         * @return the data registry entry created
         * @since 1.0
         */
        @NonNull DataRegistryEntry<V> create(@NonNull Key key, @NonNull V value, @Nullable PackInfo knownPackInfo);
    }
}