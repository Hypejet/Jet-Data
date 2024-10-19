package net.hypejet.jet.data.codecs.registry.registries.biome.effects.sound;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.sound.BiomeSoundEvent;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain BiomeSoundEvent biome sound
 * effect}.
 *
 * @since 1.0
 * @author Codestech
 * @see BiomeSoundEvent
 * @see JsonCodec
 */
public final class BiomeSoundEventJsonCodec implements JsonCodec<BiomeSoundEvent> {

    private static final String KEY = "key";
    private static final String RANGE = "range";

    @Override
    public BiomeSoundEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (json instanceof JsonPrimitive jsonPrimitive)
            return new BiomeSoundEvent(context.deserialize(jsonPrimitive, Key.class), null);
        if (!(json instanceof JsonObject jsonObject))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return new BiomeSoundEvent(JsonUtil.read(KEY, Key.class, jsonObject, context),
                JsonUtil.readOptional(RANGE, float.class, jsonObject, context));
    }

    @Override
    public JsonElement serialize(BiomeSoundEvent src, Type typeOfSrc, JsonSerializationContext context) {
        Float range = src.range();
        if (range == null)
            return context.serialize(src.key(), Key.class);

        JsonObject object = new JsonObject();

        JsonUtil.write(KEY, src.key(), Key.class, object, context);
        object.addProperty(RANGE, range);

        return object;
    }
}