import org.spongepowered.gradle.vanilla.repository.MinecraftPlatform

plugins {
    application
    alias(libs.plugins.vanillaGradle)
}

dependencies {
    implementation(project(":jet-data-model"))
    implementation(libs.checkerFrameworkQual)
    implementation(libs.adventureApi)
    implementation(libs.javaPoet)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

application {
    mainClass.set("net.hypejet.jet.data.generator.GeneratorEntrypoint")
}

minecraft {
    version("1.21")
    platform(MinecraftPlatform.SERVER)
}