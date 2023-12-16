package io.github.rebokdev.stax.format

import kotlinx.serialization.Serializable

@Serializable
data class Sheet(
    val name: String,
    val rows: MutableList<Row> = mutableListOf()
)
