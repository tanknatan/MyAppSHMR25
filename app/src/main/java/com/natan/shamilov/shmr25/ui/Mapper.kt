package com.natan.shamilov.shmr25.ui

fun String.toCurrencySymbol(): String {
    return when (this) {
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> ""
    }
}

fun Int.toCurrencyString(currency: String = "₽"): String {
    return "%,d".format(this).replace(',', ' ') + " $currency"
}

fun Double.toCurrencyString(currency: String = "₽"): String {
    return "%,.2f".format(this).replace(',', ' ') + " $currency"
}

