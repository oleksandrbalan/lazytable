package eu.wewox.lazytable

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("Wasm Demo", canvasElementId = "canvas") {
        App()
    }
}
