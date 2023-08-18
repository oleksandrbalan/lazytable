package eu.wewox.lazytable

/**
 * Enumeration of available demo examples.
 *
 * @param label Example name.
 * @param description Brief description.
 */
enum class Example(
    val label: String,
    val description: String,
) {
    LazyTableSimple(
        "Simple Lazy Table",
        "Basic lazy table usage"
    ),
    LazyTableState(
        "Lazy Table State",
        "Example how lazy table state could be used"
    ),
    LazyTablePinned(
        "Pinned Items In Lazy Table",
        "Example how to setup pinned columns and rows"
    ),
}
