package net.hypejet.jet.data.json.registry.registries.biome.effects.particle;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.json.JsonCodec;
import net.hypejet.jet.data.json.util.JsonUtil;
import net.hypejet.jet.registry.registries.biome.effects.particle.BiomeParticleSettings;
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

    private static final String NAME = "name";
    private static final String DATA = "data";
    private static final String PROBABILITY = "probability";

    @Override
    public BiomeParticleSettings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element is not a json object");
        return new BiomeParticleSettings(JsonUtil.read(NAME, String.class, object, context),
                JsonUtil.readOptional(DATA, BinaryTag.class, object, context),
                JsonUtil.read(PROBABILITY, float.class, object, context));
    }

    @Override
    public JsonElement serialize(BiomeParticleSettings src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty(NAME, src.name());
        JsonUtil.writeOptional(DATA, src.data(), BinaryTag.class, object, context);
        object.addProperty(PROBABILITY, src.probability());

        return object;
    }
}