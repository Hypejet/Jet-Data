package net.hypejet.jet.data.codecs.registry.registries.fluid;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.hypejet.jet.data.model.server.registry.registries.fluid.Fluid;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which deserializes and serializes a {@linkplain Fluid fluid}.
 *
 * @since 1.0
 * @author Codestech
 * @see Fluid
 * @see JsonCodec
 */
public final class FluidJsonCodec implements JsonCodec<Fluid> {

    private static final Fluid INSTANCE = new Fluid();

    @Override
    public Fluid deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (!(json instanceof JsonObject))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return INSTANCE;
    }

    @Override
    public JsonElement serialize(Fluid src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");
        return new JsonObject();
    }
}