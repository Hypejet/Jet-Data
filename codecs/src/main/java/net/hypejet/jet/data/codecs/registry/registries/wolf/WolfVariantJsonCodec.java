package net.hypejet.jet.data.codecs.registry.registries.wolf;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registry.registries.wolf.WolfBiomes;
import net.hypejet.jet.data.model.api.registry.registries.wolf.WolfVariant;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec} which deserializes and serializes a {@linkplain WolfVariant wolf
 * variant}.
 *
 * @since 1.0
 * @author Codestech
 * @see WolfVariant
 * @see JsonCodec
 */
public final class WolfVariantJsonCodec implements JsonCodec<WolfVariant> {

    private static final String WILD_TEXTURE = "wild-texture";
    private static final String TAME_TEXTURE = "tame-texture";
    private static final String ANGRY_TEXTURE = "angry-texture";
    private static final String BIOMES = "biomes";

    @Override
    public WolfVariant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");
        return new WolfVariant(JsonUtil.read(WILD_TEXTURE, Key.class, object, context),
                JsonUtil.read(TAME_TEXTURE, Key.class, object, context),
                JsonUtil.read(ANGRY_TEXTURE, Key.class, object, context),
                JsonUtil.read(BIOMES, WolfBiomes.class, object, context));
    }

    @Override
    public JsonElement serialize(WolfVariant src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        JsonUtil.write(WILD_TEXTURE, src.wildTexture(), Key.class, object, context);
        JsonUtil.write(TAME_TEXTURE, src.tameTexture(), Key.class, object, context);
        JsonUtil.write(ANGRY_TEXTURE, src.angryTexture(), Key.class, object, context);
        JsonUtil.write(BIOMES, src.biomes(), WolfBiomes.class, object, context);
        return object;
    }
}