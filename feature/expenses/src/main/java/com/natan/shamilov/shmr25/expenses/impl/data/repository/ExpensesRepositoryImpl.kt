package com.natan.shamilov.shmr25.expenses.impl.data.repository

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.data.model.TransactionDto
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.expenses.impl.domain.entity.Expense
import com.natan.shamilov.shmr25.expenses.impl.domain.repository.ExpensesRepository
import com.natan.shamilov.shmr25.feature.expenses.data.mapper.ExpenseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val api: TransactionsApi,
    private val accountProvider: AccountProvider,
    private val transactionsProvider: TransactionsProvider,
    private val mapper: ExpenseMapper,
) : ExpensesRepository {

    private val today = LocalDate.now().toString()
    private var todayExpensesList = emptyList<TransactionDto>()

    override suspend fun getExpensesList(): List<Expense> = withContext(Dispatchers.IO) {
        todayExpensesList
            .filter { !it.category.isIncome }
            .map { dto ->
                mapper.mapTransactionDtoToExpense(dto)
            }
    }

    override suspend fun loadTodayExpenses(): Result<Unit> =
        Result.execute {
            val accounts = accountProvider.getAccountsList()
            todayExpensesList = loadExpensesForAccounts(accounts, today, today)
        }

    override suspend fun loadExpensesByPeriod(
        startDate: String,
        endDate: String,
    ): Result<List<Expense>> = Result.execute {
        val accounts = accountProvider.getAccountsList()
        val expensesList = loadExpensesForAccounts(accounts, startDate, endDate)
            .filter { !it.category.isIncome }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        expensesList.map { dto ->
            mapper.mapTransactionDtoToExpense(dto)
        }.sortedByDescending { expense ->
            LocalDate.parse(
                expense.createdAt.substring(START_OF_DATA_IN_RESPONSE, END_OF_DATA_IN_RESPONSE),
                formatter
            )
        }
    }

    override suspend fun getExpenseById(id: Int): Expense? = withContext(Dispatchers.IO) {
        return@withContext try {
            val dto = api.getTransactionById(id)
            if (!dto.category.isIncome) mapper.mapTransactionDtoToExpense(dto) else null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteTransaction(transactionId: Int): Result<Unit> = Result.execute {
        api.deleteTransaction(transactionId)
    }

    override suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit> {
        return transactionsProvider.createTransaction(accountId, categoryId, amount, transactionDate, comment)
    }

    override suspend fun editTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit> {
        return transactionsProvider.editTransaction(
            transactionId,
            accountId,
            categoryId,
            amount,
            transactionDate,
            comment
        )
    }

    private suspend fun loadExpensesForAccounts(
        accounts: List<Account>,
        startDate: String,
        endDate: String,
    ): List<TransactionDto> = coroutineScope {
        accounts.map { account ->
            async {
                api.getTransactionsByAccountPeriod(
                    accountId = account.id,
                    startDate = startDate,
                    endDate = endDate
                )
            }
        }.awaitAll().flatten()
    }

    companion object {
        private const val START_OF_DATA_IN_RESPONSE = 0
        private const val END_OF_DATA_IN_RESPONSE = 10
    }
}
