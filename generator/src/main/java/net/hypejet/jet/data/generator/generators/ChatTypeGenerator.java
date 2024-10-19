package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.StyleAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.registry.registries.chat.ChatType;
import net.hypejet.jet.data.model.api.registry.registries.chat.decoration.ChatDecoration;
import net.hypejet.jet.data.model.api.registry.registries.chat.decoration.ChatDecorationParameter;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ChatTypeDecoration;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@linkplain Generator generator} of {@linkplain ChatType chat types}.
 *
 * @since 1.0
 * @author Codestech
 * @see ChatType
 * @see Generator
 */
public final class ChatTypeGenerator extends Generator<ChatType> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain ChatTypeGenerator chat type generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public ChatTypeGenerator(@NonNull RegistryAccess registryAccess) {
        super(new GeneratorName("Chat", "Type", "Generator"),
                new ResourceFileSettings("chat-types", JetDataJson.createChatTypesGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.API, "ChatTypes"));
        this.registryAccess = NullabilityUtil.requireNonNull(registryAccess, "registry access");
    }

    @Override
    public @NonNull List<DataRegistryEntry<ChatType>> generate() {
        Registry<net.minecraft.network.chat.ChatType> registry = this.registryAccess
                .lookupOrThrow(Registries.CHAT_TYPE);
        return RegistryUtil.createEntries(registry, chatType -> new ChatType(convert(chatType.chat()),
                convert(chatType.narration())));
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