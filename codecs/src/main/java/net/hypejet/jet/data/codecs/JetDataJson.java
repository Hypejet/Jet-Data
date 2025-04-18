package net.hypejet.jet.data.codecs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.hypejet.jet.data.codecs.binary.BinaryTagJsonCodec;
import net.hypejet.jet.data.codecs.color.ColorJsonCodec;
import net.hypejet.jet.data.codecs.key.KeyJsonCodec;
import net.hypejet.jet.data.codecs.mapper.MapperJsonCodec;
import net.hypejet.jet.data.codecs.pack.FeaturePackJsonCodec;
import net.hypejet.jet.data.codecs.pack.PackInfoCodec;
import net.hypejet.jet.data.codecs.registry.RegistryEntryDataJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.armor.material.ArmorTrimMaterialJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.armor.pattern.ArmorTrimPatternJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.banner.BannerPatternJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.BiomeJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.BiomeEffectSettingsJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.music.BiomeMusicJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.particle.BiomeParticleSettingsJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.sound.BiomeAdditionalSoundJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.sound.BiomeMoodSoundJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.biome.effects.sound.BiomeSoundEventJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.block.BlockEntityTypeJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.block.BlockJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.block.BlockStateJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.chat.ChatTypeJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.chat.decoration.ChatDecorationJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.chat.decoration.ChatDecorationParameterJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.damage.DamageTypeJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.dimension.DimensionTypeJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.dimension.number.IntegerProviderJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.dimension.number.WeightedListEntryJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.entity.EntityTypeJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.event.GameEventJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.fluid.FluidJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.item.ItemJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.painting.PaintingVariantJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.wolf.WolfBiomesJsonCodec;
import net.hypejet.jet.data.codecs.registry.registries.wolf.WolfVariantJsonCodec;
import net.hypejet.jet.data.codecs.util.mapper.Mapper;
import net.hypejet.jet.data.model.api.color.Color;
import net.hypejet.jet.data.model.api.entity.EntityType;
import net.hypejet.jet.data.model.api.event.GameEvent;
import net.hypejet.jet.data.model.api.number.IntegerProvider;
import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.server.registry.registries.block.entity.BlockEntityType;
import net.hypejet.jet.data.model.server.registry.registries.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.registries.armor.material.ArmorTrimMaterial;
import net.hypejet.jet.data.model.api.registries.armor.pattern.ArmorTrimPattern;
import net.hypejet.jet.data.model.api.registries.banner.BannerPattern;
import net.hypejet.jet.data.model.api.registries.biome.Biome;
import net.hypejet.jet.data.model.api.registries.biome.effects.BiomeEffectSettings;
import net.hypejet.jet.data.model.api.registries.biome.effects.modifier.GrassColorModifier;
import net.hypejet.jet.data.model.api.registries.biome.effects.music.BiomeMusic;
import net.hypejet.jet.data.model.api.registries.biome.effects.particle.BiomeParticleSettings;
import net.hypejet.jet.data.model.api.registries.biome.effects.sound.BiomeAdditionalSound;
import net.hypejet.jet.data.model.api.registries.biome.effects.sound.BiomeMoodSound;
import net.hypejet.jet.data.model.api.registries.biome.effects.sound.BiomeSoundEvent;
import net.hypejet.jet.data.model.api.registries.biome.temperature.BiomeTemperatureModifier;
import net.hypejet.jet.data.model.api.registries.chat.ChatType;
import net.hypejet.jet.data.model.api.registries.chat.decoration.ChatDecoration;
import net.hypejet.jet.data.model.api.registries.chat.decoration.ChatDecorationParameter;
import net.hypejet.jet.data.model.api.registries.damage.DamageEffectType;
import net.hypejet.jet.data.model.api.registries.damage.DamageScalingType;
import net.hypejet.jet.data.model.api.registries.damage.DamageType;
import net.hypejet.jet.data.model.api.registries.damage.DeathMessageType;
import net.hypejet.jet.data.model.api.registries.dimension.DimensionType;
import net.hypejet.jet.data.model.api.registries.painting.PaintingVariant;
import net.hypejet.jet.data.model.api.registries.wolf.WolfBiomes;
import net.hypejet.jet.data.model.api.registries.wolf.WolfVariant;
import net.hypejet.jet.data.model.server.registry.registries.block.Block;
import net.hypejet.jet.data.model.server.registry.registries.block.state.BlockState;
import net.hypejet.jet.data.model.server.registry.registries.fluid.Fluid;
import net.hypejet.jet.data.model.server.registry.registries.item.Item;
import net.hypejet.jet.data.model.server.registry.registries.pack.FeaturePack;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a holder of a {@linkplain Gson gson} serializing {@linkplain DataRegistryEntry data registry entries}
 * and their values.
 *
 * @since 1.0
 * @author Codestech
 */
public final class JetDataJson {

    private JetDataJson() {}

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain Biome biomes}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createBiomesGson() {
        return createBuilder()
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
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(Biome.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain DimensionType dimension
     * types}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createDimensionTypesGson() {
        return createBuilder()
                .registerTypeAdapter(IntegerProvider.WeightedList.Entry.class, new WeightedListEntryJsonCodec())
                .registerTypeAdapter(IntegerProvider.class, new IntegerProviderJsonCodec())
                .registerTypeAdapter(DimensionType.class, new DimensionTypeJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(DimensionType.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain ChatType chat types}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createChatTypesGson() {
        return createBuilder()
                .registerTypeAdapter(ChatType.class, new ChatTypeJsonCodec())
                .registerTypeAdapter(ChatDecoration.class, new ChatDecorationJsonCodec())
                .registerTypeAdapter(ChatDecorationParameter.class, new ChatDecorationParameterJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(ChatType.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain DamageType damage
     * types}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createDamageTypesGson() {
        return createBuilder()
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
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(DamageType.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain WolfVariant wolf
     * variants}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createWolfVariantsGson() {
        return createBuilder()
                .registerTypeAdapter(WolfVariant.class, new WolfVariantJsonCodec())
                .registerTypeAdapter(WolfBiomes.class, new WolfBiomesJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(WolfVariant.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain PaintingVariant
     * painting variants}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createPaintingVariantsGson() {
        return createBuilder()
                .registerTypeAdapter(PaintingVariant.class, new PaintingVariantJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(PaintingVariant.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain ArmorTrimPattern armor
     * trim patterns}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createTrimPatternsGson() {
        return createBuilder()
                .registerTypeAdapter(ArmorTrimPattern.class, new ArmorTrimPatternJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(ArmorTrimPattern.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain ArmorTrimMaterial armor
     * trim materials}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createTrimMaterialsGson() {
        return createBuilder()
                .registerTypeAdapter(ArmorTrimMaterial.class, new ArmorTrimMaterialJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(ArmorTrimMaterial.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain BannerPattern banner
     * patterns}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createBannerPatternsGson() {
        return createBuilder()
                .registerTypeAdapter(BannerPattern.class, new BannerPatternJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(BannerPattern.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain Block blocks}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createBlocksGson() {
        return createBuilder()
                .registerTypeAdapter(Block.class, new BlockJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(Block.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain BlockState block
     * states}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createBlockStatesGson() {
        return createBuilder()
                .registerTypeAdapter(BlockState.class, new BlockStateJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(BlockState.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain Item items}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createItemsGson() {
        return createBuilder()
                .registerTypeAdapter(Item.class, new ItemJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(Item.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain GameEvent game events}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createGameEventsGson() {
        return createBuilder()
                .registerTypeAdapter(GameEvent.class, new GameEventJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(GameEvent.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain EntityType entity
     * types}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createEntityTypeGson() {
        return createBuilder()
                .registerTypeAdapter(EntityType.class, new EntityTypeJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(EntityType.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes {@linkplain Fluid fluids}.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createFluidsGson() {
        return createBuilder()
                .registerTypeAdapter(Fluid.class, new FluidJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(Fluid.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes Minecraft packet identifies.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createPacketGson() {
        return createBuilder()
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(Integer.class))
                .create();
    }

    /**
     * Creates {@linkplain Gson a gson} instance, which deserializes and serializes
     * {@linkplain BlockEntityType block entity types}.
     *
     * @return the gson
     * @since 1.0
     */
    public static @NonNull Gson createBlockEntityTypeGson() {
        return createBuilder()
                .registerTypeAdapter(BlockEntityType.class, new BlockEntityTypeJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(BlockEntityType.class))
                .create();
    }

    /**
     * Creates a {@linkplain Gson gson} instance, which deserializes and serializes common model objects.
     *
     * @return the gson instance
     * @since 1.0
     */
    public static @NonNull Gson createPlainGson() {
        return createBuilder().create();
    }

    private static @NonNull GsonBuilder createBuilder() {
        return GsonComponentSerializer.gson().populator()
                .apply(new GsonBuilder())
                .setPrettyPrinting()
                .registerTypeAdapter(Color.class, new ColorJsonCodec())
                .registerTypeAdapter(Key.class, new KeyJsonCodec())
                .registerTypeAdapter(BinaryTag.class, new BinaryTagJsonCodec())
                .registerTypeAdapter(PackInfo.class, new PackInfoCodec())
                .registerTypeAdapter(FeaturePack.class, new FeaturePackJsonCodec())
                .registerTypeAdapter(DataRegistryEntry.class, new RegistryEntryDataJsonCodec<>(FeaturePack.class));
    }
}