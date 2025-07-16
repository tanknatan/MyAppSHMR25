package com.natan.shamilov.shmr25.common.api

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import kotlinx.coroutines.flow.StateFlow

interface AccountProvider {
    val accountsList: StateFlow<List<Account>>
    val selectedAccount: StateFlow<Account?>

    suspend fun getAccountsList(): List<Account>
    suspend fun loadAccountsList(): Result<Unit>
    fun getSelectedAccount(): Account?
    fun setSelectedAccount(accountId: Int)
}
