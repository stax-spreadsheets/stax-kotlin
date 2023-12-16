package io.github.rebokdev.stax.dsl

import io.github.rebokdev.stax.format.Sheet

@SpreadsheetDslMarker
class SpreadsheetDslScope(
    val name: String,
    private val addSheet: (Sheet) -> Unit
) {
    fun sheet(
        name: String,
        body: SheetDslScope.() -> Unit
    ) {
        val sheet = Sheet(name)
        body(
            SheetDslScope(
                addRow = {
                    sheet.rows.add(it)
                }
            )
        )
        addSheet(sheet)
    }
}