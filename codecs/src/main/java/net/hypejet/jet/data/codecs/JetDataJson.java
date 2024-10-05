package net.hypejet.jet.data.codecs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.hypejet.jet.data.codecs.registry.registries.armor.material.ArmorTrimMaterialJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.armor.pattern.ArmorTrimPatternJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.banner.BannerPatternJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.painting.PaintingVariantJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.wolf.WolfBiomesJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.wolf.WolfVariantJsonCodec;
import net.hypejet.jet.data.model.color.Color;
import net.hypejet.jet.data.codecs.mapper.MapperJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.BiomeJsonCodec;
import net.hypejet.jet.data.codecs.binary.BinaryTagJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.BiomeEffectSettingsJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.music.BiomeMusicJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.particle.BiomeParticleSettingsJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.sound.BiomeAdditionalSoundJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.sound.BiomeMoodSoundJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.sound.BiomeSoundEventJsonCodec;
import net.hypejet.jet.data.codecs.color.ColorJsonCodec;
import net.hypejet.jet.data.codecs.datapack.DataPackJsonCodec;
import net.hypejet.jet.data.codecs.registry.RegistryEntryDataJsonCodec;
import net.hypejet.jet.data.codecs.key.KeyJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.chat.ChatTypeJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.chat.decoration.ChatDecorationJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.chat.decoration.ChatDecorationParameterJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.damage.DamageTypeJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.dimension.DimensionTypeJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.dimension.number.IntegerProviderJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.dimension.number.WeightedListEntryJsonCodec;
import net.hypejet.jet.data.codecs.util.mapper.Mapper;
import net.hypejet.jet.data.model.number.IntegerProvider;
import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntryData;
import net.hypejet.jet.data.model.registry.registries.armor.material.ArmorTrimMaterial;
import net.hypejet.jet.data.model.registry.registries.armor.material.ArmorTrimMaterialRegistryEntryData;
import net.hypejet.jet.data.model.registry.registries.armor.pattern.ArmorTrimPattern;
import net.hypejet.jet.data.model.registry.registries.armor.pattern.ArmorTrimPatternRegistryEntryData;
import net.hypejet.jet.data.model.registry.registries.banner.BannerPattern;
import net.hypejet.jet.data.model.registry.registries.banner.BannerPatternRegistryEntryData;
import net.hypejet.jet.data.model.registry.registries.biome.Biome;
import net.hypejet.jet.data.model.registry.registries.biome.BiomeRegistryEntryData;
import net.hypejet.jet.data.model.registry.registries.biome.effects.BiomeEffectSettings;
import net.hypejet.jet.data.model.registry.registries.biome.effects.modifier.GrassColorModifier;
import net.hypejet.jet.data.model.registry.registries.biome.effects.music.BiomeMusic;
import net.hypejet.jet.data.model.registry.registries.biome.effects.particle.BiomeParticleSettings;
import net.hypejet.jet.data.model.registry.registries.biome.effects.sound.BiomeAdditionalSound;
import net.hypejet.jet.data.model.registry.registries.biome.effects.sound.BiomeMoodSound;
import net.hypejet.jet.data.model.registry.registries.biome.effects.sound.BiomeSoundEvent;
import net.hypejet.jet.data.model.registry.registries.biome.temperature.BiomeTemperatureModifier;
import net.hypejet.jet.data.model.registry.registries.chat.ChatType;
import net.hypejet.jet.data.model.registry.registries.chat.ChatTypeRegistryEntryData;
import net.hypejet.jet.data.model.registry.registries.chat.decoration.ChatDecoration;
import net.hypejet.jet.data.model.registry.registries.chat.decoration.ChatDecorationParameter;
import net.hypejet.jet.data.model.registry.registries.damage.DamageEffectType;
import net.hypejet.jet.data.model.registry.registries.damage.DamageScalingType;
import net.hypejet.jet.data.model.registry.registries.damage.DamageType;
import net.hypejet.jet.data.model.registry.registries.damage.DamageTypeRegistryEntryData;
import net.hypejet.jet.data.model.registry.registries.damage.DeathMessageType;
import net.hypejet.jet.data.model.registry.registries.dimension.DimensionType;
import net.hypejet.jet.data.model.registry.registries.dimension.DimensionTypeRegistryEntryData;
import net.hypejet.jet.data.model.registry.registries.painting.PaintingVariant;
import net.hypejet.jet.data.model.registry.registries.painting.PaintingVariantRegistryEntryData;
import net.hypejet.jet.data.model.registry.registries.wolf.WolfBiomes;
import net.hypejet.jet.data.model.registry.registries.wolf.WolfVariant;
import net.hypejet.jet.data.model.registry.registries.wolf.WolfVariantRegistryEntryData;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a holder of a {@linkplain Gson gson} serializing {@linkplain RegistryEntryData registry entry data
 * objects} and their values.
 *
 * @since 1.0
 * @author Codestech
 */
