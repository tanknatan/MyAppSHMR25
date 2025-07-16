package com.natan.shamilov.shmr25.expenses.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.expenses.impl.domain.entity.Expense

interface ExpensesRepository {

    suspend fun getExpensesList(): List<Expense>

    suspend fun loadTodayExpenses(): Result<Unit>

    suspend fun loadExpensesByPeriod(
        startDate: String,
        endDate: String,
    ): Result<List<Expense>>

    suspend fun getExpenseById(id: Int): Expense?

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
