package net.hypejet.jet.data.entity.attachment.value;

import net.hypejet.jet.coordinate.Vector;
import net.hypejet.jet.data.entity.attachment.type.EntityAttachmentType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * Represents a holder of values of a Minecraft entity attachment.
 *
 * @param type the type of the attachment
 * @param attachments the values
 * @since 1.0
 * @author Codestech
 * @see EntityAttachmentType
 * @see Vector
 */
public record EntityAttachmentValue(@NonNull EntityAttachmentType type, @NonNull List<Vector> attachments) {
    /**
     * Constructs the {@linkplain EntityAttachmentValue entity attachment value}.
     *
     * @param type the type of the attachment
     * @param attachments the values
     * @since 1.0
     */
    public EntityAttachmentValue {
        attachments = List.copyOf(attachments);
    }

    /**
     * Gets an entity attachment value at an index specified.
     *
     * @param index the index
     * @return the entity attachment
     * @since 1.0
     */
    public @Nullable Vector get(int index) {
        return this.attachments.get(index);
    }
}