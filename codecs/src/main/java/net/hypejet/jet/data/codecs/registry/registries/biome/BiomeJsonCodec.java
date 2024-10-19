package net.hypejet.jet.data.codecs.registry.registries.biome;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registries.biome.Biome;
import net.hypejet.jet.data.model.api.registries.biome.effects.BiomeEffectSettings;
import net.hypejet.jet.data.model.api.registries.biome.temperature.BiomeTemperatureModifier;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain Biome biome}.
 *
 * @since 1.0
 * @author Codestech
 * @see Biome
 * @see JsonCodec
 */
public final class BiomeJsonCodec implements JsonCodec<Biome> {

    private static final String HAS_PRECIPITATION = "has-precipitation";
    private static final String TEMPERATURE = "temperature";
    private static final String TEMPERATURE_MODIFIER = "temperature-modifier";
    private static final String DOWNFALL = "downfall";
    private static final String EFFECTS = "effects";

    @Override
    public Biome deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return Biome.builder()
                .hasPrecipitation(object.get(HAS_PRECIPITATION).getAsBoolean())
                .temperature(object.get(TEMPERATURE).getAsFloat())
                .temperatureModifier(JsonUtil.read(TEMPERATURE_MODIFIER, BiomeTemperatureModifier.class,
                        object, context))
                .downfall(object.get(DOWNFALL).getAsFloat())
                .effectSettings(context.deserialize(object.get(EFFECTS), BiomeEffectSettings.class))
                .build();
    }

    @Override
    public JsonElement serialize(Biome src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");

        JsonObject object = new JsonObject();
        object.addProperty(HAS_PRECIPITATION, src.hasPrecipitation());
        object.addProperty(TEMPERATURE , src.temperature());
        JsonUtil.writeOptional(TEMPERATURE_MODIFIER, src.temperatureModifier(), object, context);
        object.addProperty(DOWNFALL, src.downfall());
        object.add(EFFECTS, context.serialize(src.effects()));

        return object;
    }
}