import org.gradle.jvm.tasks.Jar

subprojects {
    apply<JavaPlugin>()

    group = "net.hypejet"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    extensions.getByType<JavaPluginExtension>().apply {
        withJavadocJar()
        withSourcesJar()
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }

    tasks.withType<Jar> {
        manifest {
            attributes("Automatic-Module-Name" to "net.hypejet.jet.data")
        }
    }
}