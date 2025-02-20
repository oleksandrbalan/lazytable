package eu.wewox.lazytable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import eu.wewox.minabox.MinaBox
import eu.wewox.minabox.MinaBoxItem
import kotlin.math.max
import kotlin.math.min

/**
 * Lazy layout to display columns and rows of data on the two directional plane.
 * Items should be provided with [content] lambda.
 *
 * @param modifier The modifier instance for the root composable.
 * @param state The state which could be used to observe and change translation offset.
 * @param pinConfiguration The configuration of pinned columns and rows.
 * @param dimensions The dimensions of columns and rows.
 * @param contentPadding A padding around the whole content. This will add padding for the content
 * after it has been clipped, which is not possible via modifier param.
 * @param scrollDirection Determines which directions are allowed to scroll.
 * @param content The lambda block which describes the content. Inside this block you can use
 * [LazyTableScope.items] method to add items.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
public fun LazyTable(
    modifier: Modifier = Modifier,
    state: LazyTableState = rememberSaveableLazyTableState(),
    pinConfiguration: LazyTablePinConfiguration = LazyTableDefaults.pinConfiguration(),
    dimensions: LazyTableDimensions = LazyTableDefaults.dimensions(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    scrollDirection: LazyTableScrollDirection = LazyTableScrollDirection.BOTH,
    content: LazyTableScope.() -> Unit
) {
    val scope = LazyTableScopeImpl().apply(content)

    val density = LocalDensity.current
    val (columnsCount, rowsCount) = scope.getItemsDimensions()
    val dimensionsPx = dimensions.roundToPx(columnsCount, rowsCount, density)
    val tableHeight = remember { mutableStateOf(0f) }

    state.dimensions = dimensionsPx
    state.pinConfiguration = pinConfiguration

    MinaBox(
        state = state.minaBoxState,
        contentPadding = contentPadding,
        scrollDirection = scrollDirection.toMinaBoxScrollDirection(),
        modifier = modifier.onSizeChanged {
            tableHeight.value = it.height.toFloat()
        },
    ) {
        val minTableHeight = min(tableHeight.value, dimensionsPx.rowsSize.sum())

        scope.intervals.forEach { interval ->
            items(
                count = interval.size,
                layoutInfo = {
                    val info = interval.value.layoutInfo(it)
                    info.toMinaBoxItem(pinConfiguration, dimensionsPx, minTableHeight)
                },
                key = interval.value.key,
                contentType = interval.value.contentType,
                itemContent = {
                    // Show pinned cells with higher Z index to draw over others
                    val index = interval.value.layoutInfo(it).getZIndex(pinConfiguration)
                    Box(
                        propagateMinConstraints = true,
                        modifier = Modifier.zIndex(index),
                    ) {
                        interval.value.itemContent.invoke(it)
                    }
                },
            )
        }

        if (pinConfiguration.footer) {
            // Empty content to push the second to last row up above the footer.
            items(
                count = 1,
                layoutInfo = {
                    MinaBoxItem(
                        x = 0f,
                        y = dimensionsPx.rowsSize.sum() - dimensionsPx.rowsSize.last(),
                        width = dimensionsPx.columnsSize.last(),
                        height = dimensionsPx.rowsSize.last(),
                    )
                },
                itemContent = {
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyTableScopeImpl.getItemsDimensions(): Pair<Int, Int> {
    var columns = 0
    var rows = 0

    intervals.forEach { interval ->
        repeat(interval.size) {
            val info = interval.value.layoutInfo(it)
            columns = max(columns, info.column + info.columnsCount)
            rows = max(rows, info.row + info.rowsCount)
        }
    }

    return columns to rows
}

private fun LazyTableItem.getZIndex(pinConfiguration: LazyTablePinConfiguration): Float {
    val pinnedColumn = column < pinConfiguration.columns(row)
    val pinnedRow = row < pinConfiguration.rows(column)
    val pinnedFooter = rowsCount.minus(row) >= 1 && pinConfiguration.footer // Last row and configuration footer is set

    return if (pinnedColumn && pinnedRow) {
        ZIndexPinnedCorner
    } else if (pinnedColumn && pinnedFooter) {
        ZIndexPinnedFooter
    } else if (pinnedColumn || pinnedRow || pinnedFooter) {
        ZIndexPinnedLine
    } else {
        ZIndexItem
    }
}

/**
 * Contains the default values for [LazyTable].
 */
public object LazyTableDefaults {

    /**
     * The default dimensions of the lazy table.
     */
    public fun dimensions(): LazyTableDimensions =
        lazyTableDimensions(
            columnSize = { DefaultColumnSize },
            rowSize = { DefaultRowSize }
        )

    /**
     * The default configuration of pinned columns and rows.
     * By default nothing is pinned.
     */
    public fun pinConfiguration(): LazyTablePinConfiguration =
        lazyTablePinConfiguration(columns = 0, rows = 0)
}

private val DefaultColumnSize = 96.dp
private val DefaultRowSize = 48.dp

private const val ZIndexPinnedFooter = 3f
private const val ZIndexPinnedCorner = 2f
private const val ZIndexPinnedLine = 1f
private const val ZIndexItem = 0f
