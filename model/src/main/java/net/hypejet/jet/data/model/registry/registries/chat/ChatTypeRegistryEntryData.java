package net.hypejet.jet.data.model.registry.registries.chat;

import net.hypejet.jet.data.model.pack.DataPack;
import net.hypejet.jet.data.model.registry.RegistryEntryData;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@linkplain RegistryEntryData registry entry} which holds a {@linkplain ChatType chat type}.
 *
 * @since 1.0
 * @author Codestech
 * @see ChatType
 * @see RegistryEntryData
 */
public final class ChatTypeRegistryEntryData extends RegistryEntryData<ChatType> {
    /**
     * Constructs the {@linkplain RegistryEntryData registry entry}.
     *
     * @param key  an identifier, which the registry entry should have
     * @param knownPack a data pack, which should enable the registry entry
     * @param value the chat type
     * @since 1.0
     */
    public ChatTypeRegistryEntryData(@NonNull Key key, @NonNull DataPack knownPack, @NonNull ChatType value) {
        super(key, knownPack, value);
    }
}