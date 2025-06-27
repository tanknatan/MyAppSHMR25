package com.natan.shamilov.shmr25.presentation.feature.expenses.data.repository

import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.data.api.mapper.FinanceMapper
import com.natan.shamilov.shmr25.data.api.model.TransactionDto
import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.presentation.feature.account.domain.repository.AccountRepository
import com.natan.shamilov.shmr25.presentation.feature.expenses.data.api.ExpensesApi
import com.natan.shamilov.shmr25.presentation.feature.expenses.domain.repository.ExpensesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val api: ExpensesApi,
    private val accountRepository: AccountRepository,
    private val mapper: FinanceMapper
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

    override suspend fun loadTodayExpenses(): Result<Unit> = Result.execute {
        val accounts = accountRepository.getAccountsList()
        todayExpensesList = loadExpensesForAccounts(accounts, today, today)
    }

    override suspend fun loadExpensesByPeriod(
        startDate: String,
        endDate: String
    ): Result<List<Expense>> = Result.execute {
        val accounts = accountRepository.getAccountsList()
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
    }.also {
        _completeExpensesByPeriodLoadingFlow.value = when (it) {
            is Result.Success -> State.Content
            is Result.Error -> State.Error
            is Result.Loading -> State.Loading
        }
    }

    private suspend fun loadExpensesForAccounts(
        accounts: List<Account>,
        startDate: String,
        endDate: String
    ): List<TransactionDto> = coroutineScope {
        accounts.map { account ->
            async {
                api.getExpensesByPeriod(
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