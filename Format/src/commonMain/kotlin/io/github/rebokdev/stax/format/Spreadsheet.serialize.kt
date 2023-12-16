package io.github.rebokdev.stax.format

import io.github.rebokdev.stax.hocon.hocon

fun Spreadsheet.serialize(): String {
    val spreadsheet = this
    return hocon {
        sectionObject("spreadsheet") {
            value(
                key = "name",
                value = spreadsheet.name
            )

            newLine()

            spreadsheet.sheets.forEachIndexed { index, sheet ->
                sectionObject("sheet") {
                    value(
                        key = "index",
                        value = index
                    )

                    value(
                        key = "name",
                        value = sheet.name
                    )

                    newLine()

                    sheet.rows.sortedBy { it.index }.forEachIndexed { index, row ->
                        sectionObject("row") {
                            value("index", row.index)

                            newLine()

                            row.cells.sortedBy { it.index }.forEachIndexed { index, cell ->
                                sectionObject("cell") {
                                    value("index", cell.index)
                                    value("value", cell.data.value)
                                    value("type", cell.data.type.toString())
                                }

                                if (index != row.cells.size - 1) {
                                    newLine()
                                }
                            }
                        }

                        if (index != sheet.rows.size - 1) {
                            newLine()
                        }
                    }
                }

                if (index != spreadsheet.sheets.size - 1) {
                    newLine()
                }
            }
        }
    }
}