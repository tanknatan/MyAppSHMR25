package com.natan.shamilov.shmr25.common.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account

interface AccountRepository {
    suspend fun getAccountsList(): List<Account>
    suspend fun loadAccountsList(): Result<Unit>
    fun getSelectedAccount(): Account?
    fun setSelectedAccount(accountId: Int)
}
