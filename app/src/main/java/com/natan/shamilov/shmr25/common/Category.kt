package com.natan.shamilov.shmr25.common

/**
 * Доменная модель категории.
 * Ответственность: Представление категории транзакции в доменном слое приложения.
 * Содержит основные атрибуты категории и используется для группировки транзакций.
 */
data class Category(
    val id: Long,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)
