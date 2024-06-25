plugins {
    `maven-publish`
    alias(libs.plugins.checkerFrameworkGradle)
}

dependencies {
    compileOnly(libs.adventureApi)
    checkerFramework(libs.checkerFrameworkQual)
}

checkerFramework {
    checkers = listOf("org.checkerframework.checker.nullness.NullnessChecker")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}