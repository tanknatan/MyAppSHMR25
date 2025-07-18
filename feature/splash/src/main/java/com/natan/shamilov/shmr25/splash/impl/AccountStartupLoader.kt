package com.natan.shamilov.shmr25.splash.impl

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import javax.inject.Inject

class AccountStartupLoader @Inject constructor(
    private val accountProvider: AccountProvider,
) {
    suspend fun loadAccounts(): Result<List<Account>> = accountProvider.loadAccounts()
}
