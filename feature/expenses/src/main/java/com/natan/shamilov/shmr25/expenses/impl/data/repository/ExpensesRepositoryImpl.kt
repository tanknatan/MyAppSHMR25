package com.natan.shamilov.shmr25.expenses.impl.data.repository

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.data.model.TransactionDto
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.feature.expenses.data.mapper.ExpenseMapper
import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import com.natan.shamilov.shmr25.feature.expenses.domain.repository.ExpensesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val api: TransactionsApi,
    private val accountProvider: AccountProvider,
    private val mapper: ExpenseMapper,
) : ExpensesRepository {

    private val today = LocalDate.now().toString()
    private var todayExpensesList = emptyList<TransactionDto>()

    private val _completeExpensesByPeriodLoadingFlow = MutableStateFlow<State>(State.Loading)
    override val completeExpensesByPeriodLoadingFlow = _completeExpensesByPeriodLoadingFlow.asStateFlow()

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
