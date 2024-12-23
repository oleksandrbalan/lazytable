import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "lazytable-demo"
        browser {
            commonWebpackConfig {
                outputFileName = "main.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    port = 8080
                    static = (static ?: mutableListOf()).apply {
                        add(project.rootDir.path)
                    }
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val wasmJsMain by getting {
            dependencies {
                implementation(project(":demo"))
                implementation(compose.ui)
            }
        }
    }
}

compose {
    experimental.web.application {}
}
