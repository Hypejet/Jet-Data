package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.minecraft.network.protocol.game.GameProtocols;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@linkplain Generator generator} of Minecraft client packet identifiers.
 *
 * @since 1.0
 * @author Codestech
 * @see Generator
 */
public final class ClientPacketIdentifierGenerator extends Generator<Integer> {
    /**
     * Constructs the {@linkplain ClientPacketIdentifierGenerator client packet identifier generator}.
     *
     * @since 1.0
     */
    public ClientPacketIdentifierGenerator() {
        super(new GeneratorName("Client", "Packet", "Identifier"),
                new ResourceFileSettings("client-packet-identifiers", JetDataJson.createPacketGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.SERVER,
                        "ClientPacketIdentifiers"));
    }

    @Override
    public @NonNull List<DataRegistryEntry<Integer>> generate() {
        List<DataRegistryEntry<Integer>> entries = new ArrayList<>();
        GameProtocols.SERVERBOUND_TEMPLATE.listPackets((type, identifier) -> entries.add(new DataRegistryEntry<>(
                IdentifierAdapter.convert(type.id()), identifier, null, null
        )));
        return List.copyOf(entries);
    }
}