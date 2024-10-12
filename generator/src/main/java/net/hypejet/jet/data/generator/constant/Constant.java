package net.hypejet.jet.data.generator.constant;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import net.hypejet.jet.data.model.utils.NullabilityUtil;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;

/**
 * Represents information of a Java constant.
 *
 * @param name a name of the constant
 * @param typeName a name of type of the constant
 * @param initializer an initializer of the constant
 * @param javadoc a javadoc of the constant
 * @since 1.0
 * @author Codestech
 */
public record Constant(@NonNull String name, @NonNull TypeName typeName, @NonNull CodeBlock initializer,
                       @NonNull CodeBlock javadoc) {
    /**
     * Constructs the {@linkplain Constant constant}.
     *
     * @param name a name of the constant
     * @param type a type of the constant
     * @param initializer an initializer of the constant
     * @param javadoc a javadoc of the constant
     * @since 1.0
     */
    public Constant(@NonNull String name, @NonNull Type type, @NonNull CodeBlock initializer,
                    @NonNull CodeBlock javadoc) {
        this(name, TypeName.get(NullabilityUtil.requireNonNull(type, "type")), initializer, javadoc);
    }

    /**
     * Constructs the {@linkplain Constant constant}.
     *
     * @param name a name of the constant
     * @param typeName a name of type of the constant
     * @param initializer an initializer of the constant
     * @param javadoc a javadoc of the constant
     * @since 1.0
     */
    public Constant {
        NullabilityUtil.requireNonNull(name, "name");
        NullabilityUtil.requireNonNull(typeName, "type name");
        NullabilityUtil.requireNonNull(initializer, "initializer");
        NullabilityUtil.requireNonNull(javadoc, "javadoc");
    }

    /**
     * Creates a {@linkplain FieldSpec field spec} from this constant.
     *
     * @return the field spec
     * @since 1.0
     */
    public @NonNull FieldSpec toFieldSpec() {
        return FieldSpec.builder(this.typeName, this.name)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer(this.initializer)
                .addJavadoc(this.javadoc)
                .build();
    }
}