package com.natan.shamilov.shmr25.presentation.feature.history.data.repository

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.data.api.mapper.FinanceMapper
import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.entity.Income
import com.natan.shamilov.shmr25.presentation.feature.account.domain.repository.AccountRepository
import com.natan.shamilov.shmr25.presentation.feature.history.data.api.HistoryApi
import com.natan.shamilov.shmr25.presentation.feature.history.domain.repository.HistoryRepository
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
        apiCall: suspend (Int, String, String) -> List<com.natan.shamilov.shmr25.data.api.model.TransactionDto>
    ): List<com.natan.shamilov.shmr25.data.api.model.TransactionDto> = coroutineScope {
        accounts.map { account ->
            async {
                apiCall(account.id, startDate, endDate)
            }
        }.awaitAll().flatten()
    }
} 