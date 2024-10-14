package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.entity.EntityType;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a {@linkplain Generator generator} of {@linkplain EntityType entity types}.
 *
 * @since 1.0
 * @author Codestech
 * @see EntityType
 * @see Generator
 */
public final class EntityTypeGenerator extends Generator<EntityType> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain EntityTypeGenerator entity type generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public EntityTypeGenerator(@NonNull RegistryAccess registryAccess) {
        super(new GeneratorName("Entity", "Type", "Generator"),
                new ResourceFileSettings("entity-types", JetDataJson.createEntityTypeGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.API, "EntityTypes"));
        this.registryAccess = registryAccess;
    }

    @Override
    public @NonNull List<DataRegistryEntry<EntityType>> generate() {
        return RegistryUtil.createEntries(this.registryAccess.lookupOrThrow(Registries.ENTITY_TYPE), entityType -> {
            Set<Key> requiredFeatureFlags = new HashSet<>();
            for (ResourceLocation name : FeatureFlags.REGISTRY.toNames(entityType.requiredFeatures()))
                requiredFeatureFlags.add(IdentifierAdapter.convert(name));
            return new EntityType(Set.copyOf(requiredFeatureFlags));
        });
    }
}