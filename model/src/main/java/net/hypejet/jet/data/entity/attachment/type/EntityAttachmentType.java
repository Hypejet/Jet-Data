package net.hypejet.jet.data.entity.attachment.type;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a type of Minecraft entity attachment.
 *
 * @since 1.0
 * @author Codsestech
 */
public sealed interface EntityAttachmentType permits EntityAttachmentTypeImpl {
    /**
     * Gets a name of the attachment type.
     *
     * @return the name
     * @since 1.0
     */
    @NonNull String name();
}