package net.hypejet.jet.data.codecs.pack;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec}, which deserializes and serializes a {@linkplain PackInfo pack info}.
 *
 * @since 1.0
 * @author Codestech
 * @see PackInfo
 * @see JsonCodec
 */
public final class PackInfoCodec implements JsonCodec<PackInfo> {

    private static final String KEY = "key";
    private static final String VERSION = "version";

    @Override
    public PackInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");
        return new PackInfo(JsonUtil.read(KEY, Key.class, object, context),
                JsonUtil.read(VERSION, String.class, object, context));
    }

    @Override
    public JsonElement serialize(PackInfo src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        JsonUtil.write(KEY, src.key(), Key.class, object, context);
        JsonUtil.write(VERSION, src.version(), object, context);
        return object;
    }
}