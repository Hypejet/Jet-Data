package net.hypejet.jet.data.codecs.item.component;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.model.component.JetDataComponentValue;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.event.DataComponentValueConverterRegistry.Conversion;
import net.kyori.adventure.text.event.DataComponentValueConverterRegistry.Provider;
import net.kyori.adventure.text.serializer.gson.GsonDataComponentValue;
import net.kyori.adventure.text.serializer.nbt.NBTDataComponentValue;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Represents a {@linkplain Provider data component value conversion provider}, which provides conversions
 * between {@linkplain JetDataComponentValue Jet data component value} and other data component values/
 *
 * @since 1.0
 * @author Codestech
 * @see JetDataComponentValue
 */
public final class JetDataComponentConversionProvider implements Provider {

    private static final Key IDENTIFIER = Key.key("hypejet", "jet/data/codecs/conversion-provider");

    private static final Iterable<Conversion<?, ?>> CONVERSIONS = Set.of(
            Conversion.convert(
                    JetDataComponentValue.class,
                    NBTDataComponentValue.class,
                    (key, value) -> NBTDataComponentValue.nbtDataComponentValue(value.binaryTag())
            ),
            Conversion.convert(
                    NBTDataComponentValue.class,
                    JetDataComponentValue.class,
                    (key, value) -> new JetDataComponentValue(value.binaryTag())
            ),
            Conversion.convert(
                    JetDataComponentValue.class,
                    GsonDataComponentValue.class,
                    (key, value) -> GsonDataComponentValue.gsonDataComponentValue(JetDataJson.gson()
                            .toJsonTree(value.binaryTag(), BinaryTag.class))
            ),
            Conversion.convert(
                    GsonDataComponentValue.class,
                    JetDataComponentValue.class,
                    (key, value) -> new JetDataComponentValue(JetDataJson.gson()
                            .fromJson(value.element(), BinaryTag.class))
            )
    );

    @Override
    public @NotNull Key id() {
        return IDENTIFIER;
    }

    @Override
    public @NotNull Iterable<Conversion<?, ?>> conversions() {
        return CONVERSIONS;
    }
}
