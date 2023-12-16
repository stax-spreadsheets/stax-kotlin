package io.github.rebokdev.stax.hocon

sealed class HoconObject {
    data class SectionObject(
        val name: String,
        val contains: List<HoconObject>
    ): HoconObject()

    data class Value(
        val key: String,
        val value: String
    ): HoconObject()

    data object NewLine: HoconObject()
}