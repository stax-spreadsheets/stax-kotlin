package io.github.rebokdev.stax.dsl

import io.github.rebokdev.stax.format.Spreadsheet

fun spreadsheet(
    name: String,
    body: SpreadsheetDslScope.() -> Unit
): Spreadsheet {
    val result = Spreadsheet(
        name = name
    )

    body(
        SpreadsheetDslScope(
            name = name,
            addSheet = { result.addSheet(it) }
        )
    )

    return result
}