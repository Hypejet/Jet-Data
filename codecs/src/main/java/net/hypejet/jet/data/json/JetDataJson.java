package net.hypejet.jet.data.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.hypejet.jet.color.Color;
import net.hypejet.jet.data.json.registry.registries.biome.BiomeJsonCodec;
import net.hypejet.jet.data.json.registry.registries.biome.binary.BinaryTagJsonCodec;
import net.hypejet.jet.data.json.registry.registries.biome.effects.BiomeEffectSettingsJsonCodec;
import net.hypejet.jet.data.json.registry.registries.biome.effects.modifier.GrassColorModifierJsonCodec;
import net.hypejet.jet.data.json.registry.registries.biome.effects.music.BiomeMusicJsonCodec;
import net.hypejet.jet.data.json.registry.registries.biome.effects.particle.BiomeParticleSettingsJsonCodec;
import net.hypejet.jet.data.json.registry.registries.biome.effects.sound.BiomeAdditionalSoundJsonCodec;
import net.hypejet.jet.data.json.registry.registries.biome.effects.sound.BiomeMoodSoundJsonCodec;
import net.hypejet.jet.data.json.registry.registries.biome.effects.sound.BiomeSoundEventJsonCodec;
import net.hypejet.jet.data.json.registry.registries.biome.modifier.BiomeTemperatureModifierJsonCodec;
import net.hypejet.jet.data.json.color.ColorJsonCodec;
import net.hypejet.jet.data.json.datapack.DataPackJsonCodec;
import net.hypejet.jet.data.json.registry.RegistryEntryJsonCodec;
import net.hypejet.jet.data.json.key.KeyJsonCodec;
import net.hypejet.jet.data.json.registry.registries.chat.ChatTypeJsonCodec;
import net.hypejet.jet.data.json.registry.registries.chat.decoration.ChatDecorationJsonCodec;
import net.hypejet.jet.data.json.registry.registries.chat.decoration.ChatDecorationParameterJsonCodec;
import net.hypejet.jet.data.json.registry.registries.dimension.DimensionTypeJsonCodec;
import net.hypejet.jet.data.json.registry.registries.dimension.number.IntegerProviderJsonCodec;
import net.hypejet.jet.data.json.registry.registries.dimension.number.WeightedListEntryJsonCodec;
import net.hypejet.jet.number.IntegerProvider;
import net.hypejet.jet.pack.DataPack;
import net.hypejet.jet.registry.RegistryEntry;
import net.hypejet.jet.registry.registries.biome.Biome;
import net.hypejet.jet.registry.registries.biome.BiomeRegistryEntry;
import net.hypejet.jet.registry.registries.biome.effects.BiomeEffectSettings;
import net.hypejet.jet.registry.registries.biome.effects.modifier.GrassColorModifier;
import net.hypejet.jet.registry.registries.biome.effects.music.BiomeMusic;
import net.hypejet.jet.registry.registries.biome.effects.particle.BiomeParticleSettings;
import net.hypejet.jet.registry.registries.biome.effects.sound.BiomeAdditionalSound;
import net.hypejet.jet.registry.registries.biome.effects.sound.BiomeMoodSound;
import net.hypejet.jet.registry.registries.biome.effects.sound.BiomeSoundEvent;
import net.hypejet.jet.registry.registries.biome.temperature.BiomeTemperatureModifier;
import net.hypejet.jet.registry.registries.chat.ChatType;
import net.hypejet.jet.registry.registries.chat.ChatTypeRegistryEntry;
import net.hypejet.jet.registry.registries.chat.decoration.ChatDecoration;
import net.hypejet.jet.registry.registries.chat.decoration.ChatDecorationParameter;
import net.hypejet.jet.registry.registries.dimension.DimensionType;
import net.hypejet.jet.registry.registries.dimension.DimensionTypeRegistryEntry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
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

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Color.class, new ColorJsonCodec())
            .registerTypeAdapter(Key.class, new KeyJsonCodec())
            .registerTypeAdapter(BinaryTag.class, new BinaryTagJsonCodec())
            .registerTypeAdapter(GrassColorModifier.class, new GrassColorModifierJsonCodec())
            .registerTypeAdapter(DataPack.class, new DataPackJsonCodec())
            // Biomes
            .registerTypeAdapter(BiomeMusic.class, new BiomeMusicJsonCodec())
            .registerTypeAdapter(BiomeParticleSettings.class, new BiomeParticleSettingsJsonCodec())
            .registerTypeAdapter(BiomeAdditionalSound.class, new BiomeAdditionalSoundJsonCodec())
            .registerTypeAdapter(BiomeMoodSound.class, new BiomeMoodSoundJsonCodec())
            .registerTypeAdapter(BiomeSoundEvent.class, new BiomeSoundEventJsonCodec())
            .registerTypeAdapter(BiomeEffectSettings.class, new BiomeEffectSettingsJsonCodec())
            .registerTypeAdapter(Biome.class, new BiomeJsonCodec())
            .registerTypeAdapter(BiomeTemperatureModifier.class, new BiomeTemperatureModifierJsonCodec())
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
}