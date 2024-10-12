package net.hypejet.jet.data.codecs.pack;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.server.registry.registries.pack.FeaturePack;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a {@linkplain JsonCodec json codec}, which deserializes and serializes
 * a {@linkplain FeaturePack feature pack}.
 *
 * @since 1.0
 * @author Codestech
 * @see FeaturePack
 * @see JsonCodec
 */
public final class FeaturePackJsonCodec implements JsonCodec<FeaturePack> {

    private static final String PACK_INFO = "pack-info";
    private static final String REQUIRED_FEATURE_FLAGS = "required-feature-flags";

    @Override
    public FeaturePack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");

        JsonArray jsonRequiredFeatureFlags = JsonUtil.read(REQUIRED_FEATURE_FLAGS, JsonArray.class, object, context);
        Set<Key> requiredFeatureFlags = new HashSet<>();

        for (JsonElement jsonFeatureFlag : jsonRequiredFeatureFlags)
            requiredFeatureFlags.add(context.deserialize(jsonFeatureFlag, Key.class));

        return new FeaturePack(JsonUtil.read(PACK_INFO, PackInfo.class, object, context), requiredFeatureFlags);
    }

    @Override
    public JsonElement serialize(FeaturePack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        JsonUtil.write(PACK_INFO, src.info(), object, context);

        JsonArray jsonRequiredFeatureFlags = new JsonArray();
        for (Key key : src.requiredFeatureFlags())
            jsonRequiredFeatureFlags.add(context.serialize(key, Key.class));
        object.add(REQUIRED_FEATURE_FLAGS, jsonRequiredFeatureFlags);

        return object;
    }
}