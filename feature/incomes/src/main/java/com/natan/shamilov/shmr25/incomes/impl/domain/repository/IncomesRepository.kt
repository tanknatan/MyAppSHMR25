package com.natan.shamilov.shmr25.incomes.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.incomes.impl.domain.entity.Income

interface IncomesRepository {
    suspend fun getIncomesList(): List<Income>

    suspend fun loadTodayIncomes(): Result<Unit>

    suspend fun loadIncomesByPeriod(
        startDate: String,
        endDate: String,
    ): Result<List<Income>>

    suspend fun getIncomeById(id: Int): Income?

    suspend fun deleteTransaction(transactionId: Int): Result<Unit>

    suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit>

    suspend fun editTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit>
}
