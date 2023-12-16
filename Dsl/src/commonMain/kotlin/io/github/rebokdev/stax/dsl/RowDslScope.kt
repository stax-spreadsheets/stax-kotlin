package io.github.rebokdev.stax.dsl

import io.github.rebokdev.stax.format.Cell
import io.github.rebokdev.stax.format.CellData
import io.github.rebokdev.stax.format.CellType

class RowDslScope(
    val addCell: (Cell) -> Unit,
    var currentColumnIndex: Int = 0
) {
    inline fun <reified T> cell(
        value: T,
        index: Int = -1
    ) {
        addCell(
            Cell(
                index = if (index != -1) index
                else currentColumnIndex,
                data = CellData(
                    value = value.toString(),
                    type = CellType.getCorrespondingCellType<T>()
                )
            )
        )

        if (index == -1) {
            currentColumnIndex++
        } else {
            currentColumnIndex = index + 1
        }
    }

    fun columnBreak(amountOfColumns: Int) {
        currentColumnIndex += amountOfColumns
    }

    inline fun <reified T> cells(
        list: List<T>,
        startingIndex: Int = -1
    ) {
        if (startingIndex == -1) {
            list.forEach { cellData ->
                cell(
                    value = cellData
                )
            }
        } else {
            var cellAddCurrentColumnIndex = startingIndex
            list.forEach { cellData ->
                cell(
                    value = cellData,
                    index = cellAddCurrentColumnIndex
                )
                cellAddCurrentColumnIndex++
            }
        }
    }
}