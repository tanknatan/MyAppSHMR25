package com.natan.shamilov.shmr25.common.utils

/**
 * Функция расширения для конвертации кода валюты в символ валюты.
 * Преобразует трехбуквенный код валюты (например, RUB, USD, EUR)
 * в соответствующий символ валюты (₽, $, €).
 */
fun String.convertCurrency(): String {
    when (this) {
        "RUB" -> return "₽"
        "USD" -> return "$"
        "EUR" -> return "€"
        else -> return "₽"
    }
}
