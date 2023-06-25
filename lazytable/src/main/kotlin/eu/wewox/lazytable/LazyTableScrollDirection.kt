package eu.wewox.lazytable

import eu.wewox.minabox.MinaBoxScrollDirection

/**
 * Determines which directions are allowed to scroll.
 */
public enum class LazyTableScrollDirection {
    /**
     * Both horizontal and vertical scroll gestures are allowed.
     */
    BOTH,

    /**
     * Only horizontal scroll gestures are allowed, useful for LazyRow-ish layouts.
     */
    HORIZONTAL,

    /**
     * Only vertical scroll gestures are allowed, useful for LazyColumn-ish layouts.
     */
    VERTICAL
}

/**
 * Maps [LazyTableScrollDirection] to [MinaBoxScrollDirection].
 */
internal fun LazyTableScrollDirection.toMinaBoxScrollDirection(): MinaBoxScrollDirection =
    when (this) {
        LazyTableScrollDirection.BOTH -> MinaBoxScrollDirection.BOTH
        LazyTableScrollDirection.HORIZONTAL -> MinaBoxScrollDirection.HORIZONTAL
        LazyTableScrollDirection.VERTICAL -> MinaBoxScrollDirection.VERTICAL
    }
