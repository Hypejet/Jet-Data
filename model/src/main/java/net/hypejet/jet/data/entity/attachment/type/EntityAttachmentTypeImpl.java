package net.hypejet.jet.data.entity.attachment.type;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an implementation of the {@linkplain EntityAttachmentType entity attachment type}.
 *
 * @param name the name of the attachment type
 * @since 1.0
 * @author Codestech
 */
record EntityAttachmentTypeImpl(@NonNull String name) implements EntityAttachmentType {}