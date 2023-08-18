package eu.wewox.lazytable.ui.components

import androidx.compose.runtime.Composable

/**
 * Shows the image from remote URL.
 *
 * @param model The image model, typically remote URL.
 * @param contentDescription The text used by accessibility to describe what this image represents.
 */
@Composable
expect fun AsyncImage(model: String, contentDescription: String?)
