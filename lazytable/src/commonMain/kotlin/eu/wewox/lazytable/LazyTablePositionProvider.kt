package eu.wewox.lazytable

import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import eu.wewox.minabox.MinaBoxPositionProvider

/**
 * Interface to provide offset of the registered lazy table items.
 */
public interface LazyTablePositionProvider : MinaBoxPositionProvider {
    /**
     * Returns offset of the lazy table cell with [column] and [row].
     *
     * @param column The index of the column.
     * @param row The index of the row.
     * @param columnsCount The count of columns occupied by the item.
     * @param rowsCount The count of rows occupied by the item.
     * @param alignment The alignment to align item inside the [LazyTable].
     * @param currentX The current offset on the X axis.
     * @param currentY The current offset on the Y axis.
     * @return An item offset.
     */
    public fun getCellOffset(
        column: Int,
        row: Int,
        columnsCount: Int = 1,
        rowsCount: Int = 1,
        alignment: Alignment = Alignment.Center,
        currentX: Float = 0f,
        currentY: Float = 0f,
    ): Offset
}

/**
 * Implementation of the [LazyTablePositionProvider] with [MinaBoxPositionProvider].
 *
 * @property positionProvider The instance of the [MinaBoxPositionProvider].
 * @property dimensions The dimensions of the lazy table in pixels.
 * @property pinConfiguration The configuration of pinned columns and rows.
 */
internal class LazyTablePositionProviderImpl(
    private val positionProvider: MinaBoxPositionProvider,
    private val dimensions: LazyTablePxDimensions,
    private val pinConfiguration: LazyTablePinConfiguration,
) : LazyTablePositionProvider, MinaBoxPositionProvider by positionProvider {

    override fun getCellOffset(
        column: Int,
        row: Int,
        columnsCount: Int,
        rowsCount: Int,
        alignment: Alignment,
        currentX: Float,
        currentY: Float,
    ): Offset {
        val itemOffset = Offset(
            x = dimensions.sumOfColumns(0 until column),
            y = dimensions.sumOfRows(0 until row),
        )
        val itemSize = IntSize(
            width = dimensions.sumOfColumns(column until column + columnsCount).toInt(),
            height = dimensions.sumOfRows(row until row + rowsCount).toInt(),
        )

        val columnPinConfiguration = pinConfiguration.columns(row)
        val rowPinConfiguration = pinConfiguration.rows(column)

        val paddingStart = dimensions.sumOfColumns(0 until columnPinConfiguration)
        val paddingTop = dimensions.sumOfRows(0 until rowPinConfiguration)

        val lockedHorizontally = column < columnPinConfiguration
        val lockedVertically = row < rowPinConfiguration

        val offset = align(itemSize, alignment, paddingStart, paddingTop)

        return Offset(
            x = if (lockedHorizontally) currentX else (itemOffset.x - offset.x - paddingStart),
            y = if (lockedVertically) currentY else (itemOffset.y - offset.y - paddingTop),
        )
    }
}
