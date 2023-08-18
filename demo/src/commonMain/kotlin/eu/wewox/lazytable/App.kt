package eu.wewox.lazytable

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import eu.wewox.lazytable.screens.LazyTablePinnedScreen
import eu.wewox.lazytable.screens.LazyTableSimpleScreen
import eu.wewox.lazytable.screens.LazyTableStateScreen
import eu.wewox.lazytable.ui.theme.LazyTableTheme

@Composable
fun App() {
    var example by rememberSaveable { mutableStateOf<Example?>(null) }
    App(
        example = example,
        onChangeExample = { example = it },
    )
}

@Composable
fun App(
    example: Example?,
    onChangeExample: (Example?) -> Unit,
) {
    LazyTableTheme {
        val reset = { onChangeExample(null) }

        Crossfade(
            targetState = example,
            label = RootTransitionLabel
        ) { selected ->
            when (selected) {
                null -> RootScreen(onExampleClick = onChangeExample)
                Example.LazyTableSimple -> LazyTableSimpleScreen(reset)
                Example.LazyTableState -> LazyTableStateScreen(reset)
                Example.LazyTablePinned -> LazyTablePinnedScreen(reset)
            }
        }
    }
}

private const val RootTransitionLabel = "Root cross-fade"