public final class JetDataJson {

    private static final Gson GSON = GsonComponentSerializer.gson().populator()
            .apply(new GsonBuilder())
            .setPrettyPrinting()
            // Biomes
            .registerTypeAdapter(BiomeMusic.class, new BiomeMusicJsonCodec())
            .registerTypeAdapter(BiomeParticleSettings.class, new BiomeParticleSettingsJsonCodec())
            .registerTypeAdapter(BiomeAdditionalSound.class, new BiomeAdditionalSoundJsonCodec())
            .registerTypeAdapter(BiomeMoodSound.class, new BiomeMoodSoundJsonCodec())
            .registerTypeAdapter(BiomeSoundEvent.class, new BiomeSoundEventJsonCodec())
            .registerTypeAdapter(BiomeEffectSettings.class, new BiomeEffectSettingsJsonCodec())
            .registerTypeAdapter(Biome.class, new BiomeJsonCodec())
            .registerTypeAdapter(BiomeTemperatureModifier.class, new MapperJsonCodec<>(
                    Mapper.builder(BiomeTemperatureModifier.class, String.class)
                            .register(BiomeTemperatureModifier.NONE, "none")
                            .register(BiomeTemperatureModifier.FROZEN, "frozen")
                            .build()
            ))
            .registerTypeAdapter(GrassColorModifier.class, new MapperJsonCodec<>(
                    Mapper.builder(GrassColorModifier.class, String.class)
                            .register(GrassColorModifier.NONE, "none")
                            .register(GrassColorModifier.DARK_FOREST, "dark-forest")
                            .register(GrassColorModifier.SWAMP, "swamp")
                            .build()
            ))
            .registerTypeAdapter(BiomeRegistryEntryData.class,
                    new RegistryEntryDataJsonCodec<>(Biome.class, BiomeRegistryEntryData::new))
            // Dimension types
            .registerTypeAdapter(IntegerProvider.WeightedList.Entry.class, new WeightedListEntryJsonCodec())
            .registerTypeAdapter(IntegerProvider.class, new IntegerProviderJsonCodec())
            .registerTypeAdapter(DimensionType.class, new DimensionTypeJsonCodec())
            .registerTypeAdapter(DimensionTypeRegistryEntryData.class,
                    new RegistryEntryDataJsonCodec<>(DimensionType.class, DimensionTypeRegistryEntryData::new))
            // Chat types
            .registerTypeAdapter(ChatType.class, new ChatTypeJsonCodec())
            .registerTypeAdapter(ChatDecoration.class, new ChatDecorationJsonCodec())
            .registerTypeAdapter(ChatDecorationParameter.class, new ChatDecorationParameterJsonCodec())
            .registerTypeAdapter(ChatTypeRegistryEntryData.class,
                    new RegistryEntryDataJsonCodec<>(ChatType.class, ChatTypeRegistryEntryData::new))
            // Damage types
            .registerTypeAdapter(DamageType.class, new DamageTypeJsonCodec())
            .registerTypeAdapter(DamageScalingType.class, new MapperJsonCodec<>(
                    Mapper.builder(DamageScalingType.class, String.class)
                            .register(DamageScalingType.NEVER, "never")
                            .register(DamageScalingType.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
                                    "when-caused-by-living-non-player")
                            .register(DamageScalingType.ALWAYS, "always")
                            .build()
            ))
            .registerTypeAdapter(DamageEffectType.class, new MapperJsonCodec<>(
                    Mapper.builder(DamageEffectType.class, String.class)
                            .register(DamageEffectType.HURT, "hurt")
                            .register(DamageEffectType.THORNS, "thorns")
                            .register(DamageEffectType.DROWNING, "drowning")
                            .register(DamageEffectType.BURNING, "burning")
                            .register(DamageEffectType.POKING, "poking")
                            .register(DamageEffectType.FREEZING, "freezing")
                            .build()
            ))
            .registerTypeAdapter(DeathMessageType.class, new MapperJsonCodec<>(
                    Mapper.builder(DeathMessageType.class, String.class)
                            .register(DeathMessageType.DEFAULT, "default")
                            .register(DeathMessageType.FALL_VARIANTS, "full-variants")
                            .register(DeathMessageType.INTENTIONAL_GAME_DESIGN, "intentional-game-design")
                            .build()
            ))
            .registerTypeAdapter(DamageTypeRegistryEntryData.class,
                    new RegistryEntryDataJsonCodec<>(DamageType.class, DamageTypeRegistryEntryData::new))
            // Wolf variants
            .registerTypeAdapter(WolfVariant.class, new WolfVariantJsonCodec())
            .registerTypeAdapter(WolfBiomes.class, new WolfBiomesJsonCodec())
            .registerTypeAdapter(WolfVariantRegistryEntryData.class,
                    new RegistryEntryDataJsonCodec<>(WolfVariant.class, WolfVariantRegistryEntryData::new))
            // Painting variants
            .registerTypeAdapter(PaintingVariant.class, new PaintingVariantJsonCodec())
            .registerTypeAdapter(PaintingVariantRegistryEntryData.class,
                    new RegistryEntryDataJsonCodec<>(PaintingVariant.class, PaintingVariantRegistryEntryData::new))
            // Armor trim patterns
            .registerTypeAdapter(ArmorTrimPattern.class, new ArmorTrimPatternJsonCodec())
            .registerTypeAdapter(ArmorTrimPatternRegistryEntryData.class,
                    new RegistryEntryDataJsonCodec<>(ArmorTrimPattern.class, ArmorTrimPatternRegistryEntryData::new))
            // Armor trim materials
            .registerTypeAdapter(ArmorTrimMaterial.class, new ArmorTrimMaterialJsonCodec())
            .registerTypeAdapter(ArmorTrimMaterialRegistryEntryData.class,
                    new RegistryEntryDataJsonCodec<>(ArmorTrimMaterial.class, ArmorTrimMaterialRegistryEntryData::new))
            // Banner patterns
            .registerTypeAdapter(BannerPattern.class, new BannerPatternJsonCodec())
            .registerTypeAdapter(BannerPatternRegistryEntryData.class,
                    new RegistryEntryDataJsonCodec<>(BannerPattern.class, BannerPatternRegistryEntryData::new))
            // Misc type adapters
            .registerTypeAdapter(Color.class, new ColorJsonCodec())
            .registerTypeAdapter(Key.class, new KeyJsonCodec())
            .registerTypeAdapter(BinaryTag.class, new BinaryTagJsonCodec())
            .registerTypeAdapter(DataPack.class, new DataPackJsonCodec())
            .create();

    private JetDataJson() {}

    /**
     * Deserializes {@linkplain RegistryEntryData registry entry data objects} from a {@linkplain String string}.
     *
     * <p>The deserialized registry entries keep their order, which they were serialized with.</p>
     *
     * @param serialized the string
     * @param entryClass a class of type of the registry entries
     * @return the deserialized registry entries
     * @param <E> a type of the registry entries
     * @since 1.0
     */
    public static <E extends RegistryEntryData<?>> @NonNull List<E> deserialize(@NonNull String serialized,
                                                                                @NonNull Class<E> entryClass) {
        JsonArray array = GSON.fromJson(serialized, JsonArray.class);
        List<E> entries = new ArrayList<>();
        for (JsonElement element : array)
            entries.add(GSON.fromJson(element, entryClass));
        return List.copyOf(entries);
    }

    /**
     * Serializes {@linkplain RegistryEntryData registry entry data objects} to a {@linkplain String string}.
     *
     * @param entries the registry entries
     * @return the string
     * @since 1.0
     */
    public static @NonNull String serialize(@NonNull Collection<? extends RegistryEntryData<?>> entries) {
        JsonArray array = new JsonArray();
        for (RegistryEntryData<?> entry : entries)
            array.add(GSON.toJsonTree(entry));
        return GSON.toJson(array);
    }

    /**
     * Gets an instance of {@linkplain Gson gson} which serializes and deserializes Jet data objects.
     *
     * @return the instance
     * @since 1.0
     */
    public static @NonNull Gson gson() {
        return GSON;
    }
}