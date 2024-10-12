plugins {
    `maven-publish`
}

val generatorProject = project(":jet-data-generator")

dependencies {
    compileOnly(libs.adventureApi)
    compileOnly(libs.checkerFrameworkQual)
    compileOnly(project(":jet-data-model-server"))
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    generatorProject.afterEvaluate { // Run the task configuration after evaluation, when all tasks are initialized
        withType<JavaCompile> {
            dependsOn(generatorProject.tasks.named<JavaExec>("run"))
        }
    }
    withType<Javadoc> {
        val docletOptions = options as StandardJavadocDocletOptions
        docletOptions.addBooleanOption("html5", true)
        docletOptions.addStringOption("Xdoclint:none", "-quiet")
    }
}