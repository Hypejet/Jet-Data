package net.hypejet.jet.data.codecs.registry.registries.block;

import com.google.common.primitives.ImmutableIntArray;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.hypejet.jet.data.model.server.registry.registries.block.Block;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a {@linkplain JsonCodec json codec}, which deserializes and serializes a {@linkplain Block block}.
 *
 * @since 1.0
 * @author Codestech
 * @see Block
 * @see JsonCodec
 */
public final class BlockJsonCodec implements JsonCodec<Block> {

    private static final String REQUIRED_FEATURE_FLAGS_FIELD = "required-feature-flags";
    private static final String DEFAULT_BLOCK_STATE_ID = "default-block-state-id";
    private static final String POSSIBLE_STATE_IDENTIFIERS = "possible-block-state-identifiers";
    private static final String BLOCK_ENTITY_TYPE_KEY = "block-entity-type-key";

    @Override
    public Block deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");

        JsonArray jsonFeatureFlags = JsonUtil.read(REQUIRED_FEATURE_FLAGS_FIELD, JsonArray.class, object, context);
        JsonArray jsonPossibleStates = JsonUtil.read(POSSIBLE_STATE_IDENTIFIERS, JsonArray.class, object, context);

        Set<Key> requiredFeatureFlags = new HashSet<>();
        for (JsonElement element : jsonFeatureFlags)
            requiredFeatureFlags.add(context.deserialize(element, Key.class));

        int[] possibleStateIdentifiers = new int[jsonPossibleStates.size()];
        for (int index = 0; index < possibleStateIdentifiers.length; index++) {
            JsonElement element = jsonPossibleStates.get(index);
            possibleStateIdentifiers[index] = element.getAsInt();
        }

        return new Block(
                requiredFeatureFlags,
                JsonUtil.read(DEFAULT_BLOCK_STATE_ID, int.class, object, context),
                ImmutableIntArray.copyOf(possibleStateIdentifiers),
                JsonUtil.readOptional(BLOCK_ENTITY_TYPE_KEY, Key.class, object, context)
        );
    }

    @Override
    public JsonElement serialize(Block src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");

        JsonArray jsonRequiredFeatureFlags = new JsonArray();
        for (Key key : src.requiredFeatureFlags())
            jsonRequiredFeatureFlags.add(context.serialize(key));

        JsonArray jsonPossibleStates = new JsonArray();
        src.possibleBlockStateIdentifiers().forEach(jsonPossibleStates::add);

        JsonObject object = new JsonObject();
        object.add(REQUIRED_FEATURE_FLAGS_FIELD, jsonRequiredFeatureFlags);
        object.addProperty(DEFAULT_BLOCK_STATE_ID, src.defaultBlockStateId());
        object.add(POSSIBLE_STATE_IDENTIFIERS, jsonPossibleStates);

        JsonUtil.writeOptional(BLOCK_ENTITY_TYPE_KEY, src.blockEntityTypeKey(), Key.class, object, context);
        return object;
    }
}