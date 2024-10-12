package net.hypejet.jet.data.codecs.registry;

import com.google.gson.JsonArray;
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

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;

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

    private static final String KEY_FIELD = "key";
    private static final String VALUE_FIELD = "value";
    private static final String TAGS_FIELD = "tags";
    private static final String KNOWN_PACK_INFO_FIELD = "known-pack-info";

    private final Class<V> valueClass;

    /**
     * Constructs the {@linkplain RegistryEntryDataJsonCodec registry entry json codec}.
     *
     * @param valueClass a class of the type of value held by the registry entries, which this
     *                   codec deserializes and serializes
     * @since 1.0
     */
    public RegistryEntryDataJsonCodec(@NonNull Class<V> valueClass) {
        this.valueClass = NullabilityUtil.requireNonNull(valueClass, "value class");
    }

    @Override
    public DataRegistryEntry<V> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");

        Key key = JsonUtil.read(KEY_FIELD, Key.class, object, context);
        V value = JsonUtil.read(VALUE_FIELD, this.valueClass, object, context);
        PackInfo knownPackInfo = JsonUtil.readOptional(KNOWN_PACK_INFO_FIELD, PackInfo.class, object, context);

        Collection<Key> tags;
        JsonArray tagsJson = object.getAsJsonArray(TAGS_FIELD);

        if (tagsJson == null) {
            tags = null;
        } else {
            tags = new HashSet<>();
            tagsJson.forEach(element -> tags.add(context.deserialize(element, Key.class)));
        }

        if (!this.valueClass.isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException("The value class is not assignable from the class of value specified");
        return new DataRegistryEntry<>(key, value, tags, knownPackInfo);
    }

    @Override
    public JsonElement serialize(DataRegistryEntry<V> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        V value = src.value();
        if (!this.valueClass.isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException("The value class is not assignable from the class of value specified");

        JsonArray tagsJson;
        Collection<Key> tags = src.tags();

        if (tags == null) {
            tagsJson = null;
        } else {
            tagsJson = new JsonArray();
            tags.forEach(tag -> tagsJson.add(context.serialize(tag, Key.class)));
        }

        JsonUtil.write(KEY_FIELD, src.key(), Key.class, object, context);
        JsonUtil.write(VALUE_FIELD, src.value(), object, context);
        JsonUtil.writeOptional(TAGS_FIELD, tagsJson, object, context);
        JsonUtil.writeOptional(KNOWN_PACK_INFO_FIELD, src.knownPackInfo(), object, context);

        return object;
    }
}