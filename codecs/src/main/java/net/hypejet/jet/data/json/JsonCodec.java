package net.hypejet.jet.data.json;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * Represents a combination of a {@linkplain JsonDeserializer json deserializer} and a {@linkplain JsonSerializer json
 * serializer}, which deserializer and serialize the same {@linkplain O object}.
 *
 * @param <O> a type of the object
 * @since 1.0
 * @author Codestech
 * @see JsonDeserializer
 * @see JsonSerializer
 */
public interface JsonCodec<O> extends JsonDeserializer<O>, JsonSerializer<O> {}