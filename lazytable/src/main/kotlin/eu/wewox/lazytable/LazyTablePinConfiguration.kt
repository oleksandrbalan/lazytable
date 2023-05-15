package eu.wewox.lazytable

/**
 * The configuration of pinned columns and rows.
 * Columns and rows are pinned from the beginning, then they are always displayed when user scrolls
 * in lazy table.
 *
 * @property columns The count of pinned columns for given row.
 * @property rows The count of pinned rows for given column.
 */
public data class LazyTablePinConfiguration internal constructor(
    val columns: (row: Int) -> Int,
    val rows: (column: Int) -> Int,
)

/**
 * Creates configuration of pinned columns and rows.
 *
 * @param columns The count of pinned columns for given row.
 * @param rows The count of pinned rows for given column.
 */
public fun lazyTablePinConfiguration(
    columns: (row: Int) -> Int = { 0 },
    rows: (column: Int) -> Int = { 0 },
): LazyTablePinConfiguration = LazyTablePinConfiguration(columns, rows)
