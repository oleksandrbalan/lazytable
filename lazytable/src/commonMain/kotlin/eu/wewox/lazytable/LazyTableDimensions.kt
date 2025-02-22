package eu.wewox.lazytable

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

/**
 * The dimensions of [LazyTable].
 */
public sealed interface LazyTableDimensions {

    /**
     * Defines exact count of columns and rows, and their sizes (widths & heights).
     * Registered cells must be in this range.
     *
     * @property columnsSize The list of column sizes (widths).
     * @property rowsSize The list of row sizes (heights).
     */
    @ConsistentCopyVisibility
    public data class Exact internal constructor(
        public val columnsSize: List<Dp>,
        public val rowsSize: List<Dp>,
    ) : LazyTableDimensions

    /**
     * Defines the lambdas which provides sizes (widths & heights) for columns and rows.
     * The size of [LazyTable] is driven by registered items.
     *
     * @property columnSize The lambda to provide the size of column based on its index.
     * @property rowSize The lambda to provide the size of row based on its index.
     */
    @ConsistentCopyVisibility
    public data class Dynamic internal constructor(
        public val columnSize: (Int) -> Dp,
        public val rowSize: (Int) -> Dp,
    ) : LazyTableDimensions
}

/**
 * Creates dynamic lazy table dimensions.
 *
 * @param columnSize The size of each column.
 * @param rowSize The size of each row.
 * @see LazyTableDimensions.Dynamic
 * @return The dimensions of [LazyTable].
 */
public fun lazyTableDimensions(columnSize: Dp, rowSize: Dp): LazyTableDimensions =
    LazyTableDimensions.Dynamic({ columnSize }, { rowSize })

/**
 * Creates dynamic lazy table dimensions.
 *
 * @param columnSize The lambda to provide the size of column based on its index.
 * @param rowSize The lambda to provide the size of row based on its index.
 * @see LazyTableDimensions.Dynamic
 * @return The dimensions of [LazyTable].
 */
public fun lazyTableDimensions(columnSize: (Int) -> Dp, rowSize: (Int) -> Dp): LazyTableDimensions =
    LazyTableDimensions.Dynamic(columnSize, rowSize)

/**
 * Creates exact lazy table dimensions.
 *
 * @param columnsSize The list of column sizes (widths).
 * @param rowsSize The list of row sizes (heights).
 * @see LazyTableDimensions.Exact
 * @return The dimensions of [LazyTable].
 */
public fun lazyTableDimensions(columnsSize: List<Dp>, rowsSize: List<Dp>): LazyTableDimensions =
    LazyTableDimensions.Exact(columnsSize, rowsSize)

/**
 * Creates exact lazy table dimensions.
 *
 * @param columnsCount The count of columns.
 * @param rowsCount The count of rows.
 * @param columnSize The lambda to provide the size of column based on its index.
 * @param rowSize The lambda to provide the size of row based on its index.
 * @see LazyTableDimensions.Exact
 * @return The dimensions of [LazyTable].
 */
public fun lazyTableDimensions(
    columnsCount: Int,
    rowsCount: Int,
    columnSize: (Int) -> Dp,
    rowSize: (Int) -> Dp,
): LazyTableDimensions =
    LazyTableDimensions.Exact(List(columnsCount, columnSize), List(rowsCount, rowSize))

/**
 * Converts [LazyTableDimensions] to [LazyTablePxDimensions].
 */
internal fun LazyTableDimensions.roundToPx(
    columns: Int,
    rows: Int,
    density: Density
): LazyTablePxDimensions =
    with(density) {
        when (val dimensions = this@roundToPx) {
            is LazyTableDimensions.Exact -> {
                require(
                    dimensions.columnsSize.size >= columns && dimensions.rowsSize.size >= rows
                ) {
                    "Unable to get sizes for all columns and rows. " +
                        "Ensure that both columns count and rows count is equal or greater " +
                        "than number of columns and rows in the scope. " +
                        "Columns count: ${dimensions.columnsSize.size}, " +
                        "columns in items: $columns; " +
                        "rows count: ${dimensions.rowsSize.size}, " +
                        "rows in items: $rows."
                }

                LazyTablePxDimensions(
                    columnsSize = dimensions.columnsSize.map { it.toPx() },
                    rowsSize = dimensions.rowsSize.map { it.toPx() },
                )
            }

            is LazyTableDimensions.Dynamic -> {
                LazyTablePxDimensions(
                    columnsSize = List(columns) { dimensions.columnSize(it).toPx() },
                    rowsSize = List(rows) { dimensions.rowSize(it).toPx() },
                )
            }
        }
    }

/**
 * The dimensions of the lazy table in pixels.
 */
internal data class LazyTablePxDimensions(
    val columnsSize: List<Float>,
    val rowsSize: List<Float>,
) {
    fun sumOfColumns(range: IntRange): Float = columnsSize.sumOf(range)

    fun sumOfRows(range: IntRange): Float = rowsSize.sumOf(range)

    private fun List<Float>.sumOf(range: IntRange): Float =
        range.sumOf { get(it).toDouble() }.toFloat()
}
