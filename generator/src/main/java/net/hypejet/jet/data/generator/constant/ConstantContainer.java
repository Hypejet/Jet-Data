package net.hypejet.jet.data.generator.constant;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import net.hypejet.jet.data.model.utils.NullabilityUtil;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents information of a Java class that contains constants.
 *
 * @param name a name of the class
 * @param javadoc a javadoc of the class
 * @param constants a collection of the constants
 * @since 1.0
 * @author Codestech
 * @see Constant
 */
public record ConstantContainer(@NonNull String name, @NonNull CodeBlock javadoc,
                                @NonNull Collection<Constant> constants) {
    /**
     * Constructs the {@linkplain ConstantContainer constant container}.
     *
     * @param name a name of the class
     * @param javadoc a javadoc of the class
     * @param constants a collection of the constants
     * @since 1.0
     */
    public ConstantContainer {
        NullabilityUtil.requireNonNull(name, "name");
        NullabilityUtil.requireNonNull(javadoc, "javadoc");
        constants = List.copyOf(NullabilityUtil.requireNonNull(constants, "constants"));
    }

    /**
     * Creates a {@linkplain TypeSpec type spec} of this {@linkplain ConstantContainer constant container}.
     *
     * @return the type spec
     * @since 1.0
     */
    public @NonNull TypeSpec toTypeSpec() {
        List<FieldSpec> fieldSpecs = new ArrayList<>();
        this.constants.forEach(constant -> fieldSpecs.add(constant.toFieldSpec()));
        return TypeSpec.classBuilder(this.name)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addFields(List.copyOf(fieldSpecs))
                .addJavadoc(this.javadoc)
                .build();
    }
}