package com.natan.shamilov.shmr25.common.data.api

import retrofit2.http.GET
import com.natan.shamilov.shmr25.common.data.model.AccountDto

interface AccountApi {
    @GET("accounts")
    suspend fun getAccountsList(): List<AccountDto>
} 