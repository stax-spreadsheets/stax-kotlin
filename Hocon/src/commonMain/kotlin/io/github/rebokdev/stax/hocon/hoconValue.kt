package io.github.rebokdev.stax.hocon

fun hoconValue(value: Any): String = when(value) {
    is Int -> value.toString()
    is String -> "\"$value\""
    else -> TODO("No support for this type of objects yet: ${value::class.simpleName}")
}