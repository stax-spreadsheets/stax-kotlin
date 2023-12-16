import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.rebokdev.stax.format.Sheet

@Composable
fun sheetView(
    sheet: Sheet
) = Box(
    modifier = Modifier.fillMaxSize()
) {
    val cellCount by rememberUpdatedState(sheet.rows.maxOf { it.cells.maxOf { it.index } }+1)

    LazyVerticalGrid(
        columns = GridCells.Fixed(cellCount)
    ) {
        sheet.rows.forEach { row ->
            repeat(cellCount) { cellIndex ->
                item {
                    Box(
                        modifier = Modifier.widthIn(150.dp).heightIn(75.dp).border(1.dp, Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(row.cells.getOrNull(cellIndex)?.data?.value?: "empty")
                    }
                }
            }
        }
    }
}