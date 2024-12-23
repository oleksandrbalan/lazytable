pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.7.0")
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        mavenLocal()
        maven("https://jitpack.io")
        maven {
            url = uri("https://maven.pkg.github.com/joshafeinberg/minabox")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: "joshafeinberg"
                password = System.getenv("GITHUB_TOKEN") ?: ""
            }
        }
    }
}


rootProject.name = "LazyTable"
include(":demo")
include(":lazytable")
include(":desktopdemo")
include(":androiddemo")
include(":wasmdemo")
