package eu.wewox.lazytable

/**
 * The configuration of pinned columns, rows, and footer.
 * Columns and rows are pinned from the beginning, then they are always displayed when user scrolls
 * in lazy table.
 *
 * @property columns The count of pinned columns for given row.
 * @property rows The count of pinned rows for given column.
 * @property footer The flag for showing a pinned footer.
 */
public data class LazyTablePinConfiguration internal constructor(
    val columns: (row: Int) -> Int,
    val rows: (column: Int) -> Int,
    val footer: Boolean,
)

/**
 * Creates configuration of pinned columns,rows, and footer.
 *
 * @param columns The count of pinned columns.
 * @param rows The count of pinned rows.
 * @param footer The flag for showing a pinned footer.
 */
public fun lazyTablePinConfiguration(
    columns: Int = 0,
    rows: Int = 0,
    footer: Boolean = false,
): LazyTablePinConfiguration = LazyTablePinConfiguration({ columns }, { rows }, footer)

/**
 * Creates configuration of pinned columns, rows, and footer.
 *
 * @param columns The count of pinned columns for given row.
 * @param rows The count of pinned rows for given column.
 * @param footer The flag for showing a pinned footer.
 */
public fun lazyTablePinConfiguration(
    columns: (row: Int) -> Int = { 0 },
    rows: (column: Int) -> Int = { 0 },
    footer: Boolean = false
): LazyTablePinConfiguration = LazyTablePinConfiguration(columns, rows, footer)
