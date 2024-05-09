plugins {
    java
    idea

    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.flywaydb.flyway") version "10.12.0"
}

group = "pl.nadwey"
version = "0.0.1-SNAPSHOT"
val mainPackage = "${project.group}.${rootProject.name.lowercase()}"

repositories {
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.xenondevs.xyz/releases")
    maven("https://repo.panda-lang.org/releases")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("xyz.xenondevs.invui:invui:1.30")
    implementation("dev.rollczi:litecommands-bukkit:3.4.1")
    implementation("org.apache.commons:commons-text:1.12.0")

    implementation("org.springframework:spring-jdbc:6.1.6")
    implementation("org.flywaydb:flyway-core:10.12.0")

    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.0")
}

val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}
