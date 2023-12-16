import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.github.rebokdev.stax.format.Spreadsheet

object AppManager {
    var path by mutableStateOf(mutableListOf<Any>())

    var spreadsheet: Spreadsheet? by mutableStateOf(null)
}