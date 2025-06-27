package com.natan.shamilov.shmr25.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryDto(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "emoji") val emoji: String,
    @Json(name = "isIncome") val isIncome: Boolean
)
