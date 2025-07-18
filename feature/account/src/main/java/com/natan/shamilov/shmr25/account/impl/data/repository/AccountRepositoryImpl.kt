package com.natan.shamilov.shmr25.account.impl.data.repository

import com.natan.shamilov.shmr25.account.impl.domain.repository.AccountRepository
import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import javax.inject.Inject

/**
 * Реализация репозитория для работы со счетами.
 * Управляет созданием, редактированием, удалением и получением счетов.
 */
class AccountRepositoryImpl @Inject constructor(
    private val accountProvider: AccountProvider,
) : AccountRepository {

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
