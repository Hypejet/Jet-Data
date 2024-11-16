package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.hypejet.jet.data.model.server.registry.registries.registry.DataRegistryEntry;
import net.minecraft.network.ProtocolInfo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents {@linkplain Generator a generator}, which generates mappings of names of all packets and their names.
 *
 * @since 1.0
 * @author Codestech
 * @see Generator
 */
public final class PacketIdentifierGenerator extends Generator<Integer> {

    private final ProtocolInfo.Unbound<?, ?> protocolInfo;

    /**
     * Constructs the {@linkplain Generator generator}.
     *
     * @param generatorName a name of the generator
     * @param resourceFileName a name of a resource file, which should contain mapping of names and identifiers
     *                         of all packets
     * @param javaFileName a name of a java file, which should contain names of all packets
     * @param protocolInfo protocol info, from which the packets should be extracted
     * @since 1.0
     */
    public PacketIdentifierGenerator(@NonNull GeneratorName generatorName, @NonNull String resourceFileName,
                                     @NonNull String javaFileName, ProtocolInfo.@NonNull Unbound<?, ?> protocolInfo) {
        super(generatorName,
                new ResourceFileSettings(NullabilityUtil.requireNonNull(resourceFileName, "resource file name"),
                        JetDataJson.createPacketGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.SERVER,
                        NullabilityUtil.requireNonNull(javaFileName, "java file name")));
        this.protocolInfo = NullabilityUtil.requireNonNull(protocolInfo, "protocol info");
    }

    @Override
    public @NonNull List<DataRegistryEntry<Integer>> generate() {
        List<DataRegistryEntry<Integer>> entries = new ArrayList<>();
        this.protocolInfo.listPackets((type, identifier) -> entries.add(new DataRegistryEntry<>(
                IdentifierAdapter.convert(type.id()), identifier, null, null
        )));
        return List.copyOf(entries);
    }
}
