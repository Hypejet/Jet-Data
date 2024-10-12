plugins {
    `maven-publish`
}

dependencies {
    compileOnly(project(":jet-data-model-api"))
    compileOnly(libs.bundles.adventure)
    compileOnly(libs.checkerFrameworkQual)
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}