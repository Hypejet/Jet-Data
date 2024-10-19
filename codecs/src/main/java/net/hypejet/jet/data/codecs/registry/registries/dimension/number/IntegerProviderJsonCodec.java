package net.hypejet.jet.data.codecs.registry.registries.dimension.number;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.number.IntegerProvider;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@linkplain JsonCodec json codec}, which deserializes and serializes an {@linkplain IntegerProvider
 * integer provider}.
 *
 * @since 1.0
 * @author Codestech
 * @see IntegerProvider
 * @see JsonCodec
 */
public final class IntegerProviderJsonCodec implements JsonCodec<IntegerProvider> {

    private static final String TYPE = "type";
    private static final String MINIMUM = "minimum";
    private static final String MAXIMUM = "maximum";
    private static final String SOURCE = "source";
    private static final String MEAN = "mean";
    private static final String DEVIATION = "deviation";

    private static final String TYPE_UNIFORM = "uniform";
    private static final String TYPE_BIASED_TO_BOTTOM = "biased-to-bottom";
    private static final String TYPE_CLAMPED = "clamped";
    private static final String TYPE_CLAMPED_NORMAL = "clamped-normal";

    @Override
    public IntegerProvider deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (json instanceof JsonPrimitive primitive)
            return new IntegerProvider.ConstantInteger(primitive.getAsInt());

        if (json instanceof JsonArray array) {
            List<IntegerProvider.WeightedList.Entry> entries = new ArrayList<>();
            for (JsonElement element : array)
                entries.add(context.deserialize(element, IntegerProvider.WeightedList.Entry.class));
            return new IntegerProvider.WeightedList(entries);
        }

        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");

        String type = JsonUtil.read(TYPE, String.class, object, context);
        return switch (type) {
            case TYPE_UNIFORM -> new IntegerProvider.Uniform(JsonUtil.read(MINIMUM, int.class, object, context),
                    JsonUtil.read(MAXIMUM, int.class, object, context));
            case TYPE_BIASED_TO_BOTTOM -> new IntegerProvider.BiasedToBottom(
                    JsonUtil.read(MINIMUM, int.class, object, context),
                    JsonUtil.read(MAXIMUM, int.class, object, context)
            );
            case TYPE_CLAMPED -> new IntegerProvider.Clamped(JsonUtil.read(MINIMUM, int.class, object, context),
                    JsonUtil.read(MAXIMUM, int.class, object, context),
                    JsonUtil.read(SOURCE, IntegerProvider.class, object, context));
            case TYPE_CLAMPED_NORMAL -> new IntegerProvider.ClampedNormal(
                    JsonUtil.read(MEAN, float.class, object, context),
                    JsonUtil.read(DEVIATION, float.class, object, context),
                    JsonUtil.read(MINIMUM, int.class, object, context),
                    JsonUtil.read(MAXIMUM, int.class, object, context)
            );
            default -> throw new IllegalStateException(String.format("Unknown integer provider type: %s", type));
        };
    }

    @Override
    public JsonElement serialize(IntegerProvider src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");

        if (src instanceof IntegerProvider.ConstantInteger provider)
            return new JsonPrimitive(provider.value());

        if (src instanceof IntegerProvider.WeightedList provider) {
            JsonArray array = new JsonArray();
            for (IntegerProvider.WeightedList.Entry entry : provider.entries())
                array.add(context.serialize(entry));
            return array;
        }

        JsonObject object = new JsonObject();
        switch (src) {
            case IntegerProvider.BiasedToBottom provider -> {
                object.addProperty(TYPE, TYPE_BIASED_TO_BOTTOM);
                object.addProperty(MINIMUM, provider.minimum());
                object.addProperty(MAXIMUM, provider.maximum());
            }
            case IntegerProvider.Clamped provider -> {
                object.addProperty(TYPE, TYPE_CLAMPED);
                object.addProperty(MINIMUM, provider.minimum());
                object.addProperty(MAXIMUM, provider.maximum());
                object.add(SOURCE, context.serialize(provider.source()));
            }
            case IntegerProvider.ClampedNormal provider -> {
                object.addProperty(TYPE, TYPE_CLAMPED_NORMAL);
                object.addProperty(MEAN, provider.mean());
                object.addProperty(DEVIATION, provider.deviation());
                object.addProperty(MINIMUM, provider.minimum());
                object.addProperty(MAXIMUM, provider.maximum());
            }
            case IntegerProvider.Uniform provider -> {
                object.addProperty(TYPE, TYPE_UNIFORM);
                object.addProperty(MINIMUM, provider.minimum());
                object.addProperty(MAXIMUM, provider.maximum());
            }
            // This branch is never called, but needed for proper compilation
            default -> throw new IllegalStateException(String.format("Unknown integer provider: %s", src));
        }

        return object;
    }
}