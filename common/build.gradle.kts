repositories {
    mavenCentral()
}

dependencies {
    implementation("org.flywaydb:flyway-core:10.15.2")
    implementation("org.apache.commons:commons-text:1.12.0")

    implementation(project(":api"))
}

tasks {

}
