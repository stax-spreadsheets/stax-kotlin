package io.github.rebokdev.stax.format.jvm

import com.typesafe.config.ConfigException
import com.typesafe.config.ConfigFactory
import io.github.rebokdev.stax.format.*
import io.github.rebokdev.stax.format.CellType.String.getCellType
import kotlinx.coroutines.coroutineScope

suspend fun String.deserializeSpreadsheet(): Spreadsheet {
    val content = this

    return coroutineScope {
        val contentFirstPhase = content.split("\n").toMutableList()

        var currentSheetIndex = 0

        contentFirstPhase.mapIndexed { index, line ->
            index to line
        }.filter { it.second.removeRepeatingPrefix('\t').matches(Regex("^sheet \\{$")) }.forEach {
            contentFirstPhase[it.first] = it.second.replace("sheet", "sheet${currentSheetIndex}")
            currentSheetIndex++
        }

        var currentRowIndex = 0

        contentFirstPhase.mapIndexed { index, line ->
            index to line
        }.filter { it.second.removeRepeatingPrefix('\t').matches(Regex("^row \\{$")) }.forEach {
            contentFirstPhase[it.first] = it.second.replace("row", "row${currentRowIndex}")
            currentRowIndex++
        }

        var currentCellIndex = 0

        contentFirstPhase.mapIndexed { index, line ->
            index to line
        }.filter { it.second.removeRepeatingPrefix('\t').matches(Regex("^cell \\{$")) }.forEach {
            contentFirstPhase[it.first] = it.second.replace("cell", "cell${currentCellIndex}")
            currentCellIndex++
        }

        val contentSecondPhase = contentFirstPhase.joinToString(
            separator = "\n"
        )

        val contentThirdPhase = ConfigFactory.parseString(contentSecondPhase)

        try {
            val spreadsheet = contentThirdPhase.getObject("spreadsheet").toConfig()
            val spreadsheetName = spreadsheet.getString("name")
            val sheets: MutableList<Pair<Int, Sheet>> = mutableListOf()

            spreadsheet.root().keys.filter {
                it.matches(Regex("^sheet.*\$"))
            }.let {
                it.forEach { sheetKey ->
                    val sheet = spreadsheet.getObject(sheetKey).toConfig()
                    val sheetName = sheet.getString("name")
                    val sheetIndex = sheet.getInt("index")
                    val rows: MutableList<Row> = mutableListOf()

                    sheet.root().keys.filter {
                        it.matches(Regex("^row.*\$"))
                    }.forEach { rowKey ->
                        val row = sheet.getObject(rowKey).toConfig()

                        rows.add(
                            Row(
                                index = row.getInt("index"),
                                cells = row.root().keys.filter { it.matches(Regex("^cell.*\$")) }.map { cellKey ->
                                    val cell = row.getObject(cellKey).toConfig()

                                    Cell(
                                        index = cell.getInt("index"),
                                        data = CellData(
                                            value = cell.getString("value"),
                                            type = cell.getString("type").getCellType()
                                        )
                                    )
                                }.sortedBy { it.index }.toMutableList()
                            )
                        )
                    }

                    sheets.add(
                        sheetIndex to Sheet(
                            name = sheetName,
                            rows = rows.sortedBy { it.index }.toMutableList()
                        )
                    )
                }
            }

            return@coroutineScope Spreadsheet(
                name = spreadsheetName,
                sheets = sheets.sortedBy { it.first }.map { it.second }.toMutableList()
            )
        } catch (e: ConfigException.Missing) {
            throw Exception("Spreadsheet's main file is missing arguments.")
        } catch (e: ConfigException.WrongType) {
            throw Exception("Spreadsheet main file's structure is invalid.")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        throw Exception("Unknown exception, please report the issue")
    }
}