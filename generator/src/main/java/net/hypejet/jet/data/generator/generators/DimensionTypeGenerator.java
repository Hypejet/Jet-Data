package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.ReflectionUtil;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.number.IntegerProvider;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.registry.registries.dimension.DimensionType;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

/**
 * Represents a {@linkplain Generator generator} of {@linkplain DimensionType dimension types}.
 *
 * @since 1.0
 * @author Codestech
 * @see DimensionType
 * @see Generator
 */
public final class DimensionTypeGenerator extends Generator<DimensionType> {

    private static final Field CLAMPED_SOURCE_FIELD;

    private static final Field CLAMPED_NORMAL_MEAN_FIELD;
    private static final Field CLAMPED_NORMAL_DEVIATION_FIELD;

    private static final Field WEIGHTED_LIST_DISTRIBUTION_FIELD;

    private final RegistryAccess registryAccess;

    static {
        try {
            CLAMPED_SOURCE_FIELD = ClampedInt.class.getDeclaredField("source");
            CLAMPED_NORMAL_MEAN_FIELD = ClampedNormalInt.class.getDeclaredField("mean");
            CLAMPED_NORMAL_DEVIATION_FIELD = ClampedNormalInt.class.getDeclaredField("deviation");
            WEIGHTED_LIST_DISTRIBUTION_FIELD = WeightedListInt.class.getDeclaredField("distribution");
        } catch (NoSuchFieldException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Constructs the {@linkplain DimensionTypeGenerator dimension-type generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public DimensionTypeGenerator(@NonNull RegistryAccess registryAccess) {
        super(new GeneratorName("Dimension", "Type", "Generator"),
                new ResourceFileSettings("dimension-types", JetDataJson.createDimensionTypesGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.API, "DimensionTypes"));
        this.registryAccess = NullabilityUtil.requireNonNull(registryAccess, "registry access");
    }

    @Override
    public @NonNull List<DataRegistryEntry<DimensionType>> generate() {
        Registry<net.minecraft.world.level.dimension.DimensionType> registry = this.registryAccess
                .lookupOrThrow(Registries.DIMENSION_TYPE);
        return RegistryUtil.createEntries(registry, dimensionType -> {
            OptionalLong optionalFixedTime = dimensionType.fixedTime();
            Long fixedTime = optionalFixedTime.isPresent() ? optionalFixedTime.getAsLong() : null;
            return DimensionType.builder()
                    .fixedTime(fixedTime)
                    .hasSkylight(dimensionType.hasSkyLight())
                    .hasCeiling(dimensionType.hasCeiling())
                    .ultrawarm(dimensionType.ultraWarm())
                    .natural(dimensionType.natural())
                    .coordinateScale(dimensionType.coordinateScale())
                    .bedWorks(dimensionType.bedWorks())
                    .respawnAnchorWorks(dimensionType.respawnAnchorWorks())
                    .minY(dimensionType.minY())
                    .height(dimensionType.height())
                    .localHeight(dimensionType.logicalHeight())
                    .infiniburn(IdentifierAdapter.convert(dimensionType.infiniburn().location()))
                    .effects(IdentifierAdapter.convert(dimensionType.effectsLocation()))
                    .ambientLight(dimensionType.ambientLight())
                    .piglinSafe(dimensionType.piglinSafe())
                    .hasRaids(dimensionType.hasRaids())
                    .monsterSpawnLightLevel(integerProvider(dimensionType.monsterSpawnLightTest()))
                    .monsterSpawnBlockLightLimit(dimensionType.monsterSpawnBlockLightLimit())
                    .build();
        });
    }

    private static @NonNull IntegerProvider integerProvider(@NonNull IntProvider provider) {
        return switch (provider) {
            case ConstantInt constant -> new IntegerProvider.ConstantInteger(constant.getValue());
            case UniformInt uniform -> new IntegerProvider.Uniform(uniform.getMinValue(), uniform.getMaxValue());
            case BiasedToBottomInt biasedToBottom -> new IntegerProvider.BiasedToBottom(biasedToBottom.getMinValue(),
                    biasedToBottom.getMaxValue());
            case ClampedInt clamped -> new IntegerProvider.Clamped(clamped.getMinValue(), clamped.getMaxValue(),
                    integerProvider(source(clamped)));
            case ClampedNormalInt clampedNormal -> new IntegerProvider.ClampedNormal(mean(clampedNormal),
                    deviation(clampedNormal), clampedNormal.getMinValue(), clampedNormal.getMaxValue());
            case WeightedListInt weightedList -> {
                List<IntegerProvider.WeightedList.Entry> entries = new ArrayList<>();
                for (WeightedEntry.Wrapper<?> wrapper : distribution(weightedList).unwrap()) {
                    int weight = wrapper.weight().asInt();
                    IntegerProvider data = integerProvider((IntProvider) wrapper.data());
                    entries.add(new IntegerProvider.WeightedList.Entry(data, weight));
                }
                yield new IntegerProvider.WeightedList(entries);
            }
            default -> throw new IllegalStateException(String.format("Unknown integer provider: %s", provider));
        };
    }

    private static @NonNull IntProvider source(@NonNull ClampedInt clamped) {
        return (IntProvider) ReflectionUtil.access(CLAMPED_SOURCE_FIELD, clamped, Field::get);
    }

    private static float mean(@NonNull ClampedNormalInt clampedNormal) {
        return ReflectionUtil.access(CLAMPED_NORMAL_MEAN_FIELD, clampedNormal, Field::getFloat);
    }

    private static float deviation(@NonNull ClampedNormalInt clampedNormal) {
        return ReflectionUtil.access(CLAMPED_NORMAL_DEVIATION_FIELD, clampedNormal, Field::getFloat);
    }

    private static @NonNull SimpleWeightedRandomList<?> distribution(@NonNull WeightedListInt weightedList) {
        return (SimpleWeightedRandomList<?>) ReflectionUtil.access(WEIGHTED_LIST_DISTRIBUTION_FIELD,
                weightedList, Field::get);
    }
}