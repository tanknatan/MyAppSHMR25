package com.natan.shamilov.shmr25.domain.entity

data class Expense(
    val id: Long,
    val category: Category,
    val amount: Double,
    val comment: String = "",
    val createdAt: String
) {
}