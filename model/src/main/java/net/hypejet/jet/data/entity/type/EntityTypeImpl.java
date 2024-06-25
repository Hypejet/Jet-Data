package net.hypejet.jet.data.entity.type;

import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an implementation of the {@linkplain EntityType entity type}.
 *
 * @param key a key of the entity type
 * @param identifier a numeric identifier of the block
 * @since 1.0
 * @author Codestech
 */
record EntityTypeImpl(@NonNull Key key, int identifier) implements EntityType {}