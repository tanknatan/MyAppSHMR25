package com.natan.shamilov.shmr25.incomes.impl.data.mapper

import com.natan.shamilov.shmr25.common.impl.data.model.TransactionDto
import com.natan.shamilov.shmr25.common.impl.presentation.utils.convertCurrency
import com.natan.shamilov.shmr25.incomes.impl.domain.entity.Income
import javax.inject.Inject

class IncomeMapper @Inject constructor() {
    fun mapTransactionDtoToIncome(dto: TransactionDto): Income = with(dto) {
        Income(
            id = id,
            name = dto.category.name,
            emoji = dto.category.emoji,
            amount = amount.toDouble(),
            currency = dto.account.currency.convertCurrency(),
            comment = comment,
            createdAt = transactionDate,
            categoryId = dto.category.id.toInt(),
            accountId = dto.account.id,
            isIncome = dto.category.isIncome
        )
    }
}
