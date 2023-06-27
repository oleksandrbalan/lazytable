package eu.wewox.lazytable.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * The reusable component for top bar.
 *
 * @param title The text to show in top bar.
 */
@Composable
expect fun TopBar(title: String, modifier: Modifier = Modifier)
