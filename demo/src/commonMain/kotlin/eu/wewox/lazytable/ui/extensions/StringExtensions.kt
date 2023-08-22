package eu.wewox.lazytable.ui.extensions

import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Formats a float value to a string with defined number of decimal numbers.
 */
fun Float.formatToDecimals(decimals: Int = 1): String {
    val integerDigits = this.toInt()
    val floatDigits = ((this - integerDigits) * 10f.pow(decimals)).roundToInt()
    return "$integerDigits.$floatDigits"
}
