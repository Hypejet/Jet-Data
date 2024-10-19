package net.hypejet.jet.data.model.api.registries.chat.decoration;

import net.hypejet.jet.data.model.api.registries.chat.ChatType;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.text.format.Style;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;


/**
 * Represents a decoration of a {@linkplain ChatType chat type}.
 *
 * @param translationKey an identifier of a translation of the decoration
 * @param style a style that should be applied to messages with chat type using the decoration
 * @param parameters placeholders which should be used during translation using the translation key specified
 * @since 1.0
 * @author Codestech
 * @see ChatType
 */
public record ChatDecoration(@NonNull String translationKey, @Nullable Style style,
                             @NonNull List<ChatDecorationParameter> parameters) {
    /**
     * Constructs the {@linkplain ChatDecoration chat decoration}.
     *
     * @param translationKey an identifier of a translation of the decoration
     * @param style a style that should be applied to messages with chat type using the decoration
     * @param parameters placeholders which should be used during translation using the translation key specified
     * @since 1.0
     */
    public ChatDecoration {
        NullabilityUtil.requireNonNull(translationKey, "translation key");
        NullabilityUtil.requireNonNull(parameters, "parameters");
        parameters = List.copyOf(parameters);
    }
}