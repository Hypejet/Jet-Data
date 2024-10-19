package net.hypejet.jet.data.codecs.registry.registries.armor.pattern;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registry.registries.armor.pattern.ArmorTrimPattern;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec} which deserializes and serializes an {@linkplain ArmorTrimPattern
 * armor trim pattern}.
 *
 * @since 1.0
 * @author Codestech
 * @see ArmorTrimPattern
 * @see JsonCodec
 */
public final class ArmorTrimPatternJsonCodec implements JsonCodec<ArmorTrimPattern> {

    private static final String ASSET = "asset";
    private static final String TEMPLATE_ITEM = "template-item";
    private static final String DESCRIPTION = "description";
    private static final String DECAL = "decal";

    @Override
    public ArmorTrimPattern deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");
        return new ArmorTrimPattern(JsonUtil.read(ASSET, Key.class, object, context),
                JsonUtil.read(TEMPLATE_ITEM, Key.class, object, context),
                JsonUtil.read(DESCRIPTION, Component.class, object, context),
                JsonUtil.read(DECAL, boolean.class, object, context));
    }

    @Override
    public JsonElement serialize(ArmorTrimPattern src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");

        JsonObject object = new JsonObject();
        JsonUtil.write(ASSET, src.asset(), Key.class, object, context);
        JsonUtil.write(TEMPLATE_ITEM, src.templateItem(), Key.class, object, context);
        JsonUtil.write(DESCRIPTION, src.description(), Component.class, object, context);
        object.addProperty(DECAL, src.decal());

        return object;
    }
}