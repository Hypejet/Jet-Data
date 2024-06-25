package net.hypejet.jet.data.entity.type;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a type of Minecraft entity.
 *
 * @since 1.0
 * @author Codestech
 * @see Keyed
 */
public sealed interface EntityType extends Keyed permits EntityTypeImpl {
    /**
     * Gets a {@linkplain Key key} of the entity type.
     *
     * @return the key
     * @since 1.0
     * @see Key
     */
    @Override
    @NonNull Key key();

    /**
     * Gets a numeric identifier of the entity type.
     *
     * @return the identifier
     * @since 1.0
     */
    int identifier();
}