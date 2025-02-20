import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.mavenpublish)
    alias(libs.plugins.compose.compiler)
    id("convention.jvm.toolchain")
}

kotlin {
    androidTarget()

    jvm()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.library()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)

                api(libs.minabox)
            }
        }
    }
}

android {
    namespace = "eu.wewox.lazytable"

    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
    }
    buildFeatures {
        compose = true
    }
    kotlin {
        explicitApi()

        androidTarget {
            publishLibraryVariants("release", "debug")
        }
    }
}
