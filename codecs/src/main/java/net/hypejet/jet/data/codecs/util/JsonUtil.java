package net.hypejet.jet.data.codecs.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.model.utils.NullabilityUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents a utility for a {@linkplain JsonElement json element} management.
 *
 * @since 1.0
 * @author Codestech
 * @see JsonElement
 */
public final class JsonUtil {

    private JsonUtil() {}

    /**
     * Reads and deserializes a value from a {@linkplain JsonObject json object}.
     *
     * @param name a name of the value
     * @param clazz a class of the deserialized value
     * @param jsonObject the json object
     * @param context a context of the json deserialization
     * @return the
     * @param <T> a type of the deserialized value
     * @since 1.0
     */
    public static <T> @NonNull T read(@NonNull String name, @NonNull Class<T> clazz, @NonNull JsonObject jsonObject,
                                      @NonNull JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(name, "name");
        NullabilityUtil.requireNonNull(clazz, "class");
        NullabilityUtil.requireNonNull(jsonObject, "json object");
        NullabilityUtil.requireNonNull(name, "context");

        JsonElement element = jsonObject.get(name);
        NullabilityUtil.requireNonNull(element, "element");

        return context.deserialize(element, clazz);
    }

    /**
     * Reads and deserializes an optional value from a {@linkplain JsonObject json object}.
     *
     * @param name a name of the value
     * @param clazz a class of the deserialized value
     * @param jsonObject the json object
     * @param context a context of the json deserialization
     * @return the
     * @param <T> a type of the deserialized value
     * @since 1.0
     */
    public static <T> @Nullable T readOptional(@NonNull String name, @NonNull Class<T> clazz,
                                               @NonNull JsonObject jsonObject,
                                               @NonNull JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(name, "name");
        NullabilityUtil.requireNonNull(clazz, "class");
        NullabilityUtil.requireNonNull(jsonObject, "json object");
        NullabilityUtil.requireNonNull(context, "context");

        JsonElement element = jsonObject.get(name);
        if (element == null)
            return null;

        return context.deserialize(element, clazz);
    }

    /**
     * Writes an {@linkplain Object object} into a {@linkplain JsonObject json object}.
     *
     * @param name a name of the object
     * @param object the object
     * @param jsonObject the json object
     * @param context a context of the json serialization
     * @param <T> a type of the object
     * @since 1.0
     */
    public static <T> void write(@NonNull String name, @NonNull T object, @NonNull JsonObject jsonObject,
                                 @NonNull JsonSerializationContext context) {
        write(name, object, object.getClass(), jsonObject, context);
    }

    /**
     * Writes an {@linkplain Object object} into a {@linkplain JsonObject json object}.
     *
     * @param name a name of the object
     * @param clazz a class of the object, which should be used for finding a json serializer
     * @param object the object
     * @param jsonObject the json object
     * @param context a context of the json serialization
     * @param <T> a type of the object
     * @since 1.0
     */
    public static <T> void write(@NonNull String name, @NonNull T object, @NonNull Class<? extends T> clazz,
                                 @NonNull JsonObject jsonObject, @NonNull JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(name, "name");
        NullabilityUtil.requireNonNull(object, "object");
        NullabilityUtil.requireNonNull(jsonObject, "json object");
        NullabilityUtil.requireNonNull(context, "context");

        if (!clazz.isAssignableFrom(object.getClass()))
            throw new IllegalArgumentException("The class of the type is not assignable from a class the object");

        jsonObject.add(name, context.serialize(object, clazz));
    }

    /**
     * Writes an optional {@linkplain Object object} into a {@linkplain JsonObject json object}.
     *
     * @param name a name of the optional object
     * @param object the optional object
     * @param jsonObject the json object
     * @param context a context of the json serialization
     * @param <T> a type of the object
     * @since 1.0
     */
    public static <T> void writeOptional(@NonNull String name, @Nullable T object, @NonNull JsonObject jsonObject,
                                         @NonNull JsonSerializationContext context) {
        writeOptional(name, object, object == null ? Object.class : object.getClass(), jsonObject, context);
    }

    /**
     * Writes an optional {@linkplain Object object} into a {@linkplain JsonObject json object}.
     *
     * @param name a name of the optional object
     * @param clazz a class of the object, which should be used for finding a json serializer
     * @param object the optional object
     * @param jsonObject the json object
     * @param context a context of the json serialization
     * @param <T> a type of the object
     * @since 1.0
     */
    public static <T> void writeOptional(@NonNull String name, @Nullable T object, @NonNull Class<? extends T> clazz,
                                         @NonNull JsonObject jsonObject, @NonNull JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(name, "name");
        NullabilityUtil.requireNonNull(jsonObject, "json object");
        NullabilityUtil.requireNonNull(context, "context");

        if (object == null)
            return;
        if (!clazz.isAssignableFrom(object.getClass()))
            throw new IllegalArgumentException("The class of the type is not assignable from a class the object");

        jsonObject.add(name, context.serialize(object, clazz));
    }
}