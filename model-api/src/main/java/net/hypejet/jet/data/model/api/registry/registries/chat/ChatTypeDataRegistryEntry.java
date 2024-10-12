package net.hypejet.jet.data.model.api.registry.registries.chat;

import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain DataRegistryEntry data registry entry} which holds a {@linkplain ChatType chat type}.
 *
 * @since 1.0
 * @author Codestech
 * @see ChatType
 * @see DataRegistryEntry
 */
public final class ChatTypeDataRegistryEntry extends DataRegistryEntry<ChatType> {
    /**
     * Constructs the {@linkplain ChatTypeDataRegistryEntry chat type data registry entry}.
     *
     * @param key  an identifier, which the registry entry should have
     * @param value the chat type
     * @param knownPackInfo an information of a feature pack, which enables the data registry entry
     * @since 1.0
     */
    public ChatTypeDataRegistryEntry(@NonNull Key key, @NonNull ChatType value, @NonNull PackInfo knownPackInfo) {
        super(key, value, knownPackInfo);
    }
}