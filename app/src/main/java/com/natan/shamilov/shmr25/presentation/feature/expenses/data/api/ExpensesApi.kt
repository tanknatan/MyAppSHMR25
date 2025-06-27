package com.natan.shamilov.shmr25.presentation.feature.expenses.data.api

import com.natan.shamilov.shmr25.data.api.model.TransactionDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExpensesApi {
    @GET("transactions/account/{accountId}/period")
    suspend fun getExpensesByPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): List<TransactionDto>

    companion object {
        const val BASE_URL = "https://shmr-finance.ru/api/v1/"
    }
} 