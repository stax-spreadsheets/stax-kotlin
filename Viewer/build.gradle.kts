import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass = "MainKt"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("com.darkrockstudios:mpfilepicker:3.1.0")
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation("com.soywiz.korlibs.korio:korio:4.0.10")
                implementation(project(":Format"))
            }
        }

        jvmMain {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}