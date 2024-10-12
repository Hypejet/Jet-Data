plugins {
    `maven-publish`
}

dependencies {
    compileOnly(libs.bundles.adventure)
    compileOnly(libs.checkerFrameworkQual)
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}