package com.natan.shamilov.shmr25.common.impl.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateTransactionResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "accountId") val accountid: Int,
    @Json(name = "categoryId") val categoryId: Int,
    @Json(name = "amount") val amount: String,
    @Json(name = "comment") val comment: String?,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "updatedAt") val updatedAt: String
)
