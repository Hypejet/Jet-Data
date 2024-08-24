package net.hypejet.jet.data.json.registry.registries.biome.effects.modifier;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.json.JsonCodec;
import net.hypejet.jet.registry.registries.biome.effects.modifier.GrassColorModifier;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain GrassColorModifier grass
 * color modifier}.
 *
 * @since 1.0
 * @author Codestech
 * @see GrassColorModifier
 * @see JsonCodec
 */
public final class GrassColorModifierJsonCodec implements JsonCodec<GrassColorModifier> {

    private static final String NONE = "none";
    private static final String DARK_FOREST = "dark-forest";
    private static final String SWAMP = "swamp";

    @Override
    public GrassColorModifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonPrimitive jsonPrimitive))
            throw new IllegalArgumentException("The json element is not a json primitive");

        String stringValue = jsonPrimitive.getAsString();
        return switch (stringValue) {
            case NONE -> GrassColorModifier.NONE;
            case DARK_FOREST -> GrassColorModifier.DARK_FOREST;
            case SWAMP -> GrassColorModifier.SWAMP;
            default -> throw new IllegalStateException(String.format(
                    "Unknown grass color modifier of: %s", stringValue
            ));
        };
    }

    @Override
    public JsonElement serialize(GrassColorModifier src, Type typeOfSrc, JsonSerializationContext context) {
        String stringValue = switch (src) {
            case NONE -> NONE;
            case DARK_FOREST -> DARK_FOREST;
            case SWAMP -> SWAMP;
        };
        return new JsonPrimitive(stringValue);
    }
}