package net.hypejet.jet.data.codecs.pack;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.pack.info.PackInfo;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a {@linkplain JsonCodec json codec}, which deserializes and serializes a {@linkplain DataPack data pack}.
 *
 * @since 1.0
 * @author Codestech
 * @see DataPack
 * @see JsonCodec
 */
public final class DataPackJsonCodec implements JsonCodec<DataPack> {

    private static final String PACK_INFO = "pack-info";
    private static final String REQUIRED_FEATURE_FLAGS = "required-feature-flags";

    @Override
    public DataPack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");

        JsonArray jsonRequiredFeatureFlags = JsonUtil.read(REQUIRED_FEATURE_FLAGS, JsonArray.class, object, context);
        Set<Key> requiredFeatureFlags = new HashSet<>();

        for (JsonElement jsonFeatureFlag : jsonRequiredFeatureFlags)
            requiredFeatureFlags.add(context.deserialize(jsonFeatureFlag, Key.class));

        return new DataPack(JsonUtil.read(PACK_INFO, PackInfo.class, object, context), requiredFeatureFlags);
    }

    @Override
    public JsonElement serialize(DataPack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        JsonUtil.write(PACK_INFO, src.info(), object, context);

        JsonArray jsonRequiredFeatureFlags = new JsonArray();
        for (Key key : src.requiredFeatureFlags())
            jsonRequiredFeatureFlags.add(context.serialize(key, Key.class));
        object.add(REQUIRED_FEATURE_FLAGS, jsonRequiredFeatureFlags);

        return object;
    }
}