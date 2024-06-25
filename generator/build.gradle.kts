import org.spongepowered.gradle.vanilla.repository.MinecraftPlatform

plugins {
    application
    alias(libs.plugins.shadow)
    alias(libs.plugins.vanillaGradle)
}

dependencies {
    implementation(project(":model"))
    implementation(libs.checkerFrameworkQual)
    implementation(libs.adventureApi)
    implementation(libs.javaPoet)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

application {
    mainClass.set("net.hypejet.jet.data.generator.DataGenerator")
}

minecraft {
    version("1.21")
    platform(MinecraftPlatform.SERVER)
}