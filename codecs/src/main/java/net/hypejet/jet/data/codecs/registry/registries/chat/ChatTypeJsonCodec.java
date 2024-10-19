package net.hypejet.jet.data.codecs.registry.registries.chat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registry.registries.chat.ChatType;
import net.hypejet.jet.data.model.api.registry.registries.chat.decoration.ChatDecoration;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec} which serializes and deserializes a {@linkplain ChatType chat type}.
 *
 * @since 1.0
 * @author Codestech
 * @see ChatType
 * @see JsonCodec
 */
public final class ChatTypeJsonCodec implements JsonCodec<ChatType> {

    private static final String CHAT_DECORATION = "chat-decoration";
    private static final String NARRATION_DECORATION = "narration-decoration";

    @Override
    public ChatType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return new ChatType(JsonUtil.read(CHAT_DECORATION, ChatDecoration.class, object, context),
                JsonUtil.read(NARRATION_DECORATION, ChatDecoration.class, object, context));
    }

    @Override
    public JsonElement serialize(ChatType src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");

        JsonObject object = new JsonObject();
        JsonUtil.write(CHAT_DECORATION, src.chatDecoration(), object, context);
        JsonUtil.write(NARRATION_DECORATION, src.narrationDecoration(), object, context);

        return object;
    }
}