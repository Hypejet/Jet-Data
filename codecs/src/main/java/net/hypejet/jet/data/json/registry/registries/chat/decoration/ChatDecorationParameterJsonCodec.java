package net.hypejet.jet.data.json.registry.registries.chat.decoration;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.json.JsonCodec;
import net.hypejet.jet.registry.registries.chat.decoration.ChatDecorationParameter;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec}, which serializes and deserializes a {@linkplain ChatDecorationParameter
 * chat decoration parameter}.
 *
 * @since 1.0
 * @author Codestech
 * @see ChatDecorationParameter
 * @see JsonCodec
 */
public final class ChatDecorationParameterJsonCodec implements JsonCodec<ChatDecorationParameter> {

    private static final String SENDER = "sender";
    private static final String TARGET = "target";
    private static final String CONTENT = "content";

    @Override
    public ChatDecorationParameter deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        String value = json.getAsString();
        return switch (value) {
            case SENDER -> ChatDecorationParameter.SENDER;
            case TARGET -> ChatDecorationParameter.TARGET;
            case CONTENT -> ChatDecorationParameter.CONTENT;
            default -> throw new IllegalArgumentException(String.format(
                    "Unknown chat decoration parameter of %s", value
            ));
        };
    }

    @Override
    public JsonElement serialize(ChatDecorationParameter src, Type typeOfSrc, JsonSerializationContext context) {
        String value = switch (src) {
            case SENDER -> SENDER;
            case TARGET -> TARGET;
            case CONTENT -> CONTENT;
        };
        return new JsonPrimitive(value);
    }
}