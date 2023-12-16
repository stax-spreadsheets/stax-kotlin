include(":Dsl")
include(":Format")
include(":Hocon")
include(":CopyToClipboard")
include(":Viewer")
include(":Excel")
include(":Example")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "stax"

