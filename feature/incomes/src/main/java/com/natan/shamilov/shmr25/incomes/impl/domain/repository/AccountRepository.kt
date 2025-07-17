package com.natan.shamilov.shmr25.incomes.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account

/**
 * Интерфейс репозитория для управления счетами (создание, редактирование, удаление).
 */
interface AccountRepository {

    suspend fun getAccountsList(): List<Account>
    suspend fun getSelectedAccount(): Account?
    suspend fun setSelectedAccount(accountId: Int)
    suspend fun getAccountById(id: Int): Account?
}
