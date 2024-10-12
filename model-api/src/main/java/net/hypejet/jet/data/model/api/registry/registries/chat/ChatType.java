package net.hypejet.jet.data.model.api.registry.registries.chat;

import net.hypejet.jet.data.model.api.registry.registries.chat.decoration.ChatDecoration;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a type of Minecraft chat message.
 *
 * @param chatDecoration a decoration of the type, which should be used in chat
 * @param narrationDecoration a decoration of the type, which should be used in narrations
 * @since 1.0
 * @author Codestech
 */
public record ChatType(@NonNull ChatDecoration chatDecoration, @NonNull ChatDecoration narrationDecoration) {
    /**
     * Constructs the {@linkplain ChatType chat type}.
     *
     * @param chatDecoration a decoration of the type, which should be used in chat
     * @param narrationDecoration a decoration of the type, which should be used in narrations
     * @since 1.0
     */
    public ChatType {
        NullabilityUtil.requireNonNull(chatDecoration, "chat decoration");
        NullabilityUtil.requireNonNull(narrationDecoration, "narration decoration");
    }
}