package io.github.rebokdev.stax.format.jvm

fun String.removeRepeatingPrefix(prefix: Char): String {
    var result = this
    while (result.firstOrNull() == prefix) {
        if (result.isBlank()) return ""

        result = result.drop(1)
    }

    return result
}