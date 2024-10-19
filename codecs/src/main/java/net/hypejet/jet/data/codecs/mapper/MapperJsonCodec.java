package net.hypejet.jet.data.codecs.mapper;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.mapper.Mapper;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Represents a {@linkplain JsonCodec json codec} which serializes and deserializes objects using
 * a {@linkplain Mapper mapper}.
 *
 * @param <R> a type of read object of the mapper
 * @param <W> a type of written object of the mapper
 * @since 1.0
 * @author Codestech
 * @see Mapper
 * @see JsonCodec
 */
public final class MapperJsonCodec<R, W> implements JsonCodec<R> {

    private final Mapper<R, W> mapper;

    /**
     * Constructs the {@linkplain MapperJsonCodec mapper json codec}.
     *
     * @param mapper the mapper
     * @since 1.0
     */
    public MapperJsonCodec(@NonNull Mapper<R, W> mapper) {
        this.mapper = NullabilityUtil.requireNonNull(mapper, "mapper");
    }

    @Override
    public R deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        W written = context.deserialize(json, this.mapper.writtenTypeClass());
        R read = this.mapper.read(written);

        return Objects.requireNonNull(read, String.format("Could not find a mapping for: %s", read));
    }

    @Override
    public JsonElement serialize(R src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");

        W written = this.mapper.write(src);
        Objects.requireNonNull(written, String.format("Could not find a mapping for: %s", src));

        return context.serialize(written, this.mapper.writtenTypeClass());
    }
}