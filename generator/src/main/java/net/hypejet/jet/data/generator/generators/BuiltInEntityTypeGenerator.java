package net.hypejet.jet.data.generator.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import net.hypejet.jet.coordinate.Vector;
import net.hypejet.jet.data.entity.attachment.handler.EntityAttachmentHandler;
import net.hypejet.jet.data.entity.attachment.value.EntityAttachmentValue;
import net.hypejet.jet.data.generator.ConstantGenerator;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.util.CodeBlocks;
import net.hypejet.jet.data.generator.util.JavaDocBuilder;
import net.hypejet.jet.data.generator.util.ReflectionUtil;
import net.hypejet.jet.data.generator.util.ResourceLocationUtil;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.EntityAttachments;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a {@linkplain ConstantGenerator constant generator}, which
 * generates {@linkplain net.hypejet.jet.data.entity.type.EntityType entity types} using data defined in
 * an {@linkplain BuiltInRegistries#ENTITY_TYPE entity type registry}.
 *
 * @since 1.0
 * @author Codestech
 * @see net.hypejet.jet.data.entity.type.EntityType
 * @see BuiltInRegistries#ENTITY_TYPE
 * @see Generator
 */
public final class BuiltInEntityTypeGenerator extends ConstantGenerator {

    private static final String PACKAGE = "net.hypejet.jet.data.entity.type";
    private static final String CLASS = "BuiltInEntityTypes";

    static final ClassName CLASS_NAME = ClassName.get(PACKAGE, CLASS);

    private static final Class<?> ENTITY_TYPE_CLASS = net.hypejet.jet.data.entity.type.EntityType.class;
    private static final Class<?> ENTITY_DIMENSIONS_CLASS = net.hypejet.jet.data.entity.type.EntityType
            .EntityDimensions.class;

    private static final Field SPAWN_DIMENSIONS_SCALE_FIELD;
    private static final Field IMMUNE_TO_FIELD;
    private static final Field ATTACHMENTS_FIELD;

    static {
        try {
            SPAWN_DIMENSIONS_SCALE_FIELD = EntityType.class.getDeclaredField("spawnDimensionsScale");
            IMMUNE_TO_FIELD = EntityType.class.getDeclaredField("immuneTo");
            ATTACHMENTS_FIELD = EntityAttachments.class.getDeclaredField("attachments");
        } catch (NoSuchFieldException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Constructs the {@linkplain BuiltInEntityTypeGenerator vanilla entity type generator}.
     *
     * @since 1.0
     */
    public BuiltInEntityTypeGenerator() {
        super(PACKAGE, CLASS_NAME, ENTITY_TYPE_CLASS,
                JavaDocBuilder.builder()
                        .line("Represents a holder of built-in Minecraft entity types.")
                        .paragraph("Code autogenerated, do not edit!")
                        .emptyLine()
                        .since("1.0")
                        .see(ENTITY_TYPE_CLASS)
                        .build(),
                JavaDocBuilder.builder()
                        .line("Gets all built-in entity types.")
                        .emptyLine()
                        .returns("the types")
                        .since("1.0")
                        .see(ENTITY_TYPE_CLASS)
                        .build());
    }

    @Override
    public @NonNull Collection<FieldSpec> generateFields(@NonNull Logger logger) {
        DefaultedRegistry<EntityType<?>> registry = BuiltInRegistries.ENTITY_TYPE;
        ClassName entityTypeImplClassName = ClassName.get(PACKAGE, "EntityTypeImpl");

        List<FieldSpec> entityTypeFieldSpecs = new ArrayList<>();

        for (EntityType<?> entityType : registry) {
            ResourceLocation location = ResourceLocationUtil.getOrThrow(registry, entityType);
            EntityDimensions entityDimensions = entityType.getDimensions();

            Map<?, ?> attachmentMap = getAttachments(entityDimensions.attachments());
            List<CodeBlock> attachmentEntriesBlocks = new ArrayList<>();

            for (Map.Entry<?, ?> entry : attachmentMap.entrySet()) {
                EntityAttachment attachment = (EntityAttachment) entry.getKey();
                List<CodeBlock> vectorCodeBlocks = new ArrayList<>();

                for (Object object : (List<?>) entry.getValue()) {
                    Vec3 vec3 = (Vec3) object;
                    vectorCodeBlocks.add(CodeBlocks.staticMethodInvocation(Vector.class, "vector", vec3.x, vec3.y,
                            vec3.z));
                }

                CodeBlock attachmentTypeReference = CodeBlocks.staticFieldReference(
                        BuiltInEntityAttachmentTypeGenerator.CLASS_NAME,
                        BuiltInEntityAttachmentTypeGenerator.constantName(attachment)
                );

                attachmentEntriesBlocks.add(CodeBlocks.constructor(EntityAttachmentValue.class,
                        attachmentTypeReference, CodeBlocks.list(vectorCodeBlocks.toArray())));
            }

            CodeBlock entityDimensionsConstructor = CodeBlocks.constructor(ENTITY_DIMENSIONS_CLASS,
                    CodeBlocks.floatValue(entityDimensions.width()), CodeBlocks.floatValue(entityDimensions.height()),
                    CodeBlocks.floatValue(entityDimensions.eyeHeight()), entityDimensions.fixed(),
                    CodeBlocks.staticMethodInvocation(EntityAttachmentHandler.class, "fromValues",
                            attachmentEntriesBlocks.toArray()));

            boolean canSerialize = entityType.canSerialize();
            boolean canSummon = entityType.canSummon();
            boolean fireImmune = entityType.fireImmune();
            boolean canSpawnFarFromPlayer = entityType.canSpawnFarFromPlayer();

            int clientTrackingRange = entityType.clientTrackingRange();
            int updateInterval = entityType.updateInterval();

            float spawnDimensionsScale = getSpawnDimensionsScale(entityType);

            List<CodeBlock> immuneTo = new ArrayList<>();

            for (Object object : getImmuneTo(entityType)) {
                Block block = (Block) object;
                String constantName = BuiltInBlockGenerator.constantName(block);
                immuneTo.add(CodeBlocks.staticFieldReference(BuiltInBlockGenerator.CLASS_NAME, constantName));
            }

            CodeBlock entityCategoryBlock = CodeBlocks.staticFieldReference(BuiltInEntityCategoryGenerator.CLASS_NAME,
                    BuiltInEntityCategoryGenerator.constantName(entityType.getCategory()));

            Set<CodeBlock> requiredFlags = new HashSet<>();
            for (ResourceLocation name : FeatureFlags.REGISTRY.toNames(entityType.requiredFeatures())) {
                requiredFlags.add(CodeBlocks.staticFieldReference(
                        BuiltInFeatureFlagsGenerator.CLASS_NAME,
                        BuiltInFeatureFlagsGenerator.constantName(name)
                ));
            }

            entityTypeFieldSpecs.add(FieldSpec.builder(ENTITY_TYPE_CLASS, location.getPath().toUpperCase())
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer(CodeBlocks.constructor(entityTypeImplClassName, CodeBlocks.keyCreator(location),
                            registry.getId(entityType), canSerialize, canSummon, fireImmune, canSpawnFarFromPlayer,
                            clientTrackingRange, updateInterval, CodeBlocks.floatValue(spawnDimensionsScale),
                            entityDimensionsConstructor, entityCategoryBlock, CodeBlocks.setCreator(immuneTo.toArray()),
                            CodeBlocks.string(entityType.getDescriptionId()),
                            CodeBlocks.setCreator(requiredFlags.toArray())))
                    .build());
        }

        return List.copyOf(entityTypeFieldSpecs);
    }

    private static float getSpawnDimensionsScale(@NonNull EntityType<?> type) {
        return ReflectionUtil.access(SPAWN_DIMENSIONS_SCALE_FIELD, type, Field::getFloat);
    }

    private static @NonNull Set<?> getImmuneTo(@NonNull EntityType<?> type) {
        return ReflectionUtil.access(IMMUNE_TO_FIELD, type, (field, object) -> (Set<?>) field.get(object));
    }

    private static @NonNull Map<?, ?> getAttachments(@NonNull EntityAttachments attachments) {
        return ReflectionUtil.access(ATTACHMENTS_FIELD, attachments, (field, object) -> (Map<?, ?>) field.get(object));
    }
}