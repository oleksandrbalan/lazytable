import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.cocoapods)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
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

    cocoapods {
        version = "1.0.0"
        summary = "Demo Compose Multiplatform module"
        homepage = "---"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosdemo/Podfile")
        framework {
            baseName = "demo"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":lazytable"))

                implementation(compose.material3)

                implementation(libs.ktor)
                implementation(libs.ktor.content.negotiation)
                implementation(libs.ktor.serialization.json)
                implementation(libs.ktx.serialization)

                implementation(libs.image.loader)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.okhttp)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.compose.uitooling)
                implementation(libs.compose.uitoolingpreview)
                implementation(libs.ktor.android)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.darwin)
            }
        }

        val wasmJsMain by getting {
            dependencies {
                implementation(libs.ktor.wasmJs)
            }
        }

        all {
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
        }
    }
}

android {
    namespace = "eu.wewox.lazytable.demo"

    compileSdk = libs.versions.sdk.compile.get().toInt()
}
