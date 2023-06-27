@file:OptIn(ExperimentalMaterial3Api::class)

package eu.wewox.lazytable

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import eu.wewox.lazytable.screens.LazyTablePinnedScreen
import eu.wewox.lazytable.screens.LazyTableSimpleScreen
import eu.wewox.lazytable.screens.LazyTableStateScreen
import eu.wewox.lazytable.ui.components.TopBar
import eu.wewox.lazytable.ui.theme.LazyTableTheme
import eu.wewox.lazytable.ui.theme.SpacingMedium

@Composable
fun App(backHandler: @Composable (Example?, onBackPressed: () -> Unit) -> Unit = { _, _ -> }) {
    LazyTableTheme {
        var example by rememberSaveable { mutableStateOf<Example?>(null) }

        backHandler(example) {
            example = null
        }

        Crossfade(
            targetState = example,
            label = RootTransitionLabel
        ) { selected ->
            when (selected) {
                null -> RootScreen(onExampleClick = { example = it })
                Example.LazyTableSimple -> LazyTableSimpleScreen()
                Example.LazyTableState -> LazyTableStateScreen()
                Example.LazyTablePinned -> LazyTablePinnedScreen()
            }
        }
    }
}

@Composable
private fun RootScreen(onExampleClick: (Example) -> Unit) {
    Scaffold(
        topBar = { TopBar("Lazy Table Demo") }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(Example.values()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onExampleClick(it) }
                        .padding(SpacingMedium)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = it.label,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = it.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

private const val RootTransitionLabel = "Root cross-fade"
