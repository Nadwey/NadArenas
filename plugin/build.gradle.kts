plugins {
    id("io.github.goooler.shadow") version "8.1.8"
}

repositories {
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.xenondevs.xyz/releases")
    maven("https://repo.panda-lang.org/releases")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")

    implementation("xyz.xenondevs.invui:invui:1.32")
    implementation("dev.rollczi:litecommands-bukkit:3.4.2")
    implementation("org.apache.commons:commons-text:1.12.0")
    implementation("org.flywaydb:flyway-core:10.15.2")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.3")

    implementation(project(":api"))
    implementation(project(":common"))
}

tasks {
    jar {
        enabled = false
    }

    shadowJar {
        archiveFileName.set("NadArenas-${project.version}.jar")
    }

    build {
        dependsOn(shadowJar)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}
