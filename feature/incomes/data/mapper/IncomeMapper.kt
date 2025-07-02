package com.natan.shamilov.shmr25.feature.incomes.data.mapper

import com.natan.shamilov.shmr25.feature.expenses.data.model.TransactionDto
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income
import com.natan.shamilov.shmr25.feature.categories.data.mapper.CategoryMapper
import javax.inject.Inject

class IncomeMapper @Inject constructor(
    private val categoryMapper: CategoryMapper
) {
    fun mapTransactionDtoToIncome(dto: TransactionDto): Income = with(dto) {
        Income(
            id = id,
            category = categoryMapper.mapDtoToDomain(category),
            amount = amount.toDouble(),
            comment = comment,
            createdAt = transactionDate
        )
    }
} 