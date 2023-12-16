plugins {
    kotlin("multiplatform") version "1.9.21" apply false
    kotlin("plugin.serialization") version "1.9.21" apply false
    id("org.jetbrains.compose") version "1.5.11" apply false
    id("com.github.ben-manes.versions") version "0.50.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.4"
}

allprojects {
    repositories {
        mavenCentral()
    }

    apply(
        plugin = "io.gitlab.arturbosch.detekt"
    )

    group = "io.github.rebokdev"
    version = "1A"

    detekt {
        buildUponDefaultConfig = true
    }
}