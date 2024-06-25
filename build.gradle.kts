subprojects {
    apply<JavaPlugin>()

    group = "net.hypejet"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    extensions.getByType<JavaPluginExtension>().apply {
        withJavadocJar()
        withSourcesJar()
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }
}