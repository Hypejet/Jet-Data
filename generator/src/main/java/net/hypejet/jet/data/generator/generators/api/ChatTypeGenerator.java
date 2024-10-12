package net.hypejet.jet.data.generator.generators.api;

import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.adapter.PackAdapter;
import net.hypejet.jet.data.generator.adapter.StyleAdapter;
import net.hypejet.jet.data.model.api.registry.registries.chat.ChatType;
import net.hypejet.jet.data.model.api.registry.registries.chat.ChatTypeDataRegistryEntry;
import net.hypejet.jet.data.model.api.registry.registries.chat.decoration.ChatDecoration;
import net.hypejet.jet.data.model.api.registry.registries.chat.decoration.ChatDecorationParameter;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ChatTypeDecoration;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.repository.KnownPack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class ChatTypeGenerator extends Generator<ChatType> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain ChatTypeGenerator chat type generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public ChatTypeGenerator(@NonNull RegistryAccess registryAccess) {
        super("chat-types", "ChatTypes", true);
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<ChatTypeDataRegistryEntry> generate() {
        List<ChatTypeDataRegistryEntry> entries = new ArrayList<>();
        Registry<net.minecraft.network.chat.ChatType> registry = this.registryAccess
                .lookupOrThrow(Registries.CHAT_TYPE);

        registry.forEach(chatType -> {
            ResourceKey<net.minecraft.network.chat.ChatType> key = registry.getResourceKey(chatType).orElseThrow();
            KnownPack knownPack = registry.registrationInfo(key)
                    .flatMap(RegistrationInfo::knownPackInfo)
                    .orElseThrow();

            ChatType convertedChatType = new ChatType(convert(chatType.chat()), convert(chatType.narration()));
            entries.add(new ChatTypeDataRegistryEntry(IdentifierAdapter.convert(key.location()),
                    convertedChatType, PackAdapter.convert(knownPack)));
        });

        return List.copyOf(entries);
    }

    private @NonNull ChatDecoration convert(@NonNull ChatTypeDecoration decoration) {
        List<ChatDecorationParameter> parameters = new ArrayList<>();

        for (ChatTypeDecoration.Parameter parameter : decoration.parameters()) {
            ChatDecorationParameter convertedParameter = switch (parameter) {
                case SENDER -> ChatDecorationParameter.SENDER;
                case TARGET -> ChatDecorationParameter.TARGET;
                case CONTENT -> ChatDecorationParameter.CONTENT;
            };
            parameters.add(convertedParameter);
        }

        return new ChatDecoration(decoration.translationKey(),
                StyleAdapter.convert(decoration.style(), this.registryAccess),
                List.copyOf(parameters));
    }
}