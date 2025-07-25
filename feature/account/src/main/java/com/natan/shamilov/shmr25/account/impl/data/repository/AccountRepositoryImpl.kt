package com.natan.shamilov.shmr25.account.impl.data.repository

import com.natan.shamilov.shmr25.account.impl.data.toDateAmountMapWithZeros
import com.natan.shamilov.shmr25.account.impl.domain.repository.AccountRepository
import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.presentation.utils.getLast31DaysUtcBounds
import javax.inject.Inject

/**
 * Реализация репозитория для работы со счетами.
 * Управляет созданием, редактированием, удалением и получением счетов.
 */
class AccountRepositoryImpl @Inject constructor(
    private val accountProvider: AccountProvider,
    private val transactionsProvider: TransactionsProvider,
) : AccountRepository {

    override suspend fun getIncomeExpenseMapsForLast31Days(): Pair<Map<String, Double>, Map<String, Double>> {
        val (start, end) = getLast31DaysUtcBounds()
        val accounts = accountProvider.getAccountsList()

        val incomesList = transactionsProvider.getHistoryByPeriod(
            accounts = accounts,
            startDate = start,
            endDate = end,
            isIncome = true
        )
        val expensesList = transactionsProvider.getHistoryByPeriod(
            accounts = accounts,
            startDate = start,
            endDate = end,
            isIncome = false
        )

        val incomesMap = when (incomesList) {
            is Result.Error -> emptyMap()
            is Result.Success -> incomesList.data.toDateAmountMapWithZeros()
            Result.Loading -> TODO()
        }
        val expensesMap = when (expensesList) {
            is Result.Error -> emptyMap()
            is Result.Success -> expensesList.data.toDateAmountMapWithZeros()
            Result.Loading -> TODO()
        }

        return Pair(incomesMap, expensesMap)
    }

    override suspend fun createAccount(
        name: String,
        balance: String,
        currency: String,
    ): Result<Unit> =
        accountProvider.createAccount(
            name = name,
            balance = balance,
            currency = currency
        )

    override suspend fun deleteAccount(id: Int): Result<Unit> = accountProvider.deleteAccount(id)

    override suspend fun editAccount(
        accountId: Int,
        name: String,
        balance: String,
        currency: String,
    ): Result<Unit> = accountProvider.editAccount(
        accountId = accountId,
        name = name,
        balance = balance,
        currency = currency
    )

    override suspend fun loadAccountsList(): Result<Unit> {
        return Result.execute {
            accountProvider.loadAccounts()
        }
    }

    override suspend fun getAccountsList(): List<Account> {
        return accountProvider.getAccountsList()
    }

    override suspend fun getSelectedAccount(): Account? {
        return accountProvider.getSelectedAccount()
    }

    override fun setSelectedAccount(accountId: Int) = accountProvider.setSelectedAccount(accountId)
}
