package com.natan.shamilov.shmr25.common

import com.natan.shamilov.shmr25.domain.entity.Category

interface HistoryScreenEntity {
    val id: Long
    val category: Category
    val amount: Double
    val comment: String
    val createdAt: String
}
