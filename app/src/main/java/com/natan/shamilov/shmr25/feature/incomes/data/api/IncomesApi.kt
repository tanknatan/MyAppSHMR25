package com.natan.shamilov.shmr25.feature.incomes.data.api

import com.natan.shamilov.shmr25.common.data.model.TransactionDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IncomesApi {
    @GET("transactions/account/{accountId}/period")
    suspend fun getIncomesByPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): List<TransactionDto>
}
