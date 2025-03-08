package net.hypejet.jet.data.codecs.registry.registries.block;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.model.api.block.entity.BlockEntityType;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents {@linkplain JsonCodec a json codec}, which deserializes and serializes
 * {@linkplain BlockEntityType block entity types}.
 *
 * @since 1.0
 * @see BlockEntityType
 * @see JsonCodec
 */
public final class BlockEntityTypeJsonCodec implements JsonCodec<BlockEntityType> {
    @Override
    public BlockEntityType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonArray array))
            throw new IllegalArgumentException("The json element must be a json array");

        Set<Key> validBlocks = new HashSet<>();
        for (JsonElement element : array)
            validBlocks.add(context.deserialize(element, Key.class));

        return new BlockEntityType(validBlocks);
    }

    @Override
    public JsonElement serialize(BlockEntityType src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray array = new JsonArray();
        for (Key key : src.validBlocks())
            array.add(context.serialize(key));
        return array;
    }
}