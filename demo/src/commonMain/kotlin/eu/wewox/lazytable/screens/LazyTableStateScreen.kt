package eu.wewox.lazytable.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import eu.wewox.lazytable.Example
import eu.wewox.lazytable.LazyTable
import eu.wewox.lazytable.LazyTableItem
import eu.wewox.lazytable.data.createCells
import eu.wewox.lazytable.rememberLazyTableState
import eu.wewox.lazytable.ui.components.TopBar
import kotlinx.coroutines.launch

/**
 * Example how lazy table state could be used.
 */
@Composable
fun LazyTableStateScreen(
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = Example.LazyTableState.label,
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        val columns = 10
        val rows = 30
        val cells = remember { createCells(columns, rows) }

        val scope = rememberCoroutineScope()
        val state = rememberLazyTableState()

        LazyTable(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            items(
                items = cells,
                layoutInfo = { LazyTableItem(it.first, it.second) }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .border(Dp.Hairline, MaterialTheme.colorScheme.onSurface)
                        .clickable { scope.launch { state.animateToCell(it.first, it.second) } }
                ) {
                    Text(text = "$it")
                }
            }
        }
    }
}
