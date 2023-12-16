plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

    sourceSets {
        jvmMain {
            dependencies {
                implementation(project(":Format"))
                implementation("org.apache.poi:poi:5.2.5")
                implementation("org.apache.poi:poi-ooxml:5.2.5")
            }
        }

        commonMain {
            dependencies {
                implementation("com.soywiz.korlibs.korio:korio:4.0.10")
            }
        }
    }
}