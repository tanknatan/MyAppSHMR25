package com.natan.shamilov.shmr25.app.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * DTO модель для счета.
 * Ответственность: Представление данных счета в формате для сериализации/десериализации
 * при взаимодействии с API.
 */
@JsonClass(generateAdapter = true)
data class AccountDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "balance") val balance: String,
    @Json(name = "currency") val currency: String
)
