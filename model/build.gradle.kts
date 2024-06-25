plugins {
    `maven-publish`
    alias(libs.plugins.checkerFrameworkGradle)
}

dependencies {
    compileOnly(libs.adventureApi)
    compileOnly(libs.checkerFrameworkQual)
    checkerFramework(libs.checkerFrameworkChecker)
}

checkerFramework {
    checkers = listOf("org.checkerframework.checker.nullness.NullnessChecker")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}