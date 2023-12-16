package io.github.rebokdev.stax.excel

import io.github.rebokdev.stax.format.Spreadsheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.ByteArrayOutputStream

actual fun Spreadsheet.toExcel(): ByteArray {
    val outputStream = ByteArrayOutputStream()
    val excelSpreadsheet = XSSFWorkbook()
    this.sheets.forEach { sheet ->
        val excelSheet = excelSpreadsheet.createSheet(sheet.name)
        sheet.rows.forEach { row ->
            val excelRow = excelSheet.createRow(row.index)
            row.cells.forEach { cell ->
                val excelCell = excelRow.createCell(cell.index)
                val cellValueResolved = cell.data.resolveKotlinTypeOrNull()
                if (cellValueResolved is String) {
                    excelCell.setCellValue(cellValueResolved)
                } else if (cellValueResolved is Int) {
                    excelCell.setCellValue(cellValueResolved.toDouble())
                }
            }
        }

        repeat(sheet.rows.maxOf { it.cells.maxOf { it.index } } + 1) {
            excelSheet.autoSizeColumn(it)
        }
    }

    excelSpreadsheet.write(outputStream)
    return outputStream.toByteArray()
}