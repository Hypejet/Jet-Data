package net.hypejet.jet.data.codecs.registry.registries.dimension;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.number.IntegerProvider;
import net.hypejet.jet.data.model.api.registries.dimension.DimensionType;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which deserializes and serializes a {@linkplain DimensionType
 * dimension type}.
 *
 * @since 1.0
 * @author Codestech
 * @see DimensionType
 * @see JsonCodec
 */
public final class DimensionTypeJsonCodec implements JsonCodec<DimensionType> {

    private static final String FIXED_TIME = "fixed-time";

    private static final String HAS_SKYLIGHT = "has-skylight";
    private static final String HAS_CEILING = "has-ceiling";
    private static final String ULTRAWARM = "ultrawarm";
    private static final String NATURAL = "natural";

    private static final String COORDINATE_SCALE = "coordinate-scale";

    private static final String BED_WORKS = "bed-works";
    private static final String RESPAWN_ANCHOR_WORKS = "respawn-anchor-works";

    private static final String MIN_Y = "min-y";
    private static final String HEIGHT = "height";
    private static final String LOCAL_HEIGHT = "local-height";

    private static final String INFINIBURN = "infiniburn";
    private static final String EFFECTS = "effects";

    private static final String AMBIENT_LIGHT = "ambient-light";

    private static final String PIGLIN_SAFE = "piglin-safe";
    private static final String HAS_RAIDS = "has-raids";

    private static final String MONSTER_SPAWN_LIGHT_LEVEL = "monster-spawn-light-level";
    private static final String MONSTER_SPAWN_BLOCK_LIGHT_LEVEL = "monster-spawn-block-light-level";

    @Override
    public DimensionType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified is not a json object");
        return DimensionType.builder()
                .fixedTime(JsonUtil.readOptional(FIXED_TIME, Long.class, object, context))
                .hasSkylight(JsonUtil.read(HAS_SKYLIGHT, boolean.class, object, context))
                .hasCeiling(JsonUtil.read(HAS_CEILING, boolean.class, object, context))
                .ultrawarm(JsonUtil.read(ULTRAWARM, boolean.class, object, context))
                .natural(JsonUtil.read(NATURAL, boolean.class, object, context))
                .coordinateScale(JsonUtil.read(COORDINATE_SCALE, double.class, object, context))
                .bedWorks(JsonUtil.read(BED_WORKS, boolean.class, object, context))
                .respawnAnchorWorks(JsonUtil.read(RESPAWN_ANCHOR_WORKS, boolean.class, object, context))
                .minY(JsonUtil.read(MIN_Y, int.class, object, context))
                .height(JsonUtil.read(HEIGHT, int.class, object, context))
                .localHeight(JsonUtil.read(LOCAL_HEIGHT, int.class, object, context))
                .infiniburn(JsonUtil.read(INFINIBURN, Key.class, object, context))
                .effects(JsonUtil.read(EFFECTS, Key.class, object, context))
                .ambientLight(JsonUtil.read(AMBIENT_LIGHT, float.class, object, context))
                .piglinSafe(JsonUtil.read(PIGLIN_SAFE, boolean.class, object, context))
                .hasRaids(JsonUtil.read(HAS_RAIDS, boolean.class, object, context))
                .monsterSpawnLightLevel(JsonUtil.read(MONSTER_SPAWN_LIGHT_LEVEL, IntegerProvider.class, object,
                        context))
                .monsterSpawnBlockLightLimit(JsonUtil.read(MONSTER_SPAWN_BLOCK_LIGHT_LEVEL, int.class, object,
                        context))
                .build();
    }

    @Override
    public JsonElement serialize(DimensionType src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");

        JsonObject object = new JsonObject();
        JsonUtil.writeOptional(FIXED_TIME, src.fixedTime(), object, context);
        object.addProperty(HAS_SKYLIGHT, src.hasSkylight());
        object.addProperty(HAS_CEILING, src.hasCeiling());
        object.addProperty(ULTRAWARM, src.ultrawarm());
        object.addProperty(NATURAL, src.natural());
        object.addProperty(COORDINATE_SCALE, src.coordinateScale());
        object.addProperty(BED_WORKS, src.bedWorks());
        object.addProperty(RESPAWN_ANCHOR_WORKS, src.hasSkylight());
        object.addProperty(MIN_Y, src.minY());
        object.addProperty(HEIGHT, src.height());
        object.addProperty(LOCAL_HEIGHT, src.localHeight());
        JsonUtil.write(INFINIBURN, src.infiniburn(), Key.class, object, context);
        JsonUtil.write(EFFECTS, src.effects(), Key.class, object, context);
        object.addProperty(AMBIENT_LIGHT, src.ambientLight());
        object.addProperty(PIGLIN_SAFE, src.piglinSafe());
        object.addProperty(HAS_RAIDS, src.hasRaids());
        JsonUtil.write(MONSTER_SPAWN_LIGHT_LEVEL, src.monsterSpawnLightLevel(), IntegerProvider.class, object,
                context);
        JsonUtil.write(MONSTER_SPAWN_BLOCK_LIGHT_LEVEL, src.monsterSpawnBlockLightLimit(), object, context);

        return object;
    }
}