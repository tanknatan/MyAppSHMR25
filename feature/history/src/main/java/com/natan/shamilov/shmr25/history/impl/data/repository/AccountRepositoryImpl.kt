package com.natan.shamilov.shmr25.history.impl.data.repository

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.history.impl.domain.repository.AccountRepository
import javax.inject.Inject

/**
 * Реализация репозитория для работы со счетами.
 * Управляет созданием, редактированием, удалением и получением счетов.
 */
class AccountRepositoryImpl @Inject constructor(
    private val accountProvider: AccountProvider,
) : AccountRepository {

    override suspend fun getAccountsList(): List<Account> {
        return accountProvider.getAccountsList()
    }

    override suspend fun getSelectedAccount(): Account? {
        return accountProvider.getSelectedAccount()
    }

    override suspend fun setSelectedAccount(accountId: Int) {
        accountProvider.setSelectedAccount(accountId)
    }

    override suspend fun getAccountById(id: Int): Account? {
        return accountProvider.getAccountsList().firstOrNull { it.id == id }
    }
}
