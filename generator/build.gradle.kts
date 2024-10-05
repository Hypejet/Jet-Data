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

val autogeneratedApiProject = project(":jet-data-autogenerated-api")
val autogeneratedServerProject = project(":jet-data-autogenerated-server")

dependencies {
    implementation(project(":jet-data-model"))
    implementation(project(":jet-data-codecs"))
    implementation(libs.checkerFrameworkQual)
    implementation(libs.javaPoet)
    implementation(libs.bundles.adventure)
    implementation(libs.bundles.adventureSerializers)
}

application {
    mainClass.set("net.hypejet.jet.data.generator.GeneratorEntrypoint")
}

minecraft {
    version(libs.versions.minecraftVersion.get())
    platform(MinecraftPlatform.SERVER)
}

tasks.named<JavaExec>("run") {
    val apiSourceSetContainer = autogeneratedApiProject.sourceSets.main.get()
    val apiResourcesSourcePath = prepareSourceSetPath(apiSourceSetContainer.resources)
    val apiJavaSourcePath = prepareSourceSetPath(apiSourceSetContainer.java)

    val serverSourceSetContainer = autogeneratedServerProject.sourceSets.main.get()
    val serverResourcesSourcePath = prepareSourceSetPath(serverSourceSetContainer.resources)
    val serverJavaSourcePath = prepareSourceSetPath(serverSourceSetContainer.java)

    args(apiResourcesSourcePath.toString(), apiJavaSourcePath.toString(),
        serverResourcesSourcePath.toString(), serverJavaSourcePath.toString())
}

private fun prepareSourceSetPath(set: SourceDirectorySet): Path {
    val directories = set.sourceDirectories.toList()
    if (directories.size != 1)
        error("Expected from source set of an autogenerated project to have exactly one directory")

    val sourceDirectory = directories.first().toPath().toAbsolutePath()
    if (sourceDirectory.exists())
        Files.list(sourceDirectory).forEach(::removeRecursively)

    return sourceDirectory
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