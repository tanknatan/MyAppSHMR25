package com.natan.shamilov.shmr25.common.presentation.utils

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

/**
 * Форматирует число с плавающей точкой в строку с разделителями разрядов и символом валюты.
 * Если число целое, отбрасывает десятичную часть.
 * @param currency символ валюты для добавления к сумме
 * @return отформатированная строка с суммой и валютой
 */
fun Double.toCurrencyString(currency: String): String {
    return if (this % 1 == 0.0) {
        "%,d".format(this.toLong()).replace(',', ' ') + " $currency"
    } else {
        "%,.2f".format(this).replace(',', ' ') + " $currency"
    }
}
