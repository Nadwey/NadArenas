plugins {
    java
    idea

    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.jooq.jooq-codegen-gradle") version "3.19.8"
    id("org.flywaydb.flyway") version "10.13.0"
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
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
    implementation("xyz.xenondevs.invui:invui:1.30")
    implementation("dev.rollczi:litecommands-bukkit:3.4.1")
    implementation("org.apache.commons:commons-text:1.12.0")

    implementation("org.flywaydb:flyway-core:10.13.0")
    implementation("org.jooq:jooq:3.19.8")
    jooqCodegen("org.jooq:jooq-meta:3.19.8")
    jooqCodegen("org.jooq:jooq-meta-extensions:3.19.8")

    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.1")
}

val targetJavaVersion = 21
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


jooq {
    configuration {
        generator {
            database {
                name = "org.jooq.meta.extensions.ddl.DDLDatabase"

                properties {
                    property {
                        key = "scripts"
                        value = "src/main/resources/db/migration/*.sql"
                    }
                }
            }

            target {
                packageName = "org.jooq.generated"
                directory = "${layout.projectDirectory}/src/main/java"
            }
        }
    }
}
