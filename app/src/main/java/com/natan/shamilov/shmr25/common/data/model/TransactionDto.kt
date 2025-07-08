package com.natan.shamilov.shmr25.common.data.model

import com.natan.shamilov.shmr25.feature.categories.data.model.CategoryDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * DTO модель для транзакции.
 * Ответственность: Представление данных транзакции в формате для сериализации/десериализации
 * при взаимодействии с API, включая связанные сущности счета и категории.
 */
@JsonClass(generateAdapter = true)
data class TransactionDto(
    @Json(name = "id") val id: Long,
    @Json(name = "account") val account: AccountDto,
    @Json(name = "category") val category: CategoryDto,
    @Json(name = "amount") val amount: String,
    @Json(name = "transactionDate") val transactionDate: String,
    @Json(name = "comment") val comment: String?,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "updatedAt") val updatedAt: String
)
