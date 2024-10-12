package net.hypejet.jet.data.codecs.registry.registries.dimension.number;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.number.IntegerProvider;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which serializes and deserializes
 * a {@linkplain IntegerProvider.WeightedList.Entry weighted list integer provider entry}.
 *
 * @since 1.0
 * @author Codestech
 * @see IntegerProvider.WeightedList.Entry
 * @see JsonCodec
 */
public final class WeightedListEntryJsonCodec implements JsonCodec<IntegerProvider.WeightedList.Entry> {

    private static final String SOURCE = "source";
    private static final String WEIGHT = "weight";

    @Override
    public IntegerProvider.WeightedList.Entry deserialize(JsonElement json, Type typeOfT,
                                                          JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return new IntegerProvider.WeightedList.Entry(JsonUtil.read(SOURCE, IntegerProvider.class, object, context),
                JsonUtil.read(WEIGHT, int.class, object, context));
    }

    @Override
    public JsonElement serialize(IntegerProvider.WeightedList.Entry src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        JsonUtil.write(SOURCE, src.source(), object, context);
        JsonUtil.write(WEIGHT, src.weight(), object, context);
        return object;
    }
}