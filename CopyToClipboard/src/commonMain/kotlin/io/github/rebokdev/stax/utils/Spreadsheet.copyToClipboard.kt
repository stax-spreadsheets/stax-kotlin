package io.github.rebokdev.stax.utils

import io.github.rebokdev.stax.format.Spreadsheet
import io.github.rebokdev.stax.format.serialize

suspend fun Spreadsheet.copyToClipboard() {
    copyToClipboardLogic(this.serialize())
}