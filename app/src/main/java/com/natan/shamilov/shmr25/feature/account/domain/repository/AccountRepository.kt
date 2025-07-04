package com.natan.shamilov.shmr25.feature.account.domain.repository

import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.common.domain.entity.Account
import kotlinx.coroutines.flow.StateFlow

/**
 * Интерфейс репозитория для управления счетами (создание, редактирование, удаление).
 */
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
        currency: String
    ): Result<Unit>

    suspend fun getAccountsList(): List<Account>

    // Новые методы для реактивного подхода
    fun observeAccountsList(): StateFlow<List<Account>>
    fun observeSelectedAccount(): StateFlow<Account?>
    suspend fun getSelectedAccount(): Account?
    fun setSelectedAccount(accountId: Int)
}
