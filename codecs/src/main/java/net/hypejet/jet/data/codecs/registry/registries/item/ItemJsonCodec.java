package net.hypejet.jet.data.codecs.registry.registries.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.server.registry.registries.item.Item;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a {@linkplain JsonCodec json codec}, which deserializes and serializes a {@linkplain Item item}.
 *
 * @since 1.0
 * @author Codestech
 * @see Item
 * @see JsonCodec
 */
public final class ItemJsonCodec implements JsonCodec<Item> {

    private static final String REQUIRED_FEATURE_FLAGS_FIELD = "required-feature-flags";

    @Override
    public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");

        JsonArray jsonFeatureFlags = JsonUtil.read(REQUIRED_FEATURE_FLAGS_FIELD, JsonArray.class, object, context);
        Set<Key> requiredFeatureFlags = new HashSet<>();

        for (JsonElement element : jsonFeatureFlags)
            requiredFeatureFlags.add(context.deserialize(element, Key.class));
        return new Item(requiredFeatureFlags);
    }

    @Override
    public JsonElement serialize(Item src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        JsonArray jsonRequiredFeatureFlags = new JsonArray();
        for (Key key : src.requiredFeatureFlags())
            jsonRequiredFeatureFlags.add(context.serialize(key));
        object.add(REQUIRED_FEATURE_FLAGS_FIELD, jsonRequiredFeatureFlags);

        return object;
    }
}