plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.multiplatform)
	alias(libs.plugins.jetbrains.compose)
	alias(libs.plugins.mavenpublish)
	id("convention.jvm.toolchain")
}

kotlin {
	androidTarget()
	jvm()

	sourceSets {
		val commonMain by getting {
			dependencies {
				api(compose.runtime)
				api(compose.foundation)
				api(compose.material3)

				api(libs.minabox)
			}
		}

		val jvmMain by getting

		val androidMain by getting
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
		androidTarget {
			publishLibraryVariants("release", "debug")
		}
	}
	composeOptions {
		kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
	}
}
