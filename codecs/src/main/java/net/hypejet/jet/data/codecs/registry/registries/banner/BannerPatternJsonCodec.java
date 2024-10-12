package net.hypejet.jet.data.codecs.registry.registries.banner;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registry.registries.banner.BannerPattern;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec} which deserializes and serializes a {@linkplain BannerPattern
 * banner pattern}.
 *
 * @since 1.0
 * @author Codestech
 * @see BannerPattern
 * @see JsonCodec
 */
public final class BannerPatternJsonCodec implements JsonCodec<BannerPattern> {

    private static final String ASSET_FIELD = "asset";
    private static final String TRANSLATION_KEY_FIELD = "translation-key";

    @Override
    public BannerPattern deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return new BannerPattern(JsonUtil.read(ASSET_FIELD, Key.class, object, context),
                JsonUtil.read(TRANSLATION_KEY_FIELD, String.class, object, context));
    }

    @Override
    public JsonElement serialize(BannerPattern src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add(ASSET_FIELD, context.serialize(src.asset(), Key.class));
        object.addProperty(TRANSLATION_KEY_FIELD, src.translationKey());
        return object;
    }
}