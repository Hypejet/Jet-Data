plugins {
    alias(libs.plugins.checkerFrameworkGradle)
}

dependencies {
    compileOnly(libs.adventureApi)
    checkerFramework(libs.checkerFrameworkQual)
}

checkerFramework {
    checkers = listOf("org.checkerframework.checker.nullness.NullnessChecker")
}