dependencies {
    constraints {
        implementation("org.yaml:snakeyaml") {
            version {
                require("2.2")
                reject("1.33")
            }
        }
    }

    implementation("org.flywaydb:flyway-core:10.15.2")
    implementation("org.apache.commons:commons-text:1.12.0")
    implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:5.0.2")
    implementation("org.yaml:snakeyaml:2.2")
    implementation("org.jetbrains:annotations:24.1.0")

    implementation(project(":api"))
}
