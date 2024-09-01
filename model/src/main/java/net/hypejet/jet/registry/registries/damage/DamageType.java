package net.hypejet.jet.registry.registries.damage;

import net.hypejet.jet.utils.NullabilityUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents a type of Minecraft damage.
 *
 * @param messageId an identifier of message to be sent when a player dies due to damage with this type
 * @param damageScalingType a type of difficulty-based scaling of damages with this type
 * @param exhaustion an amount of exhaustion caused by damages with this type
 * @param damageEffectType an effect played when a player suffers damage with this type, including a sound that
 *                         is played
 * @param deathMessageType a type of death message construction
 * @since 1.0
 * @author Codestech
 */
public record DamageType(@NonNull String messageId, @NonNull DamageScalingType damageScalingType, float exhaustion,
                         @Nullable DamageEffectType damageEffectType, @Nullable DeathMessageType deathMessageType) {
    /**
     * Constructs the {@linkplain DamageType damage type}.
     *
     * @param messageId an identifier of message to be sent when a player dies due to damage with this type
     * @param damageScalingType a type of difficulty-based scaling of damages with this type
     * @param exhaustion an amount of exhaustion caused by damages with this type
     * @param damageEffectType an effect played when a player suffers damage with this type, including a sound that
     *                         is played
     * @param deathMessageType a type of death message construction
     * @since 1.0
     */
    public DamageType {
        NullabilityUtil.requireNonNull(messageId, "message identifier");
        NullabilityUtil.requireNonNull(damageScalingType, "damage scaling type");
        NullabilityUtil.requireNonNull(damageEffectType, "damage effect type");
        NullabilityUtil.requireNonNull(deathMessageType, "death message type");
    }
}