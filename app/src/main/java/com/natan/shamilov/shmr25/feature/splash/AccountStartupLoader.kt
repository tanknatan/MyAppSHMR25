package com.natan.shamilov.shmr25.feature.splash

import com.natan.shamilov.shmr25.common.api.AccountProvider
import javax.inject.Inject

class AccountStartupLoader @Inject constructor(
    private val accountProvider: AccountProvider
) {
    suspend fun loadAccounts() = accountProvider.loadAccountsList()
}
