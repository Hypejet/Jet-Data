package net.hypejet.jet.data.json.datapack;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.json.JsonCodec;
import net.hypejet.jet.data.json.util.JsonUtil;
import net.hypejet.jet.pack.DataPack;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain DataPack data pack}.
 *
 * @since 1.0
 * @author Codestech
 * @see DataPack
 * @see JsonCodec
 */
public final class DataPackJsonCodec implements JsonCodec<DataPack> {

    private static final String KEY = "key";
    private static final String VERSION = "version";

    @Override
    public DataPack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return new DataPack(JsonUtil.read(KEY, Key.class, object, context),
                JsonUtil.read(VERSION, String.class, object, context));
    }

    @Override
    public JsonElement serialize(DataPack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        JsonUtil.write(KEY, src.key(), Key.class, object, context);
        object.addProperty(VERSION, src.version());
        return object;
    }
}