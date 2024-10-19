package net.hypejet.jet.data.codecs.registry.registries.event;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.event.GameEvent;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which deserializes and serializes a {@linkplain GameEvent game
 * event}.
 *
 * @since 1.0
 * @author Codestech
 * @see GameEvent
 * @see JsonCodec
 */
public final class GameEventJsonCodec implements JsonCodec<GameEvent> {

    private static final String NOTIFICATION_RADIUS_FIELD = "notification-radius-field";

    @Override
    public GameEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");
        return new GameEvent(JsonUtil.read(NOTIFICATION_RADIUS_FIELD, int.class, object, context));
    }

    @Override
    public JsonElement serialize(GameEvent src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");

        JsonObject object = new JsonObject();
        object.addProperty(NOTIFICATION_RADIUS_FIELD, src.notificationRadius());

        return object;
    }
}