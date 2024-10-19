package net.hypejet.jet.data.codecs.registry.registries.painting;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registry.registries.painting.PaintingVariant;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec} which deserializes and serializes
 * a {@linkplain PaintingVariant painting variant}.
 *
 * @since 1.0
 * @author Codestech
 * @see PaintingVariant
 * @see JsonCodec
 */
public final class PaintingVariantJsonCodec implements JsonCodec<PaintingVariant> {

    private static final String ASSET = "asset";
    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";

    @Override
    public PaintingVariant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (!(json instanceof JsonObject object))
            throw new IllegalStateException("The json element specified is not a json object");
        return new PaintingVariant(JsonUtil.read(ASSET, Key.class, object, context),
                JsonUtil.read(HEIGHT, int.class, object, context), JsonUtil.read(WIDTH, int.class, object, context));
    }

    @Override
    public JsonElement serialize(PaintingVariant src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");

        JsonObject object = new JsonObject();
        JsonUtil.write(ASSET, src.asset(), Key.class, object, context);
        object.addProperty(HEIGHT, src.height());
        object.addProperty(WIDTH, src.width());

        return object;
    }
}