package net.hypejet.jet.data.generator.generators.api;

import com.mojang.serialization.DataResult;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.BinaryTagAdapter;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.adapter.PackAdapter;
import net.hypejet.jet.data.generator.util.ReflectionUtil;
import net.hypejet.jet.data.model.api.color.Color;
import net.hypejet.jet.data.model.api.registry.registries.biome.Biome;
import net.hypejet.jet.data.model.api.registry.registries.biome.BiomeDataRegistryEntry;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.BiomeEffectSettings;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.modifier.GrassColorModifier;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.music.BiomeMusic;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.particle.BiomeParticleSettings;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.sound.BiomeAdditionalSound;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.sound.BiomeMoodSound;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.sound.BiomeSoundEvent;
import net.hypejet.jet.data.model.api.registry.registries.biome.temperature.BiomeTemperatureModifier;
import net.kyori.adventure.nbt.BinaryTag;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a {@linkplain Generator generator}, which generates all {@linkplain Biome biomes} using data defined
 * in a {@linkplain Registry registry}.
 *
 * @since 1.0
 * @author Codestech
 * @see Biome
 * @see Registry
 * @see Generator
 */
public final class BiomeGenerator extends Generator<Biome> {

    private static final Class<?> CLIMATE_SETTINGS_CLASS;
    private static final Field CLIMATE_SETTINGS_FIELD;

    private static final Method TEMPERATURE_MODIFIER_METHOD;
    private static final Method DOWNFALL_METHOD;

    private static final Field PARTICLE_SETTINGS_PROBABILITY_FIELD;
    private static final Method SOUND_EVENT_FIXED_RANGE_METHOD;

    private final RegistryAccess registryAccess;

    static {
        try {
            Class<?> biomeClass = net.minecraft.world.level.biome.Biome.class;
            CLIMATE_SETTINGS_CLASS = Class.forName(biomeClass.getName() + "$ClimateSettings");
            CLIMATE_SETTINGS_FIELD = biomeClass.getDeclaredField("climateSettings");
            TEMPERATURE_MODIFIER_METHOD = CLIMATE_SETTINGS_CLASS.getDeclaredMethod("temperatureModifier");
            DOWNFALL_METHOD = CLIMATE_SETTINGS_CLASS.getDeclaredMethod("downfall");
            PARTICLE_SETTINGS_PROBABILITY_FIELD = AmbientParticleSettings.class.getDeclaredField("probability");
            SOUND_EVENT_FIXED_RANGE_METHOD = SoundEvent.class.getDeclaredMethod("fixedRange");
        } catch (NoSuchFieldException | ClassNotFoundException | NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Constructs a {@linkplain BiomeGenerator biome generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public BiomeGenerator(@NonNull RegistryAccess registryAccess) {
        super("biomes", "Biomes", true);
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<BiomeDataRegistryEntry> generate() {
        List<BiomeDataRegistryEntry> entries = new ArrayList<>();

        Registry<net.minecraft.world.level.biome.Biome> registry = this.registryAccess.lookupOrThrow(Registries.BIOME);
        registry.forEach(biome -> {
            ResourceKey<net.minecraft.world.level.biome.Biome> key = registry.getResourceKey(biome).orElseThrow();
            KnownPack knownPack = registry.registrationInfo(key)
                    .flatMap(RegistrationInfo::knownPackInfo)
                    .orElseThrow();

            Object climateSettings = ReflectionUtil.access(CLIMATE_SETTINGS_FIELD, biome, Field::get);
            Biome convertedBiome = Biome.builder()
                    .hasPrecipitation(biome.hasPrecipitation())
                    .temperature(biome.getBaseTemperature())
                    .temperatureModifier(temperatureModifier(climateSettings))
                    .downfall((float) ReflectionUtil.invoke(DOWNFALL_METHOD, climateSettings))
                    .effectSettings(biomeEffects(biome.getSpecialEffects()))
                    .build();

            entries.add(new BiomeDataRegistryEntry(IdentifierAdapter.convert(key.location()),
                    convertedBiome, PackAdapter.convert(knownPack)));
        });

        return List.copyOf(entries);
    }

    private @NonNull BiomeEffectSettings biomeEffects(@NonNull BiomeSpecialEffects effects) {
        GrassColorModifier modifier = switch (effects.getGrassColorModifier()) {
            case NONE -> GrassColorModifier.NONE;
            case DARK_FOREST -> GrassColorModifier.DARK_FOREST;
            case SWAMP -> GrassColorModifier.SWAMP;
        };

        return BiomeEffectSettings.builder()
                .fogColor(new Color(effects.getFogColor()))
                .waterColor(new Color(effects.getWaterColor()))
                .waterFogColor(new Color(effects.getWaterFogColor()))
                .skyColor(new Color(effects.getSkyColor()))
                .foliageColorOverride(effects.getFoliageColorOverride()
                        .map(Color::new)
                        .orElse(null))
                .grassColorOverride(effects.getGrassColorOverride()
                        .map(Color::new)
                        .orElse(null))
                .grassColorModifier(modifier)
                .particleSettings(effects.getAmbientParticleSettings()
                        .map(this::particleSettings)
                        .orElse(null))
                .ambientSound(effects.getAmbientLoopSoundEvent()
                        .map(Holder::value)
                        .map(BiomeGenerator::soundEvent)
                        .orElse(null))
                .moodSound(effects.getAmbientMoodSettings()
                        .map(BiomeGenerator::moodSound)
                        .orElse(null))
                .additionalSound(effects.getAmbientAdditionsSettings()
                        .map(BiomeGenerator::additionalSound)
                        .orElse(null))
                .biomeMusic(effects.getBackgroundMusic()
                        .map(BiomeGenerator::music)
                        .orElse(null))
                .build();
    }

    private @NonNull BiomeParticleSettings particleSettings(@NonNull AmbientParticleSettings settings) {
        ParticleOptions options = settings.getOptions();
        BinaryTag convertedTag;

        ParticleType type = options.getType();

        DataResult<Tag> result = type.codec()
                .codec()
                .encodeStart(this.registryAccess.createSerializationContext(NbtOps.INSTANCE), options);

        Tag tag = result.getOrThrow();
        float probability = ReflectionUtil.access(PARTICLE_SETTINGS_PROBABILITY_FIELD, settings, Field::getFloat);

        ResourceLocation location = BuiltInRegistries.PARTICLE_TYPE.getKey(type);
        if (location == null)
            throw new IllegalArgumentException("An identifier of the particle type is null");

        if (tag instanceof CompoundTag compoundTag && compoundTag.isEmpty()) {
            // Minecraft uses empty compound tag instead of nulls when a particle has no additional options
            convertedTag = null;
        } else {
            convertedTag = BinaryTagAdapter.convert(tag);
        }

        return new BiomeParticleSettings(IdentifierAdapter.convert(location), convertedTag, probability);
    }

    private static @NonNull BiomeMoodSound moodSound(@NonNull AmbientMoodSettings settings) {
        return new BiomeMoodSound(soundEvent(settings.getSoundEvent().value()), settings.getTickDelay(),
                settings.getBlockSearchExtent(), settings.getSoundPositionOffset());
    }

    private static @NonNull BiomeAdditionalSound additionalSound(@NonNull AmbientAdditionsSettings settings) {
        return new BiomeAdditionalSound(soundEvent(settings.getSoundEvent().value()), settings.getTickChance());
    }

    private static @NonNull BiomeMusic music(@NonNull Music music) {
        return new BiomeMusic(soundEvent(music.getEvent().value()), music.getMinDelay(), music.getMaxDelay(),
                music.replaceCurrentMusic());
    }

    private static @NonNull BiomeSoundEvent soundEvent(@NonNull SoundEvent event) {
        Optional<?> optionalFixedRange;
        boolean wasAccessible = SOUND_EVENT_FIXED_RANGE_METHOD.canAccess(event);

        try {
            if (!wasAccessible) SOUND_EVENT_FIXED_RANGE_METHOD.setAccessible(true);
            optionalFixedRange = (Optional<?>) SOUND_EVENT_FIXED_RANGE_METHOD.invoke(event);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException(exception);
        } finally {
            if (!wasAccessible) SOUND_EVENT_FIXED_RANGE_METHOD.setAccessible(false);
        }

        Float range = (Float) optionalFixedRange.orElse(null);
        return new BiomeSoundEvent(IdentifierAdapter.convert(event.location()), range);
    }

    private static @NonNull BiomeTemperatureModifier temperatureModifier(@NonNull Object climateSettings) {
        return switch ((TemperatureModifier) ReflectionUtil.invoke(TEMPERATURE_MODIFIER_METHOD, climateSettings)) {
            case NONE -> BiomeTemperatureModifier.NONE;
            case FROZEN -> BiomeTemperatureModifier.FROZEN;
        };
    }
}