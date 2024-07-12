plugins {
    `maven-publish`
}

dependencies {
    implementation("org.jetbrains:annotations:24.1.0")

    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.3")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "pl.nadwey"
            artifactId = "nadarenas-api"
            version = project.version.toString()

            from(components["java"])
        }
    }
}
