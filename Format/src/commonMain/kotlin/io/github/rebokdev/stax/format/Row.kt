package io.github.rebokdev.stax.format

import kotlinx.serialization.Serializable

@Serializable
data class Row(
    val index: Int,
    val cells: MutableList<Cell> = mutableListOf()
)
