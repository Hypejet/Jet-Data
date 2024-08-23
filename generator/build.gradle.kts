import org.spongepowered.gradle.vanilla.repository.MinecraftPlatform
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

plugins {
    application
    alias(libs.plugins.vanillaGradle)
}

val autogeneratedProject = project(":jet-data-autogenerated")

dependencies {
    implementation(project(":jet-data-model"))
    implementation(project(":jet-data-codecs"))
    implementation(libs.checkerFrameworkQual)
    implementation(libs.bundles.adventure)
    implementation(libs.javaPoet)
}

application {
    mainClass.set("net.hypejet.jet.data.generator.GeneratorEntrypoint")
}

minecraft {
    version(libs.versions.minecraftVersion.get())
    platform(MinecraftPlatform.SERVER)
}

tasks.named<JavaExec>("run") {
    val sourceDirectories = autogeneratedProject.sourceSets.main.get().resources.sourceDirectories.toList()
    if (sourceDirectories.size != 1)
        error("Expected sources sets of autogenerated project to have exactly one source directory")

    val path = sourceDirectories.first().toPath().toAbsolutePath()
    if (path.exists() && path.isDirectory()) {
        // Remove the old generated files
        Files.list(path).forEach(::removeRecursively)
    }

    args(path.toString())
}

/**
 * Removes the path and its contents recursively.
 *
 * @since 1.0
 * @author Codestech
 */
private fun removeRecursively(path: Path) {
    if (!path.exists()) return

    if (path.isDirectory()) {
        Files.list(path).forEach(::removeRecursively)
    }

    path.deleteIfExists()
}