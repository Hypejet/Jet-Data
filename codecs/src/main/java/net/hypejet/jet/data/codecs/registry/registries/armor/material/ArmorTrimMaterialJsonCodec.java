package net.hypejet.jet.data.codecs.registry.registries.armor.material;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.hypejet.jet.data.codecs.JsonCodec;
import net.hypejet.jet.data.codecs.util.JsonUtil;
import net.hypejet.jet.data.codecs.util.mapper.Mapper;
import net.hypejet.jet.data.model.registry.registries.armor.material.ArmorTrimMaterial;
import net.hypejet.jet.data.model.registry.registries.armor.material.ArmorTrimMaterial.ArmorMaterialType;
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

    private static final Mapper<ArmorMaterialType, String> MAPPER = Mapper
            .builder(ArmorMaterialType.class, String.class)
            .register(ArmorMaterialType.LEATHER, "leather")
            .register(ArmorMaterialType.CHAINMAIL, "chainmail")
            .register(ArmorMaterialType.IRON, "iron")
            .register(ArmorMaterialType.GOLD, "gold")
            .register(ArmorMaterialType.DIAMOND, "diamond")
            .register(ArmorMaterialType.TURTLE, "turtle")
            .register(ArmorMaterialType.NETHERITE, "netherite")
            .build();

    @Override
    public ArmorTrimMaterial deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonObject object))
            throw new IllegalArgumentException("The json element specified must be a json object");

        JsonObject overrideArmorMaterialsJson = object.getAsJsonObject(OVERRIDE_ARMOR_MATERIALS);
        Map<ArmorMaterialType, Key> overrideArmorMaterials = null;

        if (overrideArmorMaterialsJson != null) {
            overrideArmorMaterials = new HashMap<>();

            for (Map.Entry<String, JsonElement> entry : overrideArmorMaterialsJson.entrySet()) {
                String key = entry.getKey();
                ArmorMaterialType armorMaterialType = MAPPER.read(key);

                if (armorMaterialType == null)
                    throw new IllegalArgumentException(String.format("Unknown armor material type: %s", key));

                overrideArmorMaterials.put(armorMaterialType, context.deserialize(entry.getValue(), Key.class));
            }
        }

        return new ArmorTrimMaterial(JsonUtil.read(ASSET, Key.class, object, context),
                JsonUtil.read(INGREDIENT, Key.class, object, context),
                JsonUtil.read(ITEM_MODEL_INDEX, float.class, object, context),
                overrideArmorMaterials, JsonUtil.read(DESCRIPTION, Component.class, object, context));
    }

    @Override
    public JsonElement serialize(ArmorTrimMaterial src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        JsonUtil.write(ASSET, src.asset(), Key.class, object, context);
        JsonUtil.write(INGREDIENT, src.ingredient(), Key.class, object, context);
        object.addProperty(ITEM_MODEL_INDEX, src.itemModelIndex());

        Map<ArmorMaterialType, Key> overrideArmorMaterials = src.overrideArmorMaterials();
        if (overrideArmorMaterials != null) {
            JsonObject overrideArmorMaterialsJson = new JsonObject();

            for (Map.Entry<ArmorMaterialType, Key> entry : overrideArmorMaterials.entrySet()) {
                ArmorMaterialType type = entry.getKey();
                String serializedType = MAPPER.write(type);

                if (serializedType == null) {
                    throw new IllegalArgumentException(String.format(
                            "Could not serialize armor material of: %s", type
                    ));
                }

                overrideArmorMaterialsJson.add(serializedType, context.serialize(entry.getValue()));
            }

            object.add(OVERRIDE_ARMOR_MATERIALS, overrideArmorMaterialsJson);
        }

        return object;
    }
}