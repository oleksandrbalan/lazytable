@file:OptIn(ExperimentalMaterial3Api::class)

package eu.wewox.lazytable.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eu.wewox.lazytable.Example
import eu.wewox.lazytable.LazyTable
import eu.wewox.lazytable.LazyTableDimensions
import eu.wewox.lazytable.LazyTableItem
import eu.wewox.lazytable.LazyTablePinConfiguration
import eu.wewox.lazytable.data.Pokemon
import eu.wewox.lazytable.data.pokemons
import eu.wewox.lazytable.lazyTableDimensions
import eu.wewox.lazytable.lazyTablePinConfiguration
import eu.wewox.lazytable.ui.components.AsyncImage
import eu.wewox.lazytable.ui.components.TopBar
import eu.wewox.lazytable.ui.theme.SpacingMedium
import java.util.Locale

/**
 * Example how to setup pinned columns and rows.
 */
@Composable
fun LazyTablePinnedScreen() {
    Scaffold(
        topBar = { TopBar(Example.LazyTablePinned.label) }
    ) { padding ->
        val columns = 11
        val pokemons = produceState(initialValue = emptyList<Pokemon>()) { value = pokemons() }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            var settings by remember { mutableStateOf(Settings()) }

            if (pokemons.value.isEmpty()) {
                CircularProgressIndicator()
            } else {
                Settings(
                    settings = settings,
                    onChange = { settings = it },
                )

                LazyTable(
                    pinConfiguration = pinConfiguration(settings),
                    dimensions = dimensions(settings),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = pokemons.value.size * columns,
                        layoutInfo = {
                            LazyTableItem(
                                column = it % columns,
                                row = it / columns + if (settings.showHeader) 1 else 0,
                            )
                        },
                    ) {
                        Cell(pokemon = pokemons.value[it / columns], column = it % columns)
                    }

                    if (settings.showHeader) {
                        items(
                            count = columns,
                            layoutInfo = {
                                LazyTableItem(
                                    column = it % columns,
                                    row = 0,
                                )
                            },
                        ) {
                            HeaderCell(column = it)
                        }
                    }
                }
            }
        }
    }
}

private fun dimensions(settings: Settings): LazyTableDimensions =
    lazyTableDimensions(
        columnSize = {
            when (it) {
                0 -> 148.dp
                1 -> 48.dp
                else -> 96.dp
            }
        },
        rowSize = {
            if (it == 0 && settings.showHeader) {
                32.dp
            } else {
                48.dp
            }
        }
    )

private fun pinConfiguration(settings: Settings): LazyTablePinConfiguration =
    lazyTablePinConfiguration(
        columns = when {
            settings.pinName && settings.pinImage -> 2
            settings.pinName -> 1
            else -> 0
        },
        rows = if (settings.showHeader) 1 else 0
    )

@Suppress("ComplexMethod")
@Composable
private fun Cell(
    pokemon: Pokemon,
    column: Int,
) {
    val content = when (column) {
        0 -> pokemon.name
        1 -> "" // Second column is reserved for an image
        2 -> pokemon.number
        3 -> String.format(Locale.getDefault(), "%.1f", pokemon.height)
        4 -> String.format(Locale.getDefault(), "%.1f", pokemon.weight)
        5 -> pokemon.stats.health.toString()
        6 -> pokemon.stats.attack.toString()
        7 -> pokemon.stats.defence.toString()
        8 -> pokemon.stats.specialAttack.toString()
        9 -> pokemon.stats.specialDefence.toString()
        10 -> pokemon.stats.speed.toString()
        else -> error("Unknown column index: $column")
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .border(Dp.Hairline, MaterialTheme.colorScheme.onSurface)
    ) {
        if (content.isNotEmpty()) {
            Text(text = content)
        }
        if (column == 1) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun HeaderCell(column: Int) {
    val content = when (column) {
        0 -> "Name"
        1 -> "Img"
        2 -> "Number"
        3 -> "Height (cm)"
        4 -> "Weight (kg)"
        5 -> "Health"
        6 -> "Attack"
        7 -> "Defence"
        8 -> "Sp. attack"
        9 -> "Sp. defence"
        10 -> "Speed"
        else -> error("")
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .border(Dp.Hairline, MaterialTheme.colorScheme.onPrimary)
    ) {
        Text(text = content)
    }
}

@Composable
private fun Settings(
    settings: Settings,
    onChange: (Settings) -> Unit,
) {
    @Composable
    fun SettingsRow(
        text: String,
        value: Boolean,
        onChange: (Boolean) -> Unit,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(SpacingMedium),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpacingMedium),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = value,
                onCheckedChange = onChange
            )
        }
    }

    SettingsRow(
        text = "Show header",
        value = settings.showHeader,
        onChange = { onChange(settings.copy(showHeader = it)) }
    )

    SettingsRow(
        text = "Pin name",
        value = settings.pinName,
        onChange = {
            val new = settings.copy(pinName = it, pinImage = if (!it) false else settings.pinImage)
            onChange(new)
        }
    )

    SettingsRow(
        text = "Pin image",
        value = settings.pinImage,
        onChange = {
            val new = settings.copy(pinName = if (it) true else settings.pinName, pinImage = it)
            onChange(new)
        }
    )
}

private data class Settings(
    val showHeader: Boolean = true,
    val pinName: Boolean = false,
    val pinImage: Boolean = false,
)
