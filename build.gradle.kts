plugins {
    java
}

tasks {
    jar {
        enabled = false
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "idea")

    group = "pl.nadwey.nadarenas"
    version = "0.0.1-SNAPSHOT"

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    repositories {
        mavenCentral()

        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://repo.xenondevs.xyz/releases")
        maven("https://repo.panda-lang.org/releases")
        maven("https://maven.enginehub.org/repo/")
        maven("https://storehouse.okaeri.eu/repository/maven-public/")
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.34")
        annotationProcessor("org.projectlombok:lombok:1.18.34")
    }
}
