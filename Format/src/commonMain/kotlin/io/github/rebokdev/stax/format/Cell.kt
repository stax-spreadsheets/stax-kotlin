package io.github.rebokdev.stax.format

import kotlinx.serialization.Serializable

@Serializable
data class Cell(
    val index: Int,
    val data: CellData
) {
    fun readIntOrNull(): Int? = if (data.type == CellType.Integer) {
        data.value.toIntOrNull()
    } else null
}
