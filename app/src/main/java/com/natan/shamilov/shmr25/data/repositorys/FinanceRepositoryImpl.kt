package com.natan.shamilov.shmr25.data.repositorys


import com.natan.shamilov.shmr25.commo.State
import com.natan.shamilov.shmr25.data.network.model.CreateAccountRequest
import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.domain.entity.Category
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.entity.Income
import com.natan.shamilov.shmr25.data.mapper.FinanceMapper
import com.natan.shamilov.shmr25.data.api.FinanceApi
import com.natan.shamilov.shmr25.data.api.model.TransactionDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate
import javax.inject.Inject
import com.natan.shamilov.shmr25.data.api.Result
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FinanceRepositoryImpl @Inject constructor(
    private val api: FinanceApi,
    private val mapper: FinanceMapper
) : FinanceRepository {

    private val today = LocalDate.now().toString()
    private var accountsList = emptyList<Account>()
    private var todayTransactionsList = emptyList<TransactionDto>()

    private val _completeAccAndTransactionDataLoadingFlow = MutableStateFlow<State>(State.Loading)
    override val completeAccAndTransactionDataLoadingFlow = _completeAccAndTransactionDataLoadingFlow.asStateFlow()

    override fun getExpensesList(): List<Expense> {
        return todayTransactionsList.filter { !it.category.isIncome }
            .map { dto ->
                mapper.mapTransactionDtoToExpense(dto)
            }
    }

    override fun getIncomesList(): List<Income> {
        return todayTransactionsList.filter { it.category.isIncome }
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
        expensesList
    }

    override suspend fun loadIncomesByPeriod(
        startDate: String,
        endDate: String
    ): Result<List<Income>> = Result.execute {
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
        incomeList
    }

    override fun getAccountsList(): List<Account> = accountsList

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

//class FinanceRepositoryImpl @Inject constructor() : FinanceRepository {
//
//    override fun getExpensesList(): List<Expense> {
//        return listOf(
//            Expense(
//                id = 1,
//                category = Category(
//                    id = 1,
//                    name = "Шоппинг",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 100000,
//                createdAt = "25.05.2025"
//            ),
//            Expense(
//                id = 2,
//                category = Category(
//                    id = 1,
//                    name = "Шоппинг",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 100000,
//                createdAt = "25.05.2025",
//                comment = "Продукты",
//            ),
//            Expense(
//                id = 3,
//                category = Category(
//                    id = 1,
//                    name = "Шоппинг",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 100000,
//                createdAt = "25.05.2025"
//            ),
//            Expense(
//                id = 4,
//                category = Category(
//                    id = 1,
//                    name = "Шоппинг",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 100000,
//                createdAt = "25.05.2025"
//            ),
//            Expense(
//                id = 5,
//                category = Category(
//                    id = 1,
//                    name = "Шоппинг",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 100000,
//                createdAt = "25.05.2025"
//            ),
//            Expense(
//                id = 6,
//                category = Category(
//                    id = 1,
//                    name = "Шоппинг",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 100000,
//                createdAt = "25.05.2025",
//                comment = "Техника"
//            ),
//            Expense(
//                id = 7,
//                category = Category(
//                    id = 1,
//                    name = "Шоппинг",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 100000,
//                createdAt = "25.05.2025"
//            ),
//            Expense(
//                id = 8,
//                category = Category(
//                    id = 1,
//                    name = "Шоппинг",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 100000,
//                createdAt = "25.05.2025"
//            ),
//            Expense(
//                id = 9,
//                category = Category(
//                    id = 1,
//                    name = "Шоппинг",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 100000,
//                createdAt = "25.05.2025"
//            ),
//        )
//    }
//
//    override fun getIncomesList(): List<Income> {
//        return listOf(
//            Income(
//                id = 1,
//                category = Category(
//                    id = 1,
//                    name = "Зарплата",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 165000,
//            ),
//            Income(
//                id = 2,
//                category = Category(
//                    id = 1,
//                    name = "Зарплата",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 165000,
//            ),
//            Income(
//                id = 3,
//                category = Category(
//                    id = 1,
//                    name = "Зарплата",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 165000,
//            ),
//            Income(
//                id = 4,
//                category = Category(
//                    id = 1,
//                    name = "Зарплата",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 165000,
//            ),
//            Income(
//                id = 5,
//                category = Category(
//                    id = 1,
//                    name = "Зарплата",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 165000,
//            ),
//            Income(
//                id = 6,
//                category = Category(
//                    id = 1,
//                    name = "Зарплата",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 165000,
//            ),
//            Income(
//                id = 7,
//                category = Category(
//                    id = 1,
//                    name = "Зарплата",
//                    emoji = "\uD83D\uDCB0",
//                    isIncome = true
//                ),
//                amount = 165000,
//            ),
//
//
//            )
//    }
//
//    override fun getAccount(): List<Account> {
//        return listOf(
//            Account(
//                id = 1,
//                name = "Основной счет",
//                balance = 170000,
//                currency = "₽"
//            ),
//            Account(
//                id = 2,
//                name = "2",
//                balance = 170000,
//                currency = "₽"
//            ),
//            Account(
//                id = 3,
//                name = "3",
//                balance = 170000,
//                currency = "₽"
//            ),
//            Account(
//                id = 4,
//                name = "4",
//                balance = 170000,
//                currency = "₽"
//            ),
//            Account(
//                id = 5,
//                name = "5",
//                balance = 170000,
//                currency = "₽"
//            ),
//            Account(
//                id = 6,
//                name = "6",
//                balance = 170000,
//                currency = "₽"
//            ),
//            Account(
//                id = 7,
//                name = "7",
//                balance = 170000,
//                currency = "₽"
//            ),
//            Account(
//                id = 8,
//                name = "8",
//                balance = 170000,
//                currency = "₽"
//            ),
//        )
//    }
//
//    override fun getCategoriesList(): List<Category> {
//        return listOf(
//            Category(
//                id = 1,
//                name = "Аренда квартиры",
//                emoji = "\uD83D\uDCB0",
//                isIncome = true
//            ),
//            Category(
//                id = 2,
//                name = "Аренда квартиры",
//                emoji = "\uD83D\uDCB0",
//                isIncome = true
//            ),
//            Category(
//                id = 3,
//                name = "Аренда квартиры",
//                emoji = "\uD83D\uDCB0",
//                isIncome = true
//            ),
//            Category(
//                id = 4,
//                name = "Аренда квартиры",
//                emoji = "\uD83D\uDCB0",
//                isIncome = true
//            ),
//            Category(
//                id = 5,
//                name = "Аренда квартиры",
//                emoji = "\uD83D\uDCB0",
//                isIncome = true
//            ),
//            Category(
//                id = 6,
//                name = "Аренда квартиры",
//                emoji = "\uD83D\uDCB0",
//                isIncome = true
//            ),
//            Category(
//                id = 7,
//                name = "Аренда квартиры",
//                emoji = "\uD83D\uDCB0",
//                isIncome = true
//            ),
//            Category(
//                id = 8,
//                name = "Аренда квартиры",
//                emoji = "\uD83D\uDCB0",
//                isIncome = true
//            ),
//            Category(
//                id = 9,
//                name = "Аренда квартиры",
//                emoji = "\uD83D\uDCB0",
//                isIncome = true
//            ),
//            Category(
//                id = 10,
//                name = "Аренда квартиры",
//                emoji = "\uD83D\uDCB0",
//                isIncome = true
//            ),
//            Category(
//                id = 11,
//                name = "Аренда квартиры",
//                emoji = "\uD83D\uDCB0",
//                isIncome = true
//            ),
//        )
//    }
//}