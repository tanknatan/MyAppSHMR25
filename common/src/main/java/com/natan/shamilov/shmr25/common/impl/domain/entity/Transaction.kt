package com.natan.shamilov.shmr25.common.impl.domain.entity

/**
 * Базовый интерфейс для всех сущностей, которые могут быть отображены на экране истории.
 * Ответственность: Определение общего контракта для транзакций (доходов и расходов),
 * которые должны отображаться в истории операций.
 */
data class Transaction(
    val id: Long,
    val accountId: Int,
    val categoryId: Int,
    val name: String,
    val emoji: String,
    val amount: Double,
    val currency: String,
    val comment: String?,
    val createdAt: String,
    val isIncome: Boolean,
)
