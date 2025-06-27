package com.natan.shamilov.shmr25.data.api.model

data class CreateAccountRequest(
    val name: String,
    val balance: String,
    val currency: String
)
