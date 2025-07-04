package com.natan.shamilov.shmr25.feature.expenses.data.mapper

import com.natan.shamilov.shmr25.common.data.model.TransactionDto
import com.natan.shamilov.shmr25.common.presentation.utils.convertCurrency
import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import javax.inject.Inject

/**
 * Маппер для преобразования транзакций в расходы.
 * Конвертирует сетевые DTO объекты транзакций в доменные модели расходов,
 * выполняя необходимые преобразования валют и форматирование данных.
 */
class ExpenseMapper @Inject constructor() {

    fun mapTransactionDtoToExpense(dto: TransactionDto): Expense = with(dto) {
        Expense(
            id = id,
            name = dto.category.name,
            emoji = dto.category.emoji,
            amount = amount.toDouble(),
            currency = dto.account.currency.convertCurrency(),
            comment = comment,
            createdAt = transactionDate
        )
    }
}
