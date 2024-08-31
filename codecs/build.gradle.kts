plugins {
    `maven-publish`
}

dependencies {
    compileOnly(project(":jet-data-model"))
    compileOnly(libs.gson)
    compileOnly(libs.bundles.adventure)
    compileOnly(libs.checkerFrameworkQual)
    compileOnly(libs.bundles.adventureSerializers)
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}