package net.hypejet.jet.data.codecs.registry.registries.biome.effects.particle;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.particle.BiomeParticleSettings;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain BiomeParticleSettings biome
 * particle settings}.
 *
 * @since 1.0
 * @author Codestech
 * @see BiomeParticleSettings
 * @see JsonCodec
 */
public final class BiomeParticleSettingsJsonCodec implements JsonCodec<BiomeParticleSettings> {

    private static final String KEY = "key";
    private static final String DATA = "data";
    private static final String PROBABILITY = "probability";

    @Override
    public BiomeParticleSettings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element is not a json object");
        return new BiomeParticleSettings(JsonUtil.read(KEY, Key.class, object, context),
                JsonUtil.readOptional(DATA, BinaryTag.class, object, context),
                JsonUtil.read(PROBABILITY, float.class, object, context));
    }

    @Override
    public JsonElement serialize(BiomeParticleSettings src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        JsonUtil.write(KEY, src.key(), Key.class, object, context);
        JsonUtil.writeOptional(DATA, src.data(), BinaryTag.class, object, context);
        object.addProperty(PROBABILITY, src.probability());

        return object;
    }
}