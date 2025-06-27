package com.natan.shamilov.shmr25.presentation.feature.incomes.domain.entity

import com.natan.shamilov.shmr25.common.HistoryScreenEntity
import com.natan.shamilov.shmr25.presentation.feature.categories.domain.entity.Category

data class Income(
    override val id: Long,
    override val category: com.natan.shamilov.shmr25.domain.entity.Category,
    override val amount: Double,
    override val comment: String = "",
    override val createdAt: String
) : HistoryScreenEntity 