package com.natan.shamilov.shmr25.domain.entity

data class Expense(
    val id: Int,
    val category: Category,
    val amount: Int,
    val comment: String = "",
    val createdAt: String
) {
}