package eu.wewox.lazytable.ui.components

import androidx.compose.runtime.Composable

@Composable
actual fun AsyncImage(model: String, contentDescription: String?) {
    coil.compose.AsyncImage(
        model = model,
        contentDescription = contentDescription
    )
}
