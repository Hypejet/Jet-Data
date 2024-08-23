package net.hypejet.jet.data.json.biome.modifier;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.json.JsonCodec;
import net.hypejet.jet.registry.registries.biome.temperature.BiomeTemperatureModifier;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain BiomeTemperatureModifier
 * biome temperature modifier}.
 *
 * @since 1.0
 * @author Codestech
 * @see BiomeTemperatureModifier
 * @see JsonCodec
 */
public final class BiomeTemperatureModifierJsonCodec implements JsonCodec<BiomeTemperatureModifier> {

    private static final String NONE = "none";
    private static final String FROZEN = "frozen";

    @Override
    public BiomeTemperatureModifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonPrimitive jsonPrimitive))
            throw new IllegalArgumentException("The json element specified is not a json primitive");

        String stringValue = jsonPrimitive.getAsString();
        return switch (stringValue) {
            case NONE -> BiomeTemperatureModifier.NONE;
            case FROZEN -> BiomeTemperatureModifier.FROZEN;
            default -> throw new IllegalStateException(String.format(
                    "Unknown temperature modifier of: %s", stringValue
            ));
        };
    }

    @Override
    public JsonElement serialize(BiomeTemperatureModifier src, Type typeOfSrc, JsonSerializationContext context) {
        String stringValue = switch (src) {
            case NONE -> NONE;
            case FROZEN -> FROZEN;
        };
        return new JsonPrimitive(stringValue);
    }
}