package net.hypejet.jet.data.json.key;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.json.JsonCodec;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain Key key}.
 *
 * @since 1.0
 * @author Codestech
 * @see Key
 * @see JsonCodec
 */
public final class KeyJsonCodec implements JsonCodec<Key> {
    @Override
    public Key deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonPrimitive jsonPrimitive))
            throw new IllegalArgumentException("The json element specified is not a json primitive");
        return Key.key(jsonPrimitive.getAsString());
    }

    @Override
    public JsonElement serialize(Key src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.asString());
    }
}