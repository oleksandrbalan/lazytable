package eu.wewox.lazytable.data

/**
 * Creates data for lazy table.
 */
fun createCells(columns: Int = COLUMNS, rows: Int = ROWS): List<Pair<Int, Int>> =
    buildList {
        repeat(rows) { row ->
            repeat(columns) { column ->
                add(column to row)
            }
        }
    }

private const val COLUMNS = 10
private const val ROWS = 30
