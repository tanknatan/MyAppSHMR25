package com.natan.shamilov.shmr25.feature.account.data.repository

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.app.data.api.mapper.FinanceMapper
import com.natan.shamilov.shmr25.app.data.api.model.CreateAccountRequest
import com.natan.shamilov.shmr25.feature.account.data.api.AccountApi
import com.natan.shamilov.shmr25.feature.account.domain.entity.Account
import com.natan.shamilov.shmr25.feature.account.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi,
    private val mapper: FinanceMapper
) : AccountRepository {

    private var accountsList = emptyList<Account>()

    override suspend fun getAccountsList(): List<Account> = withContext(Dispatchers.IO) {
        accountsList
    }

    override suspend fun loadAccountsList() = Result.execute {
        accountsList = api.getAccountsList().map { dto ->
            mapper.mapAccountDtoToDomain(dto)
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
} 