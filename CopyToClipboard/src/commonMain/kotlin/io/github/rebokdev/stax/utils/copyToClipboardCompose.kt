package io.github.rebokdev.stax.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

@Composable
internal fun copyToClipboardCompose(
    text: String,
    exit: () -> Unit
) = MaterialTheme(
    colorScheme = darkColorScheme()
) {
    val clipboardManager by rememberUpdatedState(LocalClipboardManager.current)

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    clipboardManager.setText(AnnotatedString(text))
                    exit()
                }
            ) {
                Text("copy")
            }
        }
    }
}