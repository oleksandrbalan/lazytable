plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.serialization)
    id("convention.jvm.toolchain")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "eu.wewox.lazytable"

    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "eu.wewox.lazytable"

        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()

        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":lazytable"))
    implementation(project(":demo"))

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.uitooling)
    implementation(libs.compose.uitoolingpreview)
    implementation(libs.androidx.activitycompose)
    implementation(libs.androidx.splashscreen)
    implementation(libs.ktor.android)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktx.serialization)
}
