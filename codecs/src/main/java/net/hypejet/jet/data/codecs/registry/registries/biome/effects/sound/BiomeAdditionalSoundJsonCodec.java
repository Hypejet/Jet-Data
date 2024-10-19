package net.hypejet.jet.data.codecs.registry.registries.biome.effects.sound;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registries.biome.effects.sound.BiomeAdditionalSound;
import net.hypejet.jet.data.model.api.registries.biome.effects.sound.BiomeSoundEvent;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain BiomeAdditionalSound biome
 * additional sound}.
 *
 * @since 1.0
 * @author Codestech
 * @see BiomeAdditionalSound
 * @see JsonCodec
 */
public final class BiomeAdditionalSoundJsonCodec implements JsonCodec<BiomeAdditionalSound> {

    private static final String EVENT = "event";
    private static final String TICK_CHANCE = "tick-chance";

    @Override
    public BiomeAdditionalSound deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return new BiomeAdditionalSound(JsonUtil.read(EVENT, BiomeSoundEvent.class, object, context),
                JsonUtil.read(TICK_CHANCE, double.class, object, context));
    }

    @Override
    public JsonElement serialize(BiomeAdditionalSound src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        JsonUtil.write(EVENT, src.event(), object, context);
        object.addProperty(TICK_CHANCE, src.tickChance());

        return object;
    }
}