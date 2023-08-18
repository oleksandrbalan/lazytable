plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
    id("convention.jvm.toolchain")
}

kotlin {
    androidTarget()
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":lazytable"))

                implementation(libs.ktor)
                implementation(libs.ktor.content.negotiation)
                implementation(libs.ktor.serialization.json)
                implementation(libs.ktx.serialization)
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
                implementation(libs.coil.compose)
                implementation(libs.ktor.android)
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
