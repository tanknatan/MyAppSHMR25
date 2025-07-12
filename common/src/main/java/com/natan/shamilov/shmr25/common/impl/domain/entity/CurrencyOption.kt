package com.natan.shamilov.shmr25.common.impl.domain.entity

data class CurrencyOption(val code: String, val name: String, val symbol: String)

val currencyOptions = listOf(
    com.natan.shamilov.shmr25.common.impl.domain.entity.CurrencyOption("RUB", "Российский рубль", "₽"),
    com.natan.shamilov.shmr25.common.impl.domain.entity.CurrencyOption("USD", "Американский доллар", "$"),
    com.natan.shamilov.shmr25.common.impl.domain.entity.CurrencyOption("EUR", "Евро", "€")
)
