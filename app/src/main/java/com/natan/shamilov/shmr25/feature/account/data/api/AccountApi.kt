package com.natan.shamilov.shmr25.feature.account.data.api

import com.natan.shamilov.shmr25.app.data.api.model.AccountDto
import com.natan.shamilov.shmr25.app.data.api.model.CreateAccountRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Определяет контракт для взаимодействия с API счетов.
 * Ответственность: Описание доступных HTTP-эндпоинтов для операций со счетами
 * (получение списка, создание, удаление).
 */
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
}
