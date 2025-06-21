package com.natan.shamilov.shmr25.data.api

import com.natan.shamilov.shmr25.data.api.model.AccountDto
import com.natan.shamilov.shmr25.data.api.model.CategoryDto
import com.natan.shamilov.shmr25.data.api.model.TransactionDto
import com.natan.shamilov.shmr25.data.network.model.CreateAccountRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FinanceApi {
    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByAccountPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?,
        @Header("Authorization") token: String = "Bearer $API_TOKEN"
    ): List<TransactionDto>

    @GET("categories")
    suspend fun getCategories(
        @Header("Authorization") token: String = "Bearer $API_TOKEN"
    ): List<CategoryDto>

    @GET("accounts")
    suspend fun getAccountsList(
        @Header("Authorization") token: String = "Bearer $API_TOKEN"
    ): List<AccountDto>

    @POST("accounts")
    suspend fun createAccount(
        @Body requestBody: CreateAccountRequest,
        @Header("Authorization") token: String = "Bearer $API_TOKEN"
    )

    @DELETE("accounts")
    suspend fun deleteAccount(
        @Query("id") accountId: Int
    )

    companion object {
        const val BASE_URL = "https://shmr-finance.ru/api/v1/"
        const val API_TOKEN = "hoPAoTM98pQocWj0Ya1DaOR6"
    }
}