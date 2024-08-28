package net.hypejet.jet.registry.registries.chat;

import net.hypejet.jet.pack.DataPack;
import net.hypejet.jet.registry.RegistryEntry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntry registry entry} which holds a {@linkplain ChatType chat type}.
 *
 * @since 1.0
 * @author Codestech
 * @see ChatType
 * @see RegistryEntry
 */
public final class ChatTypeRegistryEntry extends RegistryEntry<ChatType> {
    /**
     * Constructs the {@linkplain RegistryEntry registry entry}.
     *
     * @param key  an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the chat type
     * @since 1.0
     */
    public ChatTypeRegistryEntry(@NonNull Key key, @NonNull DataPack knownPack, @NonNull ChatType value) {
        super(key, knownPack, value);
    }
}