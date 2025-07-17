package com.natan.shamilov.shmr25.common.impl.data.model

data class CreateTransactionRequestBody(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?
)