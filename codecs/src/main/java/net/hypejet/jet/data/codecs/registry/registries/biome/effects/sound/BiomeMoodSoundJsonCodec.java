package net.hypejet.jet.data.codecs.registry.registries.biome.effects.sound;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.sound.BiomeMoodSound;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.sound.BiomeSoundEvent;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain BiomeMoodSound biome mood
 * sound}.
 *
 * @since 1.0
 * @author Codestech
 * @see BiomeMoodSound
 * @see JsonCodec
 */
public final class BiomeMoodSoundJsonCodec implements JsonCodec<BiomeMoodSound> {

    private static final String EVENT = "event";
    private static final String TICK_DELAY = "tick-delay";
    private static final String BLOCK_SEARCH_EXTENT = "block-search-extent";
    private static final String OFFSET = "offset";

    @Override
    public BiomeMoodSound deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return new BiomeMoodSound(JsonUtil.read(EVENT, BiomeSoundEvent.class, object, context),
                JsonUtil.read(TICK_DELAY, int.class, object, context),
                JsonUtil.read(BLOCK_SEARCH_EXTENT, int.class, object, context),
                JsonUtil.read(OFFSET, double.class, object, context));
    }

    @Override
    public JsonElement serialize(BiomeMoodSound src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        JsonUtil.write(EVENT, src.event(), object, context);
        object.addProperty(TICK_DELAY, src.tickDelay());
        object.addProperty(BLOCK_SEARCH_EXTENT, src.blockSearchExtent());
        object.addProperty(OFFSET, src.offset());

        return object;
    }
}