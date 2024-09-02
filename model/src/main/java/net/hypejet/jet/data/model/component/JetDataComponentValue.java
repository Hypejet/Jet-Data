package net.hypejet.jet.data.model.component;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagLike;
import net.kyori.adventure.text.event.DataComponentValue;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@linkplain DataComponentValue data component value}, which contains a value serialized as
 * a {@linkplain BinaryTag binary tag}.
 *
 * @param binaryTag the binary tag
 * @since 1.0
 * @author Codestech
 * @see DataComponentValue
 */
public record JetDataComponentValue(@NonNull BinaryTag binaryTag) implements DataComponentValue, BinaryTagLike {
    @Override
    public @NotNull BinaryTag asBinaryTag() {
        return this.binaryTag;
    }
}