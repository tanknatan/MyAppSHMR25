package com.natan.shamilov.shmr25.common.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import kotlinx.coroutines.flow.StateFlow

interface AccountRepository {
    /**
     * Создает новый счет
     * @param name название счета
     * @param balance начальный баланс
     * @param currency валюта счета
     * @return результат операции
     */
    suspend fun createAccount(name: String, balance: String, currency: String): Result<Unit>

    /**
     * Удаляет счет по идентификатору
     * @param id идентификатор счета
     * @return результат операции
     */
    suspend fun deleteAccount(id: Int): Result<Unit>

    suspend fun editAccount(
        accountId: Int,
        name: String,
        balance: String,
        currency: String,
    ): Result<Unit>

    suspend fun loadAccounts(): Result<List<Account>>
    suspend fun getAccountsList(): List<Account>
    suspend fun getAccountsFlow(): StateFlow<List<Account>>
    fun setSelectedAccount(accountId: Int)
    suspend fun getSelectedAccountFlow(): StateFlow<Account?>
    suspend fun getSelectedAccount(): Account?
}
