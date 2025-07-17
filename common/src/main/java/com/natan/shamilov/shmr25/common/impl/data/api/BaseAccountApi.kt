package com.natan.shamilov.shmr25.common.impl.data.api

import com.natan.shamilov.shmr25.common.impl.data.model.AccountDto
import com.natan.shamilov.shmr25.common.impl.data.model.AccountRequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BaseAccountApi {
    @GET("accounts")
    suspend fun getAccountsList(): List<AccountDto>
    @POST("accounts")
    suspend fun createAccount(
        @Body requestBody: AccountRequestBody
    )

    @DELETE("accounts")
    suspend fun deleteAccount(
        @Query("id") accountId: Int
    )

    @PUT("accounts/{id}")
    suspend fun editAccount(
        @Path("id") accoutId: Int,
        @Body requestBody: AccountRequestBody
    )
}
