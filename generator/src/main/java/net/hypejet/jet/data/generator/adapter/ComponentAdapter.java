package net.hypejet.jet.data.generator.adapter;

import net.hypejet.jet.data.generator.util.ReflectionUtil;
import net.hypejet.jet.data.model.utils.NullabilityUtil;
import net.kyori.adventure.text.BlockNBTComponent.LocalPos;
import net.kyori.adventure.text.BlockNBTComponent.Pos;
import net.kyori.adventure.text.BlockNBTComponent.WorldPos;
import net.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.LocalCoordinates;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.commands.arguments.selector.SelectorPattern;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.BlockDataSource;
import net.minecraft.network.chat.contents.DataSource;
import net.minecraft.network.chat.contents.EntityDataSource;
import net.minecraft.network.chat.contents.KeybindContents;
import net.minecraft.network.chat.contents.NbtContents;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.contents.ScoreContents;
import net.minecraft.network.chat.contents.SelectorContents;
import net.minecraft.network.chat.contents.StorageDataSource;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Represents a utility that converts {@linkplain net.minecraft.network.chat.Component Minecraft components}
 * into {@linkplain Component adventure components}.
 *
 * @since 1.0
 * @author Codestech
 * @see Component
 * @see net.minecraft.network.chat.Component
 */
public final class ComponentAdapter {

    private static final Field LOCAL_COORDINATES_LEFT_FIELD;
    private static final Field LOCAL_COORDINATES_UP_FIELD;
    private static final Field LOCAL_COORDINATES_FORWARDS_FIELD;

    private static final Field WORLD_COORDINATES_X_FIELD;
    private static final Field WORLD_COORDINATES_Y_FIELD;
    private static final Field WORLD_COORDINATES_Z_FIELD;

    private static final Field WORLD_COORDINATE_VALUE_FIELD;

    static {
        Class<?> localCoordinatesClass = LocalCoordinates.class;
        Class<?> worldCoordinatesClass = WorldCoordinates.class;

        try {
            LOCAL_COORDINATES_LEFT_FIELD = localCoordinatesClass.getDeclaredField("left");
            LOCAL_COORDINATES_UP_FIELD = localCoordinatesClass.getDeclaredField("up");
            LOCAL_COORDINATES_FORWARDS_FIELD = localCoordinatesClass.getDeclaredField("forwards");

            WORLD_COORDINATES_X_FIELD = worldCoordinatesClass.getDeclaredField("x");
            WORLD_COORDINATES_Y_FIELD = worldCoordinatesClass.getDeclaredField("y");
            WORLD_COORDINATES_Z_FIELD = worldCoordinatesClass.getDeclaredField("z");

            WORLD_COORDINATE_VALUE_FIELD = WorldCoordinate.class.getDeclaredField("value");
        } catch (NoSuchFieldException exception) {
            throw new RuntimeException(exception);
        }
    }

    private ComponentAdapter() {}

    /**
     * Converts the {@linkplain net.minecraft.network.chat.Component component}.
     *
     * @param component the component to convert
     * @param registryAccess access to all Minecraft registries
     * @return the converted component
     * @since 1.0
     */
    public static @NonNull Component convert(net.minecraft.network.chat.@NonNull Component component,
                                             @NonNull RegistryAccess registryAccess) {
        ComponentContents contents = component.getContents();
        Style style = StyleAdapter.convert(component.getStyle(), registryAccess);

        return switch (contents) {
            case PlainTextContents text -> Component.text(text.text(), style);
            case TranslatableContents translatable -> Component.translatable(translatable.getKey(), style);
            case KeybindContents keybind -> Component.keybind(keybind.getName(), style);
            case ScoreContents score -> Component.score()
                    .name(score.name().map(SelectorPattern::pattern, string -> string))
                    .objective(score.objective())
                    .style(style)
                    .build();
            case SelectorContents selector -> Component.selector()
                    .pattern(selector.selector().pattern())
                    .separator(selector.separator()
                            .map(separatorComponent -> ComponentAdapter.convert(separatorComponent, registryAccess))
                            .orElse(null))
                    .style(style)
                    .build();
            case NbtContents nbt -> {
                boolean interpret = nbt.isInterpreting();
                Component separator = nbt.getSeparator().
                        map(separatorComponent -> ComponentAdapter.convert(separatorComponent, registryAccess))
                        .orElse(null);
                String path = nbt.getNbtPath();
                DataSource dataSource = nbt.getDataSource();

                yield switch (nbt.getDataSource()) {
                    case EntityDataSource entity -> Component.entityNBT()
                            .interpret(interpret)
                            .separator(separator)
                            .nbtPath(path)
                            .selector(entity.selectorPattern())
                            .style(style)
                            .build();
                    case BlockDataSource block -> {
                        Pos pos = NullabilityUtil.applyIfNotNull(block.compiledPos(), ComponentAdapter::convert);
                        yield Component.blockNBT()
                                .interpret(interpret)
                                .separator(separator)
                                .nbtPath(path)
                                .pos(Objects.requireNonNull(pos, "The compiled pos of the block data source" +
                                        " specified must not be null"))
                                .style(style)
                                .build();
                    }
                    case StorageDataSource storage -> Component.storageNBT()
                            .interpret(interpret)
                            .separator(separator)
                            .nbtPath(path)
                            .storage(IdentifierAdapter.convert(storage.id()))
                            .style(style)
                            .build();
                    default -> throw new IllegalArgumentException(String.format(
                            "Unknown data source with class of: %s", dataSource.getClass().getSimpleName()
                    ));
                };
            }
            default -> throw new IllegalArgumentException(String.format("Unknown type of component contents: %s",
                    contents));
        };
    }

    private static @NonNull Pos convert(@NonNull Coordinates coordinates) {
        return switch (coordinates) {
            case LocalCoordinates local -> {
                double left = ReflectionUtil.access(LOCAL_COORDINATES_LEFT_FIELD, local, Field::getDouble);
                double up = ReflectionUtil.access(LOCAL_COORDINATES_UP_FIELD, local, Field::getDouble);
                double forwards = ReflectionUtil.access(LOCAL_COORDINATES_FORWARDS_FIELD, local, Field::getDouble);
                yield LocalPos.localPos(left, up, forwards);
            }
            case WorldCoordinates world -> {
                WorldCoordinate x = (WorldCoordinate) ReflectionUtil.access(WORLD_COORDINATES_X_FIELD, world);
                WorldCoordinate y = (WorldCoordinate) ReflectionUtil.access(WORLD_COORDINATES_Y_FIELD, world);
                WorldCoordinate z = (WorldCoordinate) ReflectionUtil.access(WORLD_COORDINATES_Z_FIELD, world);
                yield WorldPos.worldPos(convert(x), convert(y), convert(z));
            }
            default -> throw new IllegalArgumentException(String.format("Unknown coordinates of: %s", coordinates));
        };
    }

    private static @NonNull Coordinate convert(@NonNull WorldCoordinate coordinate) {
        double value = ReflectionUtil.access(WORLD_COORDINATE_VALUE_FIELD, coordinate, Field::getDouble);
        Coordinate.Type type = coordinate.isRelative() ? Coordinate.Type.RELATIVE : Coordinate.Type.ABSOLUTE;
        return Coordinate.coordinate((int) Math.floor(value), type);
    }
}