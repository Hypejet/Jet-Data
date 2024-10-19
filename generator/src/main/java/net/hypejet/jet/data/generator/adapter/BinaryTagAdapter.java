package net.hypejet.jet.data.generator.adapter;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagType;
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
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a utility that converts {@linkplain Tag tags} into {@linkplain BinaryTag binary tags}.
 *
 * @since 1.0
 * @author Codestech
 * @see BinaryTag
 * @see Tag
 */
public final class BinaryTagAdapter {

    private static final Map<TagType<?>, BinaryTagType<?>> TYPE_MAPPINGS = Map.ofEntries(
            Map.entry(EndTag.TYPE, BinaryTagTypes.END),
            Map.entry(ByteTag.TYPE, BinaryTagTypes.BYTE),
            Map.entry(ShortTag.TYPE, BinaryTagTypes.SHORT),
            Map.entry(IntTag.TYPE, BinaryTagTypes.INT),
            Map.entry(LongTag.TYPE, BinaryTagTypes.LONG),
            Map.entry(FloatTag.TYPE, BinaryTagTypes.FLOAT),
            Map.entry(DoubleTag.TYPE, BinaryTagTypes.DOUBLE),
            Map.entry(ByteArrayTag.TYPE, BinaryTagTypes.BYTE_ARRAY),
            Map.entry(StringTag.TYPE, BinaryTagTypes.STRING),
            Map.entry(ListTag.TYPE, BinaryTagTypes.LIST),
            Map.entry(CompoundTag.TYPE, BinaryTagTypes.COMPOUND),
            Map.entry(IntArrayTag.TYPE, BinaryTagTypes.INT_ARRAY),
            Map.entry(LongArrayTag.TYPE, BinaryTagTypes.LONG)
    );

    private BinaryTagAdapter() {}

    /**
     * Converts a {@linkplain Tag tag} specified into a {@linkplain BinaryTag binary tag}.
     *
     * @param tag the tag to convert
     * @return the converted binary tag
     * @since 1.0
     */
    public static @NonNull BinaryTag convert(@NonNull Tag tag) {
        NullabilityUtil.requireNonNull(tag, "tag");
        return switch (tag) {
            case EndTag ignored -> EndBinaryTag.endBinaryTag();
            case ByteTag byteTag -> ByteBinaryTag.byteBinaryTag(byteTag.getAsByte());
            case ShortTag shortTag -> ShortBinaryTag.shortBinaryTag(shortTag.getAsShort());
            case IntTag intTag -> IntBinaryTag.intBinaryTag(intTag.getAsInt());
            case LongTag longTag -> LongBinaryTag.longBinaryTag(longTag.getAsLong());
            case FloatTag floatTag -> FloatBinaryTag.floatBinaryTag(floatTag.getAsFloat());
            case DoubleTag doubleTag -> DoubleBinaryTag.doubleBinaryTag(doubleTag.getAsDouble());
            case ByteArrayTag byteArrayTag -> ByteArrayBinaryTag.byteArrayBinaryTag(byteArrayTag.getAsByteArray()
                    .clone());
            case StringTag stringTag -> StringBinaryTag.stringBinaryTag(stringTag.getAsString());
            case ListTag listTag -> convertListTag(listTag);
            case CompoundTag compoundTag -> convertCompoundTag(compoundTag);
            case IntArrayTag intArrayTag -> IntArrayBinaryTag.intArrayBinaryTag(intArrayTag.getAsIntArray().clone());
            case LongArrayTag longArrayTag -> LongArrayBinaryTag.longArrayBinaryTag(longArrayTag.getAsLongArray()
                    .clone());
            default -> throw new IllegalStateException(String.format("Unknown tag of: %s", tag));
        };
    }

    private static @NonNull BinaryTag convertListTag(@NonNull ListTag tag) {
        NullabilityUtil.requireNonNull(tag, "tag");

        List<BinaryTag> tags = new ArrayList<>();
        BinaryTagType<?> type = tagType(tag.getType());

        for (Tag element : tag) {
            BinaryTag convertedElement = convert(element);
            if (convertedElement.type() != type)
                throw new IllegalArgumentException("A type of the binary tag of the element is not a type specified" +
                        " in the list binary tag");
            tags.add(convertedElement);
        }

        return ListBinaryTag.listBinaryTag(type, List.copyOf(tags));
    }

    private static @NonNull BinaryTag convertCompoundTag(@NonNull CompoundTag tag) {
        NullabilityUtil.requireNonNull(tag, "tag");
        Map<String, BinaryTag> values = new HashMap<>();

        for (String key : tag.getAllKeys()) {
            Tag value = NullabilityUtil.requireNonNull(tag.get(key), "value");
            values.put(key, convert(value));
        }

        return CompoundBinaryTag.from(values);
    }

    private static @NonNull BinaryTagType<?> tagType(@NonNull TagType<?> type) {
        NullabilityUtil.requireNonNull(type, "type");
        BinaryTagType<?> convertedType = TYPE_MAPPINGS.get(type);

        if (convertedType == null)
            throw new IllegalArgumentException(String.format("Could not find a mapping for tag type of: %s", type));

        return convertedType;
    }
}
