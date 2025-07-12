package com.natan.shamilov.shmr25.common.impl.data.api

import com.natan.shamilov.shmr25.common.impl.data.model.AccountDto
import retrofit2.http.GET

interface BaseAccountApi {
    @GET("accounts")
    suspend fun getAccountsList(): List<AccountDto>
}
