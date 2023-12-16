package io.github.rebokdev.stax.format

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
sealed class CellType {
    data object Integer: CellType()
    data object String: CellType()
    data class Custom(
        val className: kotlin.String? = null
    ): CellType() {
        constructor(clazz: KClass<*>) : this(clazz.qualifiedName)

        override fun toString(): kotlin.String = if (className != null) "Custom/${className}"
            else "Unknown"
    }

    companion object {
        inline fun <reified T> getCorrespondingCellType(): CellType = when (T::class) {
            Int::class -> Integer
            kotlin.String::class -> String
            else -> Custom(T::class)
        }
    }

    fun kotlin.String.getCellType(): CellType = when (this) {
        "String" -> String
        "Integer" -> Integer
        "Unknown" -> Custom()
        else -> {
            if (this.matches(Regex("^Custom/.*\$"))) {
                Custom(
                    this.split("/")[1]
                )
            } else {
                throw Exception("Type not found")
            }
        }
    }
}