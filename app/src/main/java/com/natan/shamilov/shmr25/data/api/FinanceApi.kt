package com.natan.shamilov.shmr25.data.api

import com.natan.shamilov.shmr25.data.api.model.AccountDto
import com.natan.shamilov.shmr25.data.api.model.CategoryDto
import com.natan.shamilov.shmr25.data.api.model.CreateAccountRequest
import com.natan.shamilov.shmr25.data.api.model.TransactionDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FinanceApi {
    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByAccountPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): List<TransactionDto>

    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>

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
