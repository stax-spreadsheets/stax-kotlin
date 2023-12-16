import io.github.rebokdev.stax.dsl.spreadsheet
import io.github.rebokdev.stax.excel.writeExcelSpreadsheet
import korlibs.io.file.std.applicationDataVfs

suspend fun main() {
    val spd = spreadsheet("my spreadsheet") {
        sheet("introduction") {
            row {
                cell("Example spreadsheet made using Stax")

                columnBreak(2)

                cell("And generated using stax kotlin dsl")
            }

            rowBreak(3)

            row {
                cell("Hello world")
            }
        }

        sheet("numbers") {
            row {
                cells(
                    (1..10).toList()
                )
            }

            row {
                cells(
                    (20 downTo 11).toList()
                )
            }
        }
    }

    applicationDataVfs["output.xlsx"].writeExcelSpreadsheet(
        spd
    )
}