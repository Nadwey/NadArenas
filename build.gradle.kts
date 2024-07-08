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
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.34")
        annotationProcessor("org.projectlombok:lombok:1.18.34")
    }
}
