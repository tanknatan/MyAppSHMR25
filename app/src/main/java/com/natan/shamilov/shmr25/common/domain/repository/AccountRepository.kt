package com.natan.shamilov.shmr25.common.domain.repository

import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.common.domain.entity.Account

interface AccountRepository {
    suspend fun getAccountsList(): List<Account>
    suspend fun loadAccountsList(): Result<Unit>
    fun getSelectedAccount(): Account?
    fun setSelectedAccount(accountId: Int)
}
