package eu.wewox.lazytable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import eu.wewox.minabox.MinaBoxState

/**
 * Creates a [LazyTableState] that is remembered across compositions.
 *
 * @param initialOffset The lambda to provide initial offset on the plane.
 * @return Instance of the [LazyTableState].
 */
@Composable
public fun rememberLazyTableState(
    initialOffset: LazyTablePositionProvider.() -> Offset = { Offset.Zero }
): LazyTableState {
    return remember { LazyTableState(initialOffset) }
}

/**
 * A state object that can be hoisted to control and observe scrolling.
 *
 * @property initialOffset The lambda to provide initial offset on the plane.
 */
@Stable
public class LazyTableState(
    internal val initialOffset: LazyTablePositionProvider.() -> Offset
) {
    internal lateinit var dimensions: LazyTablePxDimensions
    internal lateinit var pinConfiguration: LazyTablePinConfiguration

    private val translateX: Float get() = minaBoxState.translate?.x ?: 0f

    private val translateY: Float get() = minaBoxState.translate?.y ?: 0f

    /**
     * The underlying [MinaBoxState] used to this state.
     */
    public val minaBoxState: MinaBoxState = MinaBoxState(
        initialOffset = {
            initialOffset(LazyTablePositionProviderImpl(this, dimensions, pinConfiguration))
        }
    )

    /**
     * The position provider used to get items offsets.
     */
    public val positionProvider: LazyTablePositionProvider
        get() = LazyTablePositionProviderImpl(
            positionProvider = minaBoxState.positionProvider,
            dimensions = dimensions,
            pinConfiguration = pinConfiguration
        )

    /**
     * Animates current offset to the cell with [column] and [row].
     *
     * @param column The index of the column.
     * @param row The index of the row.
     * @param columnsCount The count of columns occupied by the item.
     * @param rowsCount The count of rows occupied by the item.
     * @param alignment The alignment to align item inside the [LazyTable].
     */
    public suspend fun animateToCell(
        column: Int,
        row: Int,
        columnsCount: Int = 1,
        rowsCount: Int = 1,
        alignment: Alignment = Alignment.Center,
    ) {
        val offset = getCellOffset(column, row, columnsCount, rowsCount, alignment)
        minaBoxState.animateTo(offset.x, offset.y)
    }

    /**
     * Snaps current offset to the cell with [column] and [row].
     *
     * @param column The index of the column.
     * @param row The index of the row.
     * @param columnsCount The count of columns occupied by the item.
     * @param rowsCount The count of rows occupied by the item.
     * @param alignment The alignment to align item inside the [LazyTable].
     */
    public suspend fun snapToCell(
        column: Int,
        row: Int,
        columnsCount: Int = 1,
        rowsCount: Int = 1,
        alignment: Alignment = Alignment.Center
    ) {
        val offset = getCellOffset(column, row, columnsCount, rowsCount, alignment)
        minaBoxState.snapTo(offset.x, offset.y)
    }

    private fun getCellOffset(
        column: Int,
        row: Int,
        columnsCount: Int,
        rowsCount: Int,
        alignment: Alignment
    ): Offset =
        positionProvider.getCellOffset(
            column = column,
            row = row,
            columnsCount = columnsCount,
            rowsCount = rowsCount,
            alignment = alignment,
            currentX = translateX,
            currentY = translateY,
        )
}
