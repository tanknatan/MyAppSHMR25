package com.natan.shamilov.shmr25.domain


import com.natan.shamilov.shmr25.commo.State
import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.domain.entity.Category
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.entity.Income
import com.natan.shamilov.shmr25.data.api.Result
import kotlinx.coroutines.flow.StateFlow

interface FinanceRepository {

    val completeAccAndTransactionDataLoadingFlow: StateFlow<State>

    fun getExpensesList(): List<Expense>

    fun getIncomesList(): List<Income>

    suspend fun loadTodayTransactions(): Result<Unit>

    suspend fun loadExpensesByPeriod(
        startDate: String,
        endDate: String
    ): Result<List<Expense>>

    suspend fun loadIncomesByPeriod(
        startDate: String,
        endDate: String
    ): Result<List<Income>>

    fun getAccountsList(): List<Account>

    suspend fun loadAccountsList(): Result<Unit>

    suspend fun createAccount(
        name: String,
        balance: String,
        currency: String
    ): Result<Unit>

    suspend fun deleteAccount(id: Int): Result<Unit>

    suspend fun getCategoriesList(): Result<List<Category>>
}