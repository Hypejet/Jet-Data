plugins {
    `maven-publish`
}

dependencies {
    compileOnly(project(":jet-data-model-api"))
    compileOnly(libs.guava)
    compileOnly(libs.bundles.adventure)
    compileOnly(libs.checkerFrameworkQual)
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        pom.licenses {
            license {
                name = "AGPL 3.0"
                url = "https://choosealicense.com/licenses/agpl-3.0/"
            }
        }
    }
}