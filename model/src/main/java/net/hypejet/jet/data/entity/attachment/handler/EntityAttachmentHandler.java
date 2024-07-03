package net.hypejet.jet.data.entity.attachment.handler;

import net.hypejet.jet.data.entity.attachment.type.EntityAttachmentType;
import net.hypejet.jet.data.entity.attachment.value.EntityAttachmentValue;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a handler of Minecraft entity attachments.
 *
 * @param values the attachment values
 * @since 1.0
 * @author Codestech
 * @see EntityAttachmentType
 * @see EntityAttachmentValue
 */
public record EntityAttachmentHandler(@NonNull Map<EntityAttachmentType, EntityAttachmentValue> values) {
    /**
     * Constructs the {@linkplain EntityAttachmentHandler entity attachment handler}.
     *
     * @param values the attachment values
     * @since 1.0
     */
    public EntityAttachmentHandler {
        values = Map.copyOf(values);
    }

    /**
     * Gets an {@linkplain EntityAttachmentValue entity attachment value} by a {@linkplain EntityAttachmentType type}.
     *
     * @param type the type
     * @return the value
     * @since 1.0
     */
    public @Nullable EntityAttachmentValue getValue(@NonNull EntityAttachmentType type) {
        return this.values.get(type);
    }

    /**
     * Creates an {@linkplain EntityAttachmentHandler entity attachment handler} using
     * {@linkplain EntityAttachmentValue entity attachment values} specified.
     *
     * @param values the values
     * @return the handler
     * @since 1.0
     */
    public static @NonNull EntityAttachmentHandler fromValues(@NonNull EntityAttachmentValue @NonNull ... values) {
        Map<EntityAttachmentType, EntityAttachmentValue> valueMap = new HashMap<>();
        for (EntityAttachmentValue value : values)
            valueMap.put(value.type(), value);
        return new EntityAttachmentHandler(valueMap);
    }
}