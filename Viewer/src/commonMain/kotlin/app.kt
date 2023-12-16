import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import io.github.rebokdev.stax.format.jvm.deserializeSpreadsheet
import korlibs.io.file.std.localVfs
import kotlinx.coroutines.runBlocking

@Composable
fun app(
    onWindowTitleChange: (String) -> Unit,
) = MaterialTheme(
    colorScheme = darkColorScheme()
) {
    LaunchedEffect(AppManager.spreadsheet) {
        if (AppManager.spreadsheet == null) {
            onWindowTitleChange("dataview")
        } else {
            AppManager.spreadsheet?.let { spreadsheet ->
                onWindowTitleChange("dataview - ${spreadsheet.name}")
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedContent(
            targetState = AppManager.path
        ) {
            if (it.isEmpty()) {
                var filePickerVisible by rememberSaveable { mutableStateOf(false) }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            filePickerVisible = true
                        }
                    ) {
                        Text("Open a file")
                    }
                }

                FilePicker(
                    show = filePickerVisible,
                    onFileSelected = {
                        runBlocking {
                            filePickerVisible = false
                            it?.let {
                                AppManager.path = mutableListOf("spreadsheet-view")
                                AppManager.spreadsheet = localVfs(it.path).readString().deserializeSpreadsheet()
                            }
                        }
                    },
                    title = "Open a spreadsheet"
                )
            } else if (it == listOf("spreadsheet-view")) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    var currentlySelectedTab by rememberSaveable { mutableStateOf(0) }

                    Column {
                        Box(
                            modifier = Modifier.weight(1F, true)
                        ) {
                            AnimatedContent(
                                targetState = currentlySelectedTab to AppManager.spreadsheet!!.sheets[currentlySelectedTab],
                                transitionSpec = {
                                    if (targetState.first > initialState.first) {
                                        slideIn { objSize -> IntOffset(objSize.width, 0) } togetherWith
                                                slideOut { objSize -> IntOffset(-objSize.width, 0) }
                                    } else {
                                        slideIn { objSize -> IntOffset(-objSize.width, 0) } togetherWith
                                                slideOut { objSize -> IntOffset(objSize.width, 0) }
                                    }
                                }
                            ) { sheet ->
                                sheetView(sheet.second)
                            }
                        }

                        TabRow(
                            selectedTabIndex = currentlySelectedTab,
                            modifier = Modifier.border(
                                1.dp,
                                Color.White
                            )
                        ) {
                            AppManager.spreadsheet?.let { spreadsheet ->
                                spreadsheet.sheets.forEachIndexed { index, sheet ->
                                    Tab(
                                        selected = currentlySelectedTab == index,
                                        onClick = { currentlySelectedTab = index },
                                        text = {
                                            Text(sheet.name)
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = Icons.Outlined.Calculate,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}