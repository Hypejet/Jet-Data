package net.hypejet.jet.data.generator.adapter;

import net.hypejet.jet.data.generator.util.ReflectionUtil;
import net.hypejet.jet.data.model.component.JetDataComponentValue;
import net.hypejet.jet.data.model.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.DataComponentValue;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.network.chat.HoverEvent.EntityTooltipInfo;
import net.minecraft.network.chat.HoverEvent.ItemStackInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a utility that converts a {@linkplain net.minecraft.network.chat.Style Minecraft component style}
 * into a {@linkplain Style adventure style}.
 *
 * @since 1.0
 * @author Codestech
 * @see Style
 * @see net.minecraft.network.chat.Style
 */
public final class StyleAdapter {

    private static final Field STYLE_BOLD_FIELD;
    private static final Field STYLE_ITALIC_FIELD;
    private static final Field STYLE_UNDERLINED_FIELD;
    private static final Field STYLE_STRIKETHROUGH_FIELD;
    private static final Field STYLE_OBFUSCATED_FIELD;

    static {
        Class<?> styleClass = net.minecraft.network.chat.Style.class;
        try {
            STYLE_BOLD_FIELD = styleClass.getDeclaredField("bold");
            STYLE_ITALIC_FIELD = styleClass.getDeclaredField("italic");
            STYLE_UNDERLINED_FIELD = styleClass.getDeclaredField("underlined");
            STYLE_STRIKETHROUGH_FIELD = styleClass.getDeclaredField("strikethrough");
            STYLE_OBFUSCATED_FIELD = styleClass.getDeclaredField("obfuscated");
        } catch (NoSuchFieldException exception) {
            throw new RuntimeException(exception);
        }
    }

    private StyleAdapter() {}

    /**
     * Converts a {@linkplain net.minecraft.network.chat.Style style}.
     *
     * @param style the style to convert
     * @param registryAccess access to all Minecraft registries
     * @return the converted style
     * @since 1.0
     */
    public static @NonNull Style convert(net.minecraft.network.chat.@NonNull Style style,
                                         @NonNull RegistryAccess registryAccess) {
        return Style.style()
                .font(IdentifierAdapter.convert(style.getFont()))
                .color(NullabilityUtil.applyIfNotNull(style.getColor(), StyleAdapter::convert))
                .decoration(TextDecoration.BOLD, state(style, STYLE_BOLD_FIELD))
                .decoration(TextDecoration.ITALIC, state(style, STYLE_ITALIC_FIELD))
                .decoration(TextDecoration.UNDERLINED, state(style, STYLE_UNDERLINED_FIELD))
                .decoration(TextDecoration.STRIKETHROUGH, state(style, STYLE_STRIKETHROUGH_FIELD))
                .decoration(TextDecoration.OBFUSCATED, state(style, STYLE_OBFUSCATED_FIELD))
                .clickEvent(NullabilityUtil.applyIfNotNull(style.getClickEvent(), StyleAdapter::convert))
                .hoverEvent(NullabilityUtil.applyIfNotNull(style.getHoverEvent(),
                        hoverEvent -> convert(hoverEvent, registryAccess)))
                .insertion(style.getInsertion())
                .build();
    }

    private static @NonNull TextColor convert(net.minecraft.network.chat.@NonNull TextColor textColor) {
        return TextColor.color(textColor.getValue());
    }

    private static @NonNull ClickEvent convert(net.minecraft.network.chat.@NonNull ClickEvent clickEvent) {
        ClickEvent.Action action = switch (clickEvent.getAction()) {
            case OPEN_URL -> ClickEvent.Action.OPEN_URL;
            case OPEN_FILE -> ClickEvent.Action.OPEN_FILE;
            case RUN_COMMAND -> ClickEvent.Action.RUN_COMMAND;
            case SUGGEST_COMMAND -> ClickEvent.Action.SUGGEST_COMMAND;
            case CHANGE_PAGE -> ClickEvent.Action.CHANGE_PAGE;
            case COPY_TO_CLIPBOARD -> ClickEvent.Action.COPY_TO_CLIPBOARD;
        };
        return ClickEvent.clickEvent(action, clickEvent.getValue());
    }

    private static @NonNull HoverEvent<?> convert(net.minecraft.network.chat.@NonNull HoverEvent hoverEvent,
                                                  @NonNull RegistryAccess registryAccess) {
        Action<?> action = hoverEvent.getAction();
        if (action == Action.SHOW_TEXT) {
            return HoverEvent.showText(ComponentAdapter.convert(
                    NullabilityUtil.requireNonNull(hoverEvent.getValue(Action.SHOW_TEXT),
                            "value of a hover event"),
                    registryAccess)
            );
        } else if (action == Action.SHOW_ENTITY) {
            EntityTooltipInfo tooltipInfo = NullabilityUtil.requireNonNull(hoverEvent.getValue(Action.SHOW_ENTITY),
                    "value of hover event");

            ResourceLocation location = Objects.requireNonNull(
                    registryAccess.registryOrThrow(Registries.ENTITY_TYPE).getKey(tooltipInfo.type),
                    "An identifier of a entity type specified could not be found in a registry"
            );

            return HoverEvent.showEntity(IdentifierAdapter.convert(location), tooltipInfo.id, tooltipInfo.name
                    .map(component -> ComponentAdapter.convert(component, registryAccess))
                    .orElse(null));
        } else if (action == Action.SHOW_ITEM) {
            Registry<DataComponentType<?>> registry = registryAccess.registryOrThrow(Registries.DATA_COMPONENT_TYPE);
            ItemStackInfo info = NullabilityUtil.requireNonNull(hoverEvent.getValue(Action.SHOW_ITEM),
                    "value of hover event");

            ItemStack itemStack = info.getItemStack();
            Map<Key, DataComponentValue> components = new HashMap<>();

            for (TypedDataComponent<?> component : itemStack.getComponents()) {
                ResourceLocation location = Objects.requireNonNull(registry.getKey(component.type()),
                        "An identifier of a data component type specified could not be found in a registry");

                Tag serializedTag = component.encodeValue(NbtOps.INSTANCE).getOrThrow();
                BinaryTag convertedTag = BinaryTagAdapter.convert(serializedTag);

                components.put(IdentifierAdapter.convert(location), new JetDataComponentValue(convertedTag));
            }

            return HoverEvent.showItem(
                    itemStack.getItemHolder()
                            .unwrapKey()
                            .map(ResourceKey::location)
                            .map(IdentifierAdapter::convert)
                            .orElseThrow(),
                    itemStack.getCount(),
                    Map.copyOf(components)
            );
        } else {
            throw new IllegalArgumentException(String.format("Unknown hover event action of: %s",
                    action.getSerializedName()));
        }
    }

    private static TextDecoration.@NonNull State state(net.minecraft.network.chat.@NonNull Style style,
                                                       @NonNull Field field) {
        Boolean bool = (Boolean) ReflectionUtil.access(field, style, Field::get);
        return TextDecoration.State.byBoolean(bool);
    }
}