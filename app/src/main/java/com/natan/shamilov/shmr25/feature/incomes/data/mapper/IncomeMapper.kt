package com.natan.shamilov.shmr25.feature.incomes.data.mapper

import com.natan.shamilov.shmr25.common.data.model.TransactionDto
import com.natan.shamilov.shmr25.common.presentation.utils.convertCurrency
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income
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
            createdAt = transactionDate
        )
    }
}
