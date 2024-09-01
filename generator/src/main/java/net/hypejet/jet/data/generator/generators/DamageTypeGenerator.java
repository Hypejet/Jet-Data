package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.DataPackAdapter;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.registry.registries.damage.DamageEffectType;
import net.hypejet.jet.registry.registries.damage.DamageScalingType;
import net.hypejet.jet.registry.registries.damage.DamageType;
import net.hypejet.jet.registry.registries.damage.DamageTypeRegistryEntry;
import net.hypejet.jet.registry.registries.damage.DeathMessageType;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.repository.KnownPack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@linkplain Generator generator} which generates {@linkplain DamageTypeRegistryEntry damage type
 * registry entries}.
 *
 * @since 1.0
 * @author Codestech
 * @see DamageTypeRegistryEntry
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
        super("damage-types", "DamageTypeIdentifiers");
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<DamageTypeRegistryEntry> generate(@NonNull Logger logger) {
        List<DamageTypeRegistryEntry> entries = new ArrayList<>();
        Registry<net.minecraft.world.damagesource.DamageType> registry = this.registryAccess
                .registryOrThrow(Registries.DAMAGE_TYPE);

        registry.forEach(damageType -> {
            ResourceKey<net.minecraft.world.damagesource.DamageType> key = registry.getResourceKey(damageType)
                    .orElseThrow();
            KnownPack knownPack = registry.registrationInfo(key)
                    .flatMap(RegistrationInfo::knownPackInfo)
                    .orElseThrow();

            entries.add(new DamageTypeRegistryEntry(IdentifierAdapter.convert(key.location()),
                    DataPackAdapter.dataPack(knownPack), damageType(damageType)));
        });

        return List.copyOf(entries);
    }

    private static @NotNull DamageType damageType(net.minecraft.world.damagesource.DamageType damageType) {
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

        return new DamageType(damageType.msgId(), scalingType, damageType.exhaustion(), effectType, deathMessageType);
    }
}