package net.hypejet.jet.data.codecs.registry.registries.chat.decoration;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registry.registries.chat.decoration.ChatDecoration;
import net.hypejet.jet.data.model.api.registry.registries.chat.decoration.ChatDecorationParameter;
import net.kyori.adventure.text.format.Style;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@linkplain JsonCodec json codec} which serializes and deserializes a {@linkplain ChatDecoration chat
 * decoration}.
 *
 * @since 1.0
 * @author Codestech
 * @see ChatDecoration
 * @see JsonCodec
 */
public final class ChatDecorationJsonCodec implements JsonCodec<ChatDecoration> {

    private static final String TRANSLATION_KEY = "translation-key";
    private static final String STYLE = "style";
    private static final String PARAMETERS = "parameters";

    @Override
    public ChatDecoration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");

        JsonArray jsonParameterArray = JsonUtil.read(PARAMETERS, JsonArray.class, object, context);
        List<ChatDecorationParameter> parameters = new ArrayList<>();

        for (JsonElement element : jsonParameterArray)
            parameters.add(context.deserialize(element, ChatDecorationParameter.class));

        return new ChatDecoration(JsonUtil.read(TRANSLATION_KEY, String.class, object, context),
                JsonUtil.readOptional(STYLE, Style.class, object, context), List.copyOf(parameters));
    }

    @Override
    public JsonElement serialize(ChatDecoration src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        JsonUtil.write(TRANSLATION_KEY, src.translationKey(), object, context);
        JsonUtil.writeOptional(STYLE, src.style(), Style.class, object, context);

        JsonArray jsonParameterArray = new JsonArray();
        for (ChatDecorationParameter parameter : src.parameters())
            jsonParameterArray.add(context.serialize(parameter));

        object.add(PARAMETERS, jsonParameterArray);
        return object;
    }
}