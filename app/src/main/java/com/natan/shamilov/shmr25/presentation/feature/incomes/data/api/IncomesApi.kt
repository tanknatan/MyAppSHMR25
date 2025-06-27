package com.natan.shamilov.shmr25.presentation.feature.incomes.data.api

import com.natan.shamilov.shmr25.data.api.model.TransactionDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IncomesApi {
    @GET("transactions/account/{accountId}/period")
    suspend fun getIncomesByPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): List<TransactionDto>

    companion object {
        const val BASE_URL = "https://shmr-finance.ru/api/v1/"
    }
} 