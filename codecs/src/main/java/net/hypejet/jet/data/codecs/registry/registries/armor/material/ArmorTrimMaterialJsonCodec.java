package net.hypejet.jet.data.codecs.registry.registries.armor.material;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.model.api.registry.registries.armor.material.ArmorTrimMaterial;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a {@linkplain JsonCodec json codec} which deserializes and serializes a {@linkplain ArmorTrimMaterial
 * armor trim material}.
 *
 * @since 1.0
 * @author Codestech
 * @see ArmorTrimMaterial
 * @see JsonCodec
 */
public final class ArmorTrimMaterialJsonCodec implements JsonCodec<ArmorTrimMaterial> {

    private static final String ASSET = "asset";
    private static final String INGREDIENT = "ingredient";
    private static final String ITEM_MODEL_INDEX = "item-model-index";
    private static final String OVERRIDE_ARMOR_MATERIALS = "override-armor-materials";
    private static final String DESCRIPTION = "description";

    @Override
    public ArmorTrimMaterial deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        NullabilityUtil.requireNonNull(json, "json");
        NullabilityUtil.requireNonNull(typeOfT, "type");
        NullabilityUtil.requireNonNull(context, "context");

        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");

        JsonObject overrideArmorMaterialsJson = object.getAsJsonObject(OVERRIDE_ARMOR_MATERIALS);
        Map<Key, Key> overrideArmorMaterials = null;

        if (overrideArmorMaterialsJson != null) {
            overrideArmorMaterials = new HashMap<>();

            for (Map.Entry<String, JsonElement> entry : overrideArmorMaterialsJson.entrySet()) {
                Key armorMaterialKey = Key.key(entry.getKey());
                Key assetKey = context.deserialize(entry.getValue(), Key.class);
                overrideArmorMaterials.put(armorMaterialKey, assetKey);
            }
        }

        return new ArmorTrimMaterial(JsonUtil.read(ASSET, Key.class, object, context),
                JsonUtil.read(INGREDIENT, Key.class, object, context),
                JsonUtil.read(ITEM_MODEL_INDEX, float.class, object, context),
                overrideArmorMaterials, JsonUtil.read(DESCRIPTION, Component.class, object, context));
    }

    @Override
    public JsonElement serialize(ArmorTrimMaterial src, Type typeOfSrc, JsonSerializationContext context) {
        NullabilityUtil.requireNonNull(src, "source");
        NullabilityUtil.requireNonNull(typeOfSrc, "type of source");
        NullabilityUtil.requireNonNull(context, "context");

        JsonObject object = new JsonObject();

        JsonUtil.write(ASSET, src.asset(), Key.class, object, context);
        JsonUtil.write(INGREDIENT, src.ingredient(), Key.class, object, context);
        object.addProperty(ITEM_MODEL_INDEX, src.itemModelIndex());

        Map<Key, Key> overrideArmorMaterials = src.overrideArmorMaterials();
        if (overrideArmorMaterials != null) {
            JsonObject overrideArmorMaterialsJson = new JsonObject();

            for (Map.Entry<Key, Key> entry : overrideArmorMaterials.entrySet()) {
                String key = entry.getKey().asString();
                JsonElement value = context.serialize(entry.getValue(), Key.class);
                overrideArmorMaterialsJson.add(key, value);
            }

            object.add(OVERRIDE_ARMOR_MATERIALS, overrideArmorMaterialsJson);
        }

        JsonUtil.write(DESCRIPTION, src.description(), Component.class, object, context);
        return object;
    }
}