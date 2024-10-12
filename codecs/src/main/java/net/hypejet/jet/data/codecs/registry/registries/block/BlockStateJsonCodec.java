package net.hypejet.jet.data.codecs.registry.registries.block;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.server.registry.registries.block.state.BlockState;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a {@linkplain JsonCodec json codec}, which deserializes and serializes
 * a {@linkplain BlockState block state}.
 *
 * @since 1.0
 * @author Codestech
 * @see BlockState
 * @see JsonCodec
 */
public final class BlockStateJsonCodec implements JsonCodec<BlockState> {

    private static final String PROPERTIES_FIELD = "properties";

    @Override
    public BlockState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");

        JsonObject propertiesObject = JsonUtil.read(PROPERTIES_FIELD, JsonObject.class, object, context);
        Map<String, String> properties = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : propertiesObject.entrySet())
            properties.put(entry.getKey(), entry.getValue().getAsString());
        return new BlockState(properties);
    }

    @Override
    public JsonElement serialize(BlockState src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        JsonObject propertiesObject = new JsonObject();
        src.properties().forEach(propertiesObject::addProperty);
        object.add(PROPERTIES_FIELD, propertiesObject);

        return object;
    }
}