package io.github.rebokdev.stax.dsl

import io.github.rebokdev.stax.format.Row

@SheetDslMarker
class SheetDslScope(
    private val addRow: (Row) -> Unit
) {
    var currentRowIndex: Int = 0

    fun row(
        index: Int = -1,
        startsFromColumn: Int = 0,
        body: RowDslScope.() -> Unit
    ) {
        val row = Row(
            if (index == -1) {
                currentRowIndex++; currentRowIndex - 1
            } else index
        )

        body(
            RowDslScope(
                addCell = { row.cells.add(it) },
                currentColumnIndex = startsFromColumn
            )
        )

        addRow(row)
    }

    fun rowBreak(amountOfRows: Int) {
        currentRowIndex += amountOfRows
    }
}