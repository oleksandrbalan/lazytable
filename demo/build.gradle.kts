plugins {
	alias(libs.plugins.kotlin.multiplatform)
	alias(libs.plugins.jetbrains.compose)
	alias(libs.plugins.android.library)
	alias(libs.plugins.serialization)
}

kotlin {
	android()
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
	}
}

android {
	namespace = "eu.wewox.lazytable.demo"

	compileSdk = libs.versions.sdk.compile.get().toInt()

	compileOptions {
		sourceCompatibility = JavaVersion.toVersion(libs.versions.java.sourceCompatibility.get())
		targetCompatibility = JavaVersion.toVersion(libs.versions.java.targetCompatibility.get())
	}
}
