package com.natan.shamilov.shmr25.common.domain.entity

data class CurrencyOption(val code: String, val name: String, val symbol: String)

val currencyOptions = listOf(
    CurrencyOption("RUB", "Российский рубль", "₽"),
    CurrencyOption("USD", "Американский доллар", "$"),
    CurrencyOption("EUR", "Евро", "€")
)
