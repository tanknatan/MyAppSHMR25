package com.natan.shamilov.shmr25.data.repositorys

import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.data.api.FinanceApi
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.data.api.model.TransactionDto
import com.natan.shamilov.shmr25.data.api.mapper.FinanceMapper
import com.natan.shamilov.shmr25.data.api.model.CreateAccountRequest
import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.domain.entity.Category
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.entity.Income
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class FinanceRepositoryImpl @Inject constructor(
    private val api: FinanceApi,
    private val mapper: FinanceMapper
) : FinanceRepository {

    private val today = LocalDate.now().toString()
    private var accountsList = emptyList<Account>()
    private var todayTransactionsList = emptyList<TransactionDto>()

    private val _completeAccAndTransactionDataLoadingFlow = MutableStateFlow<State>(State.Loading)
    override val completeAccAndTransactionDataLoadingFlow = _completeAccAndTransactionDataLoadingFlow.asStateFlow()

    private val _completeExpensesByPeriodLoadingFlow = MutableStateFlow<State>(State.Loading)
    override val completeExpensesByPeriodLoadingFlow = _completeExpensesByPeriodLoadingFlow.asStateFlow()

    override suspend fun getExpensesList(): List<Expense> = withContext(Dispatchers.IO) {
        todayTransactionsList.filter { !it.category.isIncome }
            .map { dto ->
                mapper.mapTransactionDtoToExpense(dto)
            }
    }

    override suspend fun getIncomesList(): List<Income> = withContext(Dispatchers.IO) {
        todayTransactionsList.filter { it.category.isIncome }
            .map { dto ->
                mapper.mapTransactionDtoToIncome(dto)
            }
    }

    override suspend fun loadTodayTransactions(): Result<Unit> = Result.execute {
        todayTransactionsList = coroutineScope {
            accountsList.map { account ->
                async {
                    api.getTransactionsByAccountPeriod(
                        accountId = account.id,
                        startDate = today,
                        endDate = today
                    )
                }
            }
        }.awaitAll().flatten()
    }.also {
        _completeAccAndTransactionDataLoadingFlow.value = when (it) {
            is Result.Success -> State.Content
            is Result.Error -> State.Error
            is Result.Loading -> State.Loading
        }
    }

    override suspend fun loadExpensesByPeriod(
        startDate: String,
        endDate: String
    ): Result<List<Expense>> = Result.execute {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val expensesList = coroutineScope {
            accountsList.map { account ->
                async {
                    api.getTransactionsByAccountPeriod(
                        accountId = account.id,
                        startDate = startDate,
                        endDate = endDate
                    ).filter { !it.category.isIncome }
                        .map { dto ->
                            mapper.mapTransactionDtoToExpense(dto)
                        }
                }
            }
        }.awaitAll().flatten()
        val sortedList = expensesList.sortedByDescending { expense ->
            LocalDate.parse(expense.createdAt.substring(0, 10), formatter)
        }
        sortedList
    }

    override suspend fun loadIncomesByPeriod(
        startDate: String,
        endDate: String
    ): Result<List<Income>> = Result.execute {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val incomeList = coroutineScope {
            accountsList.map { account ->
                async {
                    api.getTransactionsByAccountPeriod(
                        accountId = account.id,
                        startDate = startDate,
                        endDate = endDate
                    ).filter { it.category.isIncome }
                        .map { dto ->
                            mapper.mapTransactionDtoToIncome(dto)
                        }
                }
            }
        }.awaitAll().flatten()
        val sortedList = incomeList.sortedByDescending { income ->
            LocalDate.parse(income.createdAt.substring(0, 10), formatter)
        }
        sortedList
    }

    override suspend fun getAccountsList(): List<Account> = withContext(Dispatchers.IO) {
        accountsList
    }

    override suspend fun loadAccountsList() = Result.execute {
        accountsList = api.getAccountsList().map { dto ->
            mapper.mapAccountDtoToDomain(dto)
        }
    }.also {
        _completeAccAndTransactionDataLoadingFlow.value = when (it) {
            is Result.Success -> State.Content
            is Result.Error -> State.Error
            is Result.Loading -> State.Loading
        }
    }

    override suspend fun createAccount(
        name: String,
        balance: String,
        currency: String
    ): Result<Unit> {
        val result = Result.execute {
            api.createAccount(
                CreateAccountRequest(
                    name = name,
                    balance = balance,
                    currency = currency
                )
            )
        }
        loadAccountsList()
        return result
    }

    override suspend fun deleteAccount(id: Int): Result<Unit> {
        val result = Result.execute {
            api.deleteAccount(id)
        }
        loadAccountsList()
        return result
    }

    override suspend fun getCategoriesList(): Result<List<Category>> = Result.execute {
        api.getCategories().map { dto ->
            mapper.mapCategoryDtoToDomain(dto)
        }
    }
}
