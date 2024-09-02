package net.hypejet.jet.data.codecs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.hypejet.jet.data.codecs.registry.registries.armor.pattern.ArmorTrimPatternJsonCodec;
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
import net.hypejet.jet.data.codecs.registry.RegistryEntryJsonCodec;
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
import net.hypejet.jet.data.model.registry.RegistryEntry;
import net.hypejet.jet.data.model.registry.registries.armor.pattern.ArmorTrimPattern;
import net.hypejet.jet.data.model.registry.registries.armor.pattern.ArmorTrimPatternRegistryEntry;
import net.hypejet.jet.data.model.registry.registries.biome.Biome;
import net.hypejet.jet.data.model.registry.registries.biome.BiomeRegistryEntry;
import net.hypejet.jet.data.model.registry.registries.biome.effects.BiomeEffectSettings;
import net.hypejet.jet.data.model.registry.registries.biome.effects.modifier.GrassColorModifier;
import net.hypejet.jet.data.model.registry.registries.biome.effects.music.BiomeMusic;
import net.hypejet.jet.data.model.registry.registries.biome.effects.particle.BiomeParticleSettings;
import net.hypejet.jet.data.model.registry.registries.biome.effects.sound.BiomeAdditionalSound;
import net.hypejet.jet.data.model.registry.registries.biome.effects.sound.BiomeMoodSound;
import net.hypejet.jet.data.model.registry.registries.biome.effects.sound.BiomeSoundEvent;
import net.hypejet.jet.data.model.registry.registries.biome.temperature.BiomeTemperatureModifier;
import net.hypejet.jet.data.model.registry.registries.chat.ChatType;
import net.hypejet.jet.data.model.registry.registries.chat.ChatTypeRegistryEntry;
import net.hypejet.jet.data.model.registry.registries.chat.decoration.ChatDecoration;
import net.hypejet.jet.data.model.registry.registries.chat.decoration.ChatDecorationParameter;
import net.hypejet.jet.data.model.registry.registries.damage.DamageEffectType;
import net.hypejet.jet.data.model.registry.registries.damage.DamageScalingType;
import net.hypejet.jet.data.model.registry.registries.damage.DamageType;
import net.hypejet.jet.data.model.registry.registries.damage.DamageTypeRegistryEntry;
import net.hypejet.jet.data.model.registry.registries.damage.DeathMessageType;
import net.hypejet.jet.data.model.registry.registries.dimension.DimensionType;
import net.hypejet.jet.data.model.registry.registries.dimension.DimensionTypeRegistryEntry;
import net.hypejet.jet.data.model.registry.registries.painting.PaintingVariant;
import net.hypejet.jet.data.model.registry.registries.painting.PaintingVariantRegistryEntry;
import net.hypejet.jet.data.model.registry.registries.wolf.WolfBiomes;
import net.hypejet.jet.data.model.registry.registries.wolf.WolfVariant;
import net.hypejet.jet.data.model.registry.registries.wolf.WolfVariantRegistryEntry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a holder of a {@linkplain Gson gson} serializing {@linkplain RegistryEntry registry entries}
 * and their values.
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
            .registerTypeAdapter(BiomeRegistryEntry.class,
                    new RegistryEntryJsonCodec<>(Biome.class, BiomeRegistryEntry::new))
            // Dimension types
            .registerTypeAdapter(IntegerProvider.WeightedList.Entry.class, new WeightedListEntryJsonCodec())
            .registerTypeAdapter(IntegerProvider.class, new IntegerProviderJsonCodec())
            .registerTypeAdapter(DimensionType.class, new DimensionTypeJsonCodec())
            .registerTypeAdapter(DimensionTypeRegistryEntry.class,
                    new RegistryEntryJsonCodec<>(DimensionType.class, DimensionTypeRegistryEntry::new))
            // Chat types
            .registerTypeAdapter(ChatType.class, new ChatTypeJsonCodec())
            .registerTypeAdapter(ChatDecoration.class, new ChatDecorationJsonCodec())
            .registerTypeAdapter(ChatDecorationParameter.class, new ChatDecorationParameterJsonCodec())
            .registerTypeAdapter(ChatTypeRegistryEntry.class,
                    new RegistryEntryJsonCodec<>(ChatType.class, ChatTypeRegistryEntry::new))
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
            .registerTypeAdapter(DamageTypeRegistryEntry.class,
                    new RegistryEntryJsonCodec<>(DamageType.class, DamageTypeRegistryEntry::new))
            // Wolf variants
            .registerTypeAdapter(WolfVariant.class, new WolfVariantJsonCodec())
            .registerTypeAdapter(WolfBiomes.class, new WolfBiomesJsonCodec())
            .registerTypeAdapter(WolfVariantRegistryEntry.class,
                    new RegistryEntryJsonCodec<>(WolfVariant.class, WolfVariantRegistryEntry::new))
            // Painting variants
            .registerTypeAdapter(PaintingVariant.class, new PaintingVariantJsonCodec())
            .registerTypeAdapter(PaintingVariantRegistryEntry.class,
                    new RegistryEntryJsonCodec<>(PaintingVariant.class, PaintingVariantRegistryEntry::new))
            // Armor trim patterns
            .registerTypeAdapter(ArmorTrimPattern.class, new ArmorTrimPatternJsonCodec())
            .registerTypeAdapter(ArmorTrimPatternRegistryEntry.class,
                    new RegistryEntryJsonCodec<>(ArmorTrimPattern.class, ArmorTrimPatternRegistryEntry::new))
            // Misc type adapters
            .registerTypeAdapter(Color.class, new ColorJsonCodec())
            .registerTypeAdapter(Key.class, new KeyJsonCodec())
            .registerTypeAdapter(BinaryTag.class, new BinaryTagJsonCodec())
            .registerTypeAdapter(DataPack.class, new DataPackJsonCodec())
            .create();

    private JetDataJson() {}

    /**
     * Deserializes {@linkplain RegistryEntry registry entries} from a {@linkplain String string}.
     *
     * <p>The deserialized registry entries keep their order, which they were serialized with.</p>
     *
     * @param serialized the string
     * @param entryClass a class of type of the registry entries
     * @return the deserialized registry entries
     * @param <E> a type of the registry entries
     * @since 1.0
     */
    public static <E extends RegistryEntry<?>> @NonNull List<E> deserialize(@NonNull String serialized,
                                                                            @NonNull Class<E> entryClass) {
        JsonArray array = GSON.fromJson(serialized, JsonArray.class);
        List<E> entries = new ArrayList<>();
        for (JsonElement element : array)
            entries.add(GSON.fromJson(element, entryClass));
        return List.copyOf(entries);
    }

    /**
     * Serializes {@linkplain RegistryEntry registry entries} to a {@linkplain String string}.
     *
     * @param entries the registry entries
     * @return the string
     * @since 1.0
     */
    public static @NonNull String serialize(@NonNull Collection<? extends RegistryEntry<?>> entries) {
        JsonArray array = new JsonArray();
        for (RegistryEntry<?> entry : entries)
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