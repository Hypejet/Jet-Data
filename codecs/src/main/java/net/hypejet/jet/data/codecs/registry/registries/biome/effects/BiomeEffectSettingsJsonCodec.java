package net.hypejet.jet.data.codecs.registry.registries.biome.effects;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.color.Color;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.BiomeEffectSettings;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.modifier.GrassColorModifier;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.music.BiomeMusic;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.particle.BiomeParticleSettings;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.sound.BiomeAdditionalSound;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.sound.BiomeMoodSound;
import net.hypejet.jet.data.model.api.registry.registries.biome.effects.sound.BiomeSoundEvent;

import java.lang.reflect.Type;

/**
 * Represents a {@linkplain JsonCodec json codec}, which reads and writes a {@linkplain BiomeEffectSettings biome
 * effect settings}.
 *
 * @since 1.0
 * @author Codestech
 * @see BiomeEffectSettings
 * @see JsonCodec
 */
public final class BiomeEffectSettingsJsonCodec implements JsonCodec<BiomeEffectSettings> {

    private static final String FOG_COLOR = "fog-color";
    private static final String WATER_COLOR = "water-color";
    private static final String WATER_FOG_COLOR = "water-fog-color";
    private static final String SKY_COLOR = "sky-color";

    private static final String FOLIAGE_COLOR = "foliage-color";
    private static final String GRASS_COLOR = "grass-color";

    private static final String GRASS_COLOR_MODIFIER = "grass-color-modifier";
    private static final String PARTICLE_SETTINGS = "particle-settings";

    private static final String AMBIENT_SOUND = "ambient-sound";
    private static final String MOOD_SOUND = "mood-sound";
    private static final String ADDITIONAL_SOUND = "additional-sound";

    private static final String MUSIC = "music";

    @Override
    public BiomeEffectSettings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element is not a json objet");
        return new BiomeEffectSettings(JsonUtil.read(FOG_COLOR, Color.class, object, context),
                JsonUtil.read(WATER_COLOR, Color.class, object, context),
                JsonUtil.read(WATER_FOG_COLOR, Color.class, object, context),
                JsonUtil.read(SKY_COLOR, Color.class, object, context),
                JsonUtil.readOptional(FOLIAGE_COLOR, Color.class, object, context),
                JsonUtil.readOptional(GRASS_COLOR, Color.class, object, context),
                JsonUtil.readOptional(GRASS_COLOR_MODIFIER, GrassColorModifier.class, object, context),
                JsonUtil.readOptional(PARTICLE_SETTINGS, BiomeParticleSettings.class, object, context),
                JsonUtil.readOptional(AMBIENT_SOUND, BiomeSoundEvent.class, object, context),
                JsonUtil.readOptional(MOOD_SOUND, BiomeMoodSound.class, object, context),
                JsonUtil.readOptional(ADDITIONAL_SOUND, BiomeAdditionalSound.class, object, context),
                JsonUtil.readOptional(MUSIC, BiomeMusic.class, object, context));
    }

    @Override
    public JsonElement serialize(BiomeEffectSettings src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        JsonUtil.write(FOG_COLOR, src.fogColor(), object, context);
        JsonUtil.write(WATER_COLOR, src.waterColor(), object, context);
        JsonUtil.write(WATER_FOG_COLOR, src.waterFogColor(), object, context);
        JsonUtil.write(SKY_COLOR, src.skyColor(), object, context);

        JsonUtil.writeOptional(FOLIAGE_COLOR, src.foliageColorOverride(), object, context);
        JsonUtil.writeOptional(GRASS_COLOR, src.grassColorOverride(), object, context);

        JsonUtil.writeOptional(GRASS_COLOR_MODIFIER, src.grassColorModifier(), object, context);
        JsonUtil.writeOptional(PARTICLE_SETTINGS, src.particleSettings(), object, context);

        JsonUtil.writeOptional(AMBIENT_SOUND, src.ambientSound(), object, context);
        JsonUtil.writeOptional(MOOD_SOUND, src.moodSound(), object, context);
        JsonUtil.writeOptional(ADDITIONAL_SOUND, src.additionalSound(), object, context);

        JsonUtil.writeOptional(MUSIC, src.music(), object, context);
        return object;
    }
}