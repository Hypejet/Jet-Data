package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.registry.registries.damage.DamageEffectType;
import net.hypejet.jet.data.model.api.registry.registries.damage.DamageScalingType;
import net.hypejet.jet.data.model.api.registry.registries.damage.DamageType;
import net.hypejet.jet.data.model.api.registry.registries.damage.DeathMessageType;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * Represents a {@linkplain Generator generator} of {@linkplain DamageType damage types}.
 *
 * @since 1.0
 * @author Codestech
 * @see DamageType
 * @see Generator
 */
public final class DamageTypeGenerator extends Generator<DamageType> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain DamageTypeGenerator damage type generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public DamageTypeGenerator(@NonNull RegistryAccess registryAccess) {
        super(new GeneratorName("Damage", "Type", "Generator"),
                new ResourceFileSettings("damage-types", JetDataJson.createDamageTypesGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.API, "DamageTypes"));
        this.registryAccess = NullabilityUtil.requireNonNull(registryAccess, "registry access");
    }

    @Override
    public @NonNull List<DataRegistryEntry<DamageType>> generate() {
        Registry<net.minecraft.world.damagesource.DamageType> registry = this.registryAccess
                .lookupOrThrow(Registries.DAMAGE_TYPE);
        return RegistryUtil.createEntries(registry, damageType -> {
            DamageEffectType effectType = switch (damageType.effects()) {
                case HURT -> DamageEffectType.HURT;
                case THORNS -> DamageEffectType.THORNS;
                case DROWNING -> DamageEffectType.DROWNING;
                case BURNING -> DamageEffectType.BURNING;
                case POKING -> DamageEffectType.POKING;
                case FREEZING -> DamageEffectType.FREEZING;
            };

            DeathMessageType deathMessageType = switch (damageType.deathMessageType()) {
                case DEFAULT -> DeathMessageType.DEFAULT;
                case FALL_VARIANTS -> DeathMessageType.FALL_VARIANTS;
                case INTENTIONAL_GAME_DESIGN -> DeathMessageType.INTENTIONAL_GAME_DESIGN;
            };

            DamageScalingType scalingType = switch (damageType.scaling()) {
                case NEVER -> DamageScalingType.NEVER;
                case WHEN_CAUSED_BY_LIVING_NON_PLAYER -> DamageScalingType.WHEN_CAUSED_BY_LIVING_NON_PLAYER;
                case ALWAYS -> DamageScalingType.ALWAYS;
            };

            return new DamageType(damageType.msgId(), scalingType,
                    damageType.exhaustion(), effectType, deathMessageType);
        });
    }
}