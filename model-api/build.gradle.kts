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
        pom.licenses {
            license {
                name = "MIT"
                url = "https://choosealicense.com/licenses/mit/"
            }
        }
    }
}