package io.github.rebokdev.stax.format

import kotlinx.serialization.Serializable

@Serializable
data class CellData(
    val value: String,
    val type: CellType
) {
    fun resolveKotlinTypeOrNull(): Any? = when (type) {
        is CellType.String -> value
        is CellType.Integer -> value.toIntOrNull()
        is CellType.Custom -> null
    }
}
