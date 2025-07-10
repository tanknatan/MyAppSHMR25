package com.natan.shamilov.shmr25.feature.account.data.repository

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.common.domain.entity.Account
import com.natan.shamilov.shmr25.feature.account.data.api.AccountApi
import com.natan.shamilov.shmr25.feature.account.data.model.AccountRequestBody
import com.natan.shamilov.shmr25.feature.account.domain.repository.AccountRepository
import javax.inject.Inject

/**
 * Реализация репозитория для работы со счетами.
 * Управляет созданием, редактированием, удалением и получением счетов.
 */
class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi,
    private val accountProvider: AccountProvider,
) : AccountRepository {

    override suspend fun createAccount(
        name: String,
        balance: String,
        currency: String
    ): Result<Unit> {
        val result = Result.execute {
            api.createAccount(
                AccountRequestBody(
                    name = name,
                    balance = balance,
                    currency = currency
                )
            )
        }
        if (result is Result.Success) {
            accountProvider.loadAccountsList()
            accountProvider.setSelectedAccount(accountProvider.accountsList.value.last().id)
        }
        return result
    }

    override suspend fun deleteAccount(id: Int): Result<Unit> {
        val result = Result.execute {
            api.deleteAccount(id)
        }
        return result
    }

    override suspend fun editAccount(
        accountId: Int,
        name: String,
        balance: String,
        currency: String
    ): Result<Unit> {
        val result = Result.execute {
            api.editAccount(
                accoutId = accountId,
                AccountRequestBody(
                    name = name,
                    balance = balance,
                    currency = currency
                )
            )
        }
        if (result is Result.Success) {
            accountProvider.loadAccountsList()
            accountProvider.setSelectedAccount(accountProvider.selectedAccount.value!!.id)
        }
        return result
    }

    override suspend fun getAccountsList(): List<Account> {
        return accountProvider.getAccountsList()
    }

    override suspend fun getSelectedAccount(): Account? {
        return accountProvider.getSelectedAccount()
    }

    override fun observeAccountsList() = accountProvider.accountsList
    override fun observeSelectedAccount() = accountProvider.selectedAccount
    override fun setSelectedAccount(accountId: Int) = accountProvider.setSelectedAccount(accountId)
}
