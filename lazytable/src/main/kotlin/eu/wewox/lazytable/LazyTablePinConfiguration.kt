package eu.wewox.lazytable

/**
 * The configuration of pinned columns and rows.
 * Columns and rows are pinned from the beginning, then they are always displayed when user scrolls
 * in lazy table.
 *
 * @property columns The count of pinned columns.
 * @property rows The count of pinned rows.
 */
public data class LazyTablePinConfiguration internal constructor(
    val columns: Int,
    val rows: Int,
)

/**
 * Creates configuration of pinned columns and rows.
 *
 * @param columns The count of pinned columns.
 * @param rows The count of pinned rows.
 */
public fun lazyTablePinConfiguration(
    columns: Int = 0,
    rows: Int = 0,
): LazyTablePinConfiguration = LazyTablePinConfiguration(columns, rows)
