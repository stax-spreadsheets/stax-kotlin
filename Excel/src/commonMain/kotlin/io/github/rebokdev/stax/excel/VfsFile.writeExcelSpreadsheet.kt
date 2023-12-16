package io.github.rebokdev.stax.excel

import io.github.rebokdev.stax.format.Spreadsheet
import korlibs.io.file.VfsFile

suspend fun VfsFile.writeExcelSpreadsheet(spreadsheet: Spreadsheet) = this.writeBytes(spreadsheet.toExcel())