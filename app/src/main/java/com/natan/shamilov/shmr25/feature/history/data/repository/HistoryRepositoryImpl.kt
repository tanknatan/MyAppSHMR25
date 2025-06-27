package com.natan.shamilov.shmr25.feature.history.data.repository

import com.natan.shamilov.shmr25.app.data.api.model.TransactionDto
import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.app.data.api.mapper.FinanceMapper
import com.natan.shamilov.shmr25.feature.account.domain.entity.Account
import com.natan.shamilov.shmr25.feature.account.domain.repository.AccountRepository
import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import com.natan.shamilov.shmr25.feature.history.data.api.HistoryApi
import com.natan.shamilov.shmr25.feature.history.domain.repository.HistoryRepository
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api: HistoryApi,
    private val accountRepository: AccountRepository,
    private val mapper: FinanceMapper
) : HistoryRepository {

    override suspend fun getExpensesByPeriod(startDate: String, endDate: String): Result<List<Expense>> {
        return try {
            val accounts = accountRepository.getAccountsList()
            val transactions = loadTransactionsForAccounts(accounts, startDate, endDate, api::getExpensesByPeriod)
            Result.Success(
                transactions
                    .filter { !it.category.isIncome }
                    .map { mapper.mapTransactionDtoToExpense(it) }
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getIncomesByPeriod(startDate: String, endDate: String): Result<List<Income>> {
        return try {
            val accounts = accountRepository.getAccountsList()
            val transactions = loadTransactionsForAccounts(accounts, startDate, endDate, api::getIncomesByPeriod)
            Result.Success(
                transactions
                    .filter { it.category.isIncome }
                    .map { mapper.mapTransactionDtoToIncome(it) }
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private suspend fun loadTransactionsForAccounts(
        accounts: List<Account>,
        startDate: String,
        endDate: String,
        apiCall: suspend (Int, String, String) -> List<TransactionDto>
    ): List<TransactionDto> = coroutineScope {
        accounts.map { account ->
            async {
                apiCall(account.id, startDate, endDate)
            }
        }.awaitAll().flatten()
    }
} 
