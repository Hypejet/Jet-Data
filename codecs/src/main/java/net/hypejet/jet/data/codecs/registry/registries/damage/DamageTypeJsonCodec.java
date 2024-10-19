package net.hypejet.jet.data.codecs.registry.registries.damage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registries.damage.DamageEffectType;
import net.hypejet.jet.data.model.api.registries.damage.DamageScalingType;
import net.hypejet.jet.data.model.api.registries.damage.DamageType;
import net.hypejet.jet.data.model.api.registries.damage.DeathMessageType;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec} which serializes and deserializes a {@linkplain DamageType damage
 * type}.
 *
 * @since 1.0
 * @author Codestech
 * @see DamageType
 * @see JsonCodec
 */
public final class DamageTypeJsonCodec implements JsonCodec<DamageType> {

    private static final String MESSAGE_ID = "message-id";
    private static final String DAMAGE_SCALING_TYPE = "damage-scaling-type";
    private static final String EXHAUSTION = "exhaustion";
    private static final String DAMAGE_EFFECT_TYPE = "damage-effect-type";
    private static final String DEATH_MESSAGE_TYPE = "death-message-type";

    @Override
    public DamageType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return new DamageType(JsonUtil.read(MESSAGE_ID, String.class, object, context),
                JsonUtil.read(DAMAGE_SCALING_TYPE, DamageScalingType.class, object, context),
                JsonUtil.read(EXHAUSTION, float.class, object, context),
                JsonUtil.readOptional(DAMAGE_EFFECT_TYPE, DamageEffectType.class, object, context),
                JsonUtil.readOptional(DEATH_MESSAGE_TYPE, DeathMessageType.class, object, context));
    }

    @Override
    public JsonElement serialize(DamageType src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");

        JsonObject object = new JsonObject();
        object.addProperty(MESSAGE_ID, src.messageId());
        JsonUtil.write(DAMAGE_SCALING_TYPE, src.damageScalingType(), object, context);
        object.addProperty(EXHAUSTION, src.exhaustion());
        JsonUtil.writeOptional(DAMAGE_EFFECT_TYPE, src.damageEffectType(), object, context);
        JsonUtil.writeOptional(DEATH_MESSAGE_TYPE, src.deathMessageType(), object, context);

        return object;
    }
}