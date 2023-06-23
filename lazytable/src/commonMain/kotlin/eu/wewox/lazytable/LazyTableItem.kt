package eu.wewox.lazytable

import eu.wewox.minabox.MinaBoxItem

/**
 * The layout data for item inside lazy table.
 *
 * @property column The index of the column.
 * @property row The index of the row.
 * @property columnsCount The count of columns occupied by the item.
 * @property rowsCount The count of rows occupied by the item.
 */
public class LazyTableItem(
    public val column: Int,
    public val row: Int,
    public val columnsCount: Int = 1,
    public val rowsCount: Int = 1,
)

/**
 * Maps lazy table layout data to [MinaBoxItem]s.
 */
internal fun LazyTableItem.toMinaBoxItem(
    pinConfiguration: LazyTablePinConfiguration,
    dimensions: LazyTablePxDimensions
): MinaBoxItem =
    MinaBoxItem(
        x = dimensions.sumOfColumns(0 until column),
        y = dimensions.sumOfRows(0 until row),
        width = dimensions.sumOfColumns(column until column + columnsCount),
        height = dimensions.sumOfRows(row until row + rowsCount),
        lockHorizontally = column < pinConfiguration.columns(row),
        lockVertically = row < pinConfiguration.rows(column),
    )
