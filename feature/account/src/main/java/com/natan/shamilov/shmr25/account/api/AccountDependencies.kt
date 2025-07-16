package com.natan.shamilov.shmr25.account.api

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi
import retrofit2.Retrofit

interface AccountDependencies {

    val accountProvider: AccountProvider

    val retrofit: Retrofit
}
