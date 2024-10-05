package net.hypejet.jet.data.codecs.color;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.model.color.Color;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain Color color}.
 *
 * @since 1.0
 * @author Codestech
 * @see Color
 * @see JsonCodec
 */
public final class ColorJsonCodec implements JsonCodec<Color> {
    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonPrimitive jsonPrimitive))
            throw new IllegalArgumentException("The json element specified is not a json primitive");
        return new Color(jsonPrimitive.getAsInt());
    }

    @Override
    public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.value());
    }
}