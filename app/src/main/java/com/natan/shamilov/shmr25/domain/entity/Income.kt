package com.natan.shamilov.shmr25.domain.entity

data class Income(
    val id: Long,
    val category: Category,
    val amount: Double,
    val comment: String = "",
    val createdAt: String
) {
}