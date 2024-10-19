package net.hypejet.jet.data.codecs.registry.registries.wolf;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.model.api.registry.registries.wolf.WolfBiomes;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a {@linkplain JsonCodec json codec} which deserializes and serializes a {@linkplain WolfBiomes wolf
 * biomes}.
 *
 * @since 1.0
 * @author Codestech
 * @see WolfBiomes
 * @see JsonCodec
 */
public final class WolfBiomesJsonCodec implements JsonCodec<WolfBiomes> {

    private static final String TAG_PREFIX = "#";

    @Override
    public WolfBiomes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");
        return switch (json) {
            case JsonPrimitive primitive -> {
                String string = primitive.getAsString();
                boolean isTag = string.startsWith(TAG_PREFIX);

                if (isTag) string = string.replaceFirst(TAG_PREFIX, "");

                Key key = Key.key(string);
                WolfBiomes biomes;

                if (isTag)
                    biomes = new WolfBiomes.TaggedBiomes(key);
                else
                    biomes = new WolfBiomes.SingleBiome(key);

                yield biomes;
            }
            case JsonArray array -> {
                Set<Key> keys = new HashSet<>();
                for (JsonElement element : array)
                    keys.add(context.deserialize(element, Key.class));
                yield new WolfBiomes.Biomes(Set.copyOf(keys));
            }
            default -> throw new IllegalArgumentException("The json element specified is not a json primitive" +
                    " or array");
        };
    }

    @Override
    public JsonElement serialize(WolfBiomes src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");
        return switch (src) {
            case WolfBiomes.SingleBiome biome -> new JsonPrimitive(biome.key().asString());
            case WolfBiomes.TaggedBiomes biomes -> new JsonPrimitive(TAG_PREFIX + biomes.key().asString());
            case WolfBiomes.Biomes biomes -> {
                JsonArray array = new JsonArray();
                for (Key key : biomes.keys())
                    array.add(context.serialize(key, Key.class));
                yield array;
            }
        };
    }
}