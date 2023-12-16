import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass = "MainKt"
        }
    }

    sourceSets {
        jvmMain {
            dependencies {
                implementation(project(":Dsl"))
                implementation(project(":Hocon"))
                implementation(project(":CopyToClipboard"))
                implementation("com.soywiz.korlibs.korio:korio:4.0.10")
                implementation(project(":Excel"))
            }
        }
    }
}