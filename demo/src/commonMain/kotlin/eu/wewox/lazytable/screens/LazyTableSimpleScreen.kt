package eu.wewox.lazytable.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import eu.wewox.lazytable.Example
import eu.wewox.lazytable.LazyTable
import eu.wewox.lazytable.LazyTableItem
import eu.wewox.lazytable.data.createCells
import eu.wewox.lazytable.ui.components.TopBar

/**
 * Basic lazy table usage.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyTableSimpleScreen() {
    Scaffold(
        topBar = { TopBar(Example.LazyTableSimple.label) }
    ) { padding ->
        val columns = 10
        val rows = 30
        val cells = remember { createCells(columns, rows) }

        LazyTable(
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
                ) {
                    Text(text = "$it")
                }
            }
        }
    }
}
