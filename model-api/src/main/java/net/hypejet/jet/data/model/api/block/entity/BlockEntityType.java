package net.hypejet.jet.data.model.api.block.entity;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Set;

/**
 * Represents a type of Minecraft block entity.
 *
 * @param validBlocks keys of block types that should support the block entity type
 * @since 1.0
 */
public record BlockEntityType(@NonNull Set<Key> validBlocks) {
    /**
     * Constructs the {@linkplain BlockEntityType block entity type}.
     *
     * @param validBlocks keys of block types that should support the block entity type
     * @since 1.0
     */
    public BlockEntityType {
        validBlocks = Set.copyOf(NullabilityUtil.requireNonNull(validBlocks, "valid blocks"));
    }
}