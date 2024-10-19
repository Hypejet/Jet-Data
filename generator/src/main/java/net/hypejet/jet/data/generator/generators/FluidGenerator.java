package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.hypejet.jet.data.model.server.registry.registries.fluid.Fluid;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * Represents a {@linkplain Generator generator} of {@linkplain Fluid fluids}.
 *
 * @since 1.0
 * @author Codestech
 * @see Fluid
 * @see Generator
 */
public final class FluidGenerator extends Generator<Fluid> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain FluidGenerator fluid generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public FluidGenerator(@NonNull RegistryAccess registryAccess) {
        super(new GeneratorName("Fluid", "Generator"),
                new ResourceFileSettings("fluids", JetDataJson.createFluidsGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.SERVER, "Fluids"));
        this.registryAccess = NullabilityUtil.requireNonNull(registryAccess, "registry access");
    }

    @Override
    public @NonNull List<DataRegistryEntry<Fluid>> generate() {
        Fluid fluidInstance = new Fluid();
        return RegistryUtil.createEntries(this.registryAccess.lookupOrThrow(Registries.FLUID), fluid -> fluidInstance);
    }
}