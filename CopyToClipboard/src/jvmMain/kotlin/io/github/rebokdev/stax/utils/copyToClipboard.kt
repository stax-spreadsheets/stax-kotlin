package io.github.rebokdev.stax.utils

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

internal actual suspend fun copyToClipboardLogic(text: String) = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Copy to clipboard"
    ) {
        copyToClipboardCompose(
            text = text,
            exit = ::exitApplication
        )
    }
}