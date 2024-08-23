package net.hypejet.jet.data.json.biome.binary;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.json.JsonCodec;
import net.hypejet.jet.data.json.util.JsonUtil;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagTypes;
import net.kyori.adventure.nbt.ByteArrayBinaryTag;
import net.kyori.adventure.nbt.ByteBinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.DoubleBinaryTag;
import net.kyori.adventure.nbt.EndBinaryTag;
import net.kyori.adventure.nbt.FloatBinaryTag;
import net.kyori.adventure.nbt.IntArrayBinaryTag;
import net.kyori.adventure.nbt.IntBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.kyori.adventure.nbt.LongArrayBinaryTag;
import net.kyori.adventure.nbt.LongBinaryTag;
import net.kyori.adventure.nbt.ShortBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain BinaryTag binary tag}.
 *
 * @since 1.0
 * @author Codestech
 */
public final class BinaryTagJsonCodec implements JsonCodec<BinaryTag> {

    private static final String ID = "id";
    private static final String DATA = "data";

    private static final String LIST_TYPE = "type";
    private static final String LIST_CONTENTS = "contents";

    @Override
    public BinaryTag deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)  {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");

        byte id = JsonUtil.read(ID, byte.class, object, context);
        JsonElement data = object.get(DATA);

        if (id == BinaryTagTypes.END.id())
            return EndBinaryTag.endBinaryTag();
        else if (id == BinaryTagTypes.BYTE.id())
            return ByteBinaryTag.byteBinaryTag(data.getAsByte());
        else if (id == BinaryTagTypes.SHORT.id())
            return ShortBinaryTag.shortBinaryTag(data.getAsShort());
        else if (id == BinaryTagTypes.INT.id())
            return IntBinaryTag.intBinaryTag(data.getAsInt());
        else if (id == BinaryTagTypes.LONG.id())
            return LongBinaryTag.longBinaryTag(data.getAsLong());
        else if (id == BinaryTagTypes.FLOAT.id())
            return FloatBinaryTag.floatBinaryTag(data.getAsFloat());
        else if (id == BinaryTagTypes.DOUBLE.id())
            return DoubleBinaryTag.doubleBinaryTag(data.getAsDouble());
        else if (id == BinaryTagTypes.BYTE_ARRAY.id())
            return deserializeByteArray(data);
        else if (id == BinaryTagTypes.STRING.id())
            return StringBinaryTag.stringBinaryTag(data.getAsString());
        else if (id == BinaryTagTypes.LIST.id())
            return deserializeList(data, context);
        else if (id == BinaryTagTypes.COMPOUND.id())
            return deserializeCompound(data, context);
        else if (id == BinaryTagTypes.INT_ARRAY.id())
            return deserializeIntArray(data);
        else if (id == BinaryTagTypes.LONG_ARRAY.id())
            return deserializeLongArray(data);

        throw new IllegalArgumentException(String.format("Unknown binary tag type with identifier of %s", id));
    }

    @Override
    public JsonElement serialize(BinaryTag src, Type typeOfSrc, JsonSerializationContext context) {
        JsonElement data = switch (src) {
            case EndBinaryTag ignored -> null;
            case ByteBinaryTag tag -> new JsonPrimitive(tag.value());
            case ShortBinaryTag tag -> new JsonPrimitive(tag.value());
            case IntBinaryTag tag -> new JsonPrimitive(tag.value());
            case LongBinaryTag tag -> new JsonPrimitive(tag.value());
            case FloatBinaryTag tag -> new JsonPrimitive(tag.value());
            case DoubleBinaryTag tag -> new JsonPrimitive(tag.value());
            case ByteArrayBinaryTag tag -> serializeByteArray(tag);
            case StringBinaryTag tag -> new JsonPrimitive(tag.value());
            case ListBinaryTag tag -> serializeList(tag, context);
            case CompoundBinaryTag tag -> serializeCompound(tag, context);
            case IntArrayBinaryTag tag -> serializeIntArray(tag);
            case LongArrayBinaryTag tag -> serializeLongArray(tag);
            default -> throw new IllegalStateException(String.format("Unknown binary tag of: %s", src));
        };

        JsonObject object = new JsonObject();
        object.addProperty(ID, src.type().id());
        object.add(DATA, object);
        return object;
    }

    private static @NonNull BinaryTag deserializeByteArray(@NonNull JsonElement element) {
        if (!(element instanceof JsonArray array))
            throw new IllegalArgumentException("The data json element must be a json array");

        byte[] bytes = new byte[array.size()];
        for (int index = 0; index < bytes.length; index++)
            bytes[index] = array.get(index).getAsByte();

        return ByteArrayBinaryTag.byteArrayBinaryTag(bytes);
    }

    private static @NonNull JsonElement serializeByteArray(@NonNull ByteArrayBinaryTag tag) {
        JsonArray array = new JsonArray();
        for (byte element : tag.value())
            array.add(element);
        return array;
    }

    private static @NonNull BinaryTag deserializeList(@NonNull JsonElement element,
                                                      @NonNull JsonDeserializationContext context) {
        if (!(element instanceof JsonObject listObject))
            throw new IllegalArgumentException("The data json element for a list binary tag must be a json object");

        byte type = JsonUtil.read(LIST_TYPE, byte.class, listObject, context);
        JsonArray array = listObject.getAsJsonArray(LIST_CONTENTS);

        List<BinaryTag> tags = new ArrayList<>();
        for (JsonElement arrayElement : array) {
            BinaryTag tag = context.deserialize(arrayElement, BinaryTag.class);
            if (tag.type().id() != type) {
                throw new IllegalArgumentException("The type of element binary tag of the element is not the type" +
                        " specified in the list binary tag");
            }
            tags.add(tag);
        }

        return ListBinaryTag.from(List.copyOf(tags));
    }

    private static @NonNull JsonElement serializeList(@NonNull ListBinaryTag tag,
                                                      @NonNull JsonSerializationContext context) {
        JsonArray array = new JsonArray();
        for (BinaryTag element : tag)
            array.add(context.serialize(element, BinaryTag.class));

        JsonObject listObject = new JsonObject();
        listObject.addProperty(LIST_TYPE, tag.elementType().id());
        listObject.add(LIST_CONTENTS, array);

        return listObject;
    }

    private static @NonNull BinaryTag deserializeCompound(@NonNull JsonElement element,
                                                          @NonNull JsonDeserializationContext context) {
        if (!(element instanceof JsonObject compoundObject))
            throw new IllegalArgumentException("The data json element for a compound binary tag must be a" +
                    " json object");

        Map<String, BinaryTag> map = new HashMap<>();
        for (String key : compoundObject.keySet())
            map.put(key, JsonUtil.read(key, BinaryTag.class, compoundObject, context));

        return CompoundBinaryTag.from(map);
    }

    private static @NonNull JsonElement serializeCompound(@NonNull CompoundBinaryTag tag,
                                                          @NonNull JsonSerializationContext context) {
        JsonObject compoundObject = new JsonObject();
        for (Map.Entry<String, ? extends BinaryTag> element : tag)
            compoundObject.add(element.getKey(), context.serialize(element.getValue(), BinaryTag.class));
        return compoundObject;
    }

    private static @NonNull BinaryTag deserializeIntArray(@NonNull JsonElement element) {
        if (!(element instanceof JsonArray array))
            throw new IllegalArgumentException("The data json element for an integer array binary tag must be a json" +
                    " array");

        int[] integers = new int[array.size()];
        for (int index = 0; index < integers.length; index++)
            integers[index] = array.get(index).getAsInt();

        return IntArrayBinaryTag.intArrayBinaryTag(integers);
    }

    private static @NonNull JsonElement serializeIntArray(@NonNull IntArrayBinaryTag tag) {
        JsonArray array = new JsonArray();
        for (int element : tag.value())
            array.add(element);
        return array;
    }

    private static @NonNull BinaryTag deserializeLongArray(@NonNull JsonElement element) {
        if (!(element instanceof JsonArray array))
            throw new IllegalArgumentException("The data json element for a long array binary tag must be a json" +
                    " array");

        long[] longs = new long[array.size()];
        for (int index = 0; index < longs.length; index++)
            longs[index] = array.get(index).getAsLong();

        return LongArrayBinaryTag.longArrayBinaryTag(longs);
    }

    private static @NonNull JsonElement serializeLongArray(@NonNull LongArrayBinaryTag tag) {
        JsonArray array = new JsonArray();
        for (long element : tag.value())
            array.add(element);
        return array;
    }
}