package com.natan.shamilov.shmr25.presentation.feature.account.data.api

import com.natan.shamilov.shmr25.data.api.model.AccountDto
import com.natan.shamilov.shmr25.data.api.model.CreateAccountRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountApi {
    @GET("accounts")
    suspend fun getAccountsList(): List<AccountDto>

    @POST("accounts")
    suspend fun createAccount(
        @Body requestBody: CreateAccountRequest
    )

    @DELETE("accounts")
    suspend fun deleteAccount(
        @Query("id") accountId: Int
    )

    companion object {
        const val BASE_URL = "https://shmr-finance.ru/api/v1/"
    }
} 