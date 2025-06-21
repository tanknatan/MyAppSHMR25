package com.natan.shamilov.shmr25.data.mapper

import com.natan.shamilov.shmr25.data.api.model.AccountDto
import com.natan.shamilov.shmr25.data.api.model.CategoryDto
import com.natan.shamilov.shmr25.data.api.model.TransactionDto
import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.domain.entity.Category
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.entity.Income
import javax.inject.Inject

class FinanceMapper @Inject constructor() {

    fun mapAccountDtoToDomain(dto: AccountDto): Account = with(dto) {
        Account(
            id = id,
            name = name,
            balance = balance.toDouble(),
            currency = currency
        )
    }

    fun mapCategoryDtoToDomain(dto: CategoryDto): Category = with(dto) {
        Category(
            id = id,
            name = name,
            emoji = emoji,
            isIncome = isIncome
        )
    }

    fun mapTransactionDtoToExpense(dto: TransactionDto): Expense = with(dto) {
        Expense(
            id = id,
            category = mapCategoryDtoToDomain(category),
            amount = amount.toDouble(),
            comment = comment,
            createdAt = transactionDate.substring(0, 10)
        )
    }

    fun mapTransactionDtoToIncome(dto: TransactionDto): Income = with(dto) {
        Income(
            id = id,
            category = mapCategoryDtoToDomain(category),
            amount = amount.toDouble(),
            comment = comment,
            createdAt = transactionDate.substring(0, 10)
        )
    }
} 