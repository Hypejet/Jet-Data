package net.hypejet.jet.data.codecs.registry.registries.biome.effects.music;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.registry.registries.biome.effects.music.BiomeMusic;
import net.hypejet.jet.data.model.registry.registries.biome.effects.sound.BiomeSoundEvent;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain BiomeMusic biome music}.
 *
 * @since 1.0
 * @author Codestech
 * @see JsonCodec
 * @see BiomeMusic
 */
public final class BiomeMusicJsonCodec implements JsonCodec<BiomeMusic> {

    private static final String EVENT = "event";
    private static final String MINIMUM_DELAY = "minimum-delay";
    private static final String MAXIMUM_DELAY = "maximum-delay";
    private static final String REPLACE_CURRENT_MUSIC = "replace-current-music";

    @Override
    public BiomeMusic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)  {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return new BiomeMusic(JsonUtil.read(EVENT, BiomeSoundEvent.class, object, context),
                JsonUtil.read(MINIMUM_DELAY, int.class, object, context),
                JsonUtil.read(MAXIMUM_DELAY, int.class, object, context),
                JsonUtil.read(REPLACE_CURRENT_MUSIC, boolean.class, object, context));
    }

    @Override
    public JsonElement serialize(BiomeMusic src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        JsonUtil.write(EVENT, src.event(), object, context);
        JsonUtil.write(MINIMUM_DELAY, src.minimumDelay(), object, context);
        JsonUtil.write(MAXIMUM_DELAY, src.maximumDelay(), object, context);
        JsonUtil.write(REPLACE_CURRENT_MUSIC, src.replaceCurrentMusic(), object, context);

        return object;
    }
}