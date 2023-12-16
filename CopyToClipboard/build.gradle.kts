plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm()

    sourceSets {
        jvmMain {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }

        commonMain {
            dependencies {
                implementation(compose.materialIconsExtended)
                implementation(compose.material3)
                implementation(project(":Format"))
            }
        }
    }
}