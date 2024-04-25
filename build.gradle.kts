plugins {
    java
    idea

    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.flywaydb.flyway") version "10.11.1"
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
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("xyz.xenondevs.invui:invui:1.29")
    implementation("dev.rollczi:litecommands-bukkit:3.4.1")

    implementation("org.xerial:sqlite-jdbc:3.45.3.0")

    implementation("org.flywaydb:flyway-core:10.11.1")
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
