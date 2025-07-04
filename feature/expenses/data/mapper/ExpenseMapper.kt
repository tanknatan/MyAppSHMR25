package com.natan.shamilov.shmr25.feature.expenses.data.mapper

import com.natan.shamilov.shmr25.feature.expenses.data.model.TransactionDto
import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import com.natan.shamilov.shmr25.feature.categories.data.mapper.CategoryMapper
import javax.inject.Inject

class ExpenseMapper @Inject constructor(
    private val categoryMapper: CategoryMapper
) {
    fun mapTransactionDtoToExpense(dto: TransactionDto): Expense = with(dto) {
        Expense(
            id = id,
            category = categoryMapper.mapDtoToDomain(category),
            amount = amount.toDouble(),
            comment = comment,
            createdAt = transactionDate
        )
    }
} 