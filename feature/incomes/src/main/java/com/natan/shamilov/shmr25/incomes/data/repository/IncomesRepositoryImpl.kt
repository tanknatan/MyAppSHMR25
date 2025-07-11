package com.natan.shamilov.shmr25.feature.incomes.data.repository

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.data.model.TransactionDto
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account

import com.natan.shamilov.shmr25.feature.incomes.data.mapper.IncomeMapper
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income
import com.natan.shamilov.shmr25.feature.incomes.domain.repository.IncomesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class IncomesRepositoryImpl @Inject constructor(
    private val api: TransactionsApi,
    private val accountProvider: AccountProvider,
    private val mapper: IncomeMapper,
) : IncomesRepository {

    private val today = LocalDate.now().toString()
    private var todayIncomesList = emptyList<TransactionDto>()
    private var todayIncomes: List<Income> = emptyList()

    override suspend fun getIncomesList(): List<Income> = withContext(Dispatchers.IO) {
        todayIncomes
    }

    override suspend fun loadTodayIncomes(): Result<Unit> = Result.execute {
        val accounts = accountProvider.getAccountsList()
        todayIncomesList = loadIncomesForAccounts(accounts, today, today)
    }

    override suspend fun loadIncomesByPeriod(
        startDate: String,
        endDate: String,
    ): Result<List<Income>> = Result.execute {
        val accounts = accountProvider.getAccountsList()
        val incomesList = loadIncomesForAccounts(accounts, startDate, endDate)
            .filter { it.category.isIncome }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val sortedIncomes = incomesList.map { dto ->
            mapper.mapTransactionDtoToIncome(dto)
        }.sortedByDescending { income ->
            LocalDate.parse(
                income.createdAt.substring(START_OF_DATA_IN_RESPONSE, END_OF_DATA_IN_RESPONSE),
                formatter
            )
        }
        todayIncomes = sortedIncomes
        sortedIncomes
    }

    private suspend fun loadIncomesForAccounts(
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
