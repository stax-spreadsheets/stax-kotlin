package io.github.rebokdev.stax.format

import kotlinx.serialization.Serializable

@Serializable
data class Spreadsheet(
    val name: String,
    val sheets: MutableList<Sheet> = mutableListOf()
) {
    fun addSheet(sheet: Sheet) {
        sheets.add(sheet)
    }

    override fun toString(): String = this.serialize()

    fun optimize(): Spreadsheet = Spreadsheet(
        name = name,
        sheets = sheets.map {
            Sheet(
                name = it.name,
                rows = it.rows.filter { it.cells.isNotEmpty() }.toMutableList()
            )
        }.toMutableList()
    )
}