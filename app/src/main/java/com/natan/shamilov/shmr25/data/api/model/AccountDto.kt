
package com.natan.shamilov.shmr25.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "balance") val balance: String,
    @Json(name = "currency") val currency: String
) 