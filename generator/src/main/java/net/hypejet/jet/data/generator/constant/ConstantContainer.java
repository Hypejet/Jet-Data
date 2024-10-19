package net.hypejet.jet.data.generator.constant;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
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
 * @param destination a destination where a generated Java file should be put
 * @since 1.0
 * @author Codestech
 * @see Constant
 */
public record ConstantContainer(@NonNull String name, @NonNull CodeBlock javadoc,
                                @NonNull Collection<Constant> constants, @NonNull JavaFileDestination destination) {

    private static final String JAVA_FILE_INDENT = "    "; // 4 spaces

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
        NullabilityUtil.requireNonNull(destination, "destination");
    }

    /**
     * Creates a {@linkplain JavaFile java file} of this {@linkplain ConstantContainer constant container}.
     *
     * @param packageName a name of the package
     * @return the java file
     * @since 1.0
     */
    public @NonNull JavaFile toJavaFile(@NonNull String packageName) {
        NullabilityUtil.requireNonNull(packageName, "package name");

        List<FieldSpec> fieldSpecs = new ArrayList<>();
        this.constants.forEach(constant -> fieldSpecs.add(constant.toFieldSpec()));

        TypeSpec typeSpec = TypeSpec.classBuilder(this.name)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addFields(List.copyOf(fieldSpecs))
                .addJavadoc(this.javadoc)
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PRIVATE)
                        .build())
                .build();

        return JavaFile.builder(packageName, typeSpec)
                .indent(JAVA_FILE_INDENT)
                .build();
    }

    /**
     * Represents a destination where a generated Java file should be put.
     *
     * @since 1.0
     * @author Codestech
     */
    public enum JavaFileDestination {
        /**
         * A Java file destination of an API module.
         *
         * @since 1.0
         */
        API,
        /**
         * A Java file destination of a server module.
         *
         * @since 1.0
         */
        SERVER
    }
}