package ua.honchar.reddit.core.util.extensions

import kotlin.math.round

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0L

fun Double?.orZero() = this ?: 0.0

fun Float?.orZero() = this ?: 0.0

fun Double.thousandsWithK(): String {
    return if (this >= 1000.0) {
        val number = round((this / 1000) * 10) / 10
        "${number}k"
    } else this.toString()
}

fun Int.thousandsWithK(): String {
    return if (this >= 1000){
        if (this % 1000 == 0)
            "${this / 1000}k"
        else "${round(((this / 1000) * 10).toDouble()) / 10}k"
    } else this.toString()
}