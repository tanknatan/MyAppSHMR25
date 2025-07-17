package com.natan.shamilov.shmr25.history.impl.presentation.screen.analysis

data class CategoryStatUiModel(
    val categoryId: Int,
    val emoji: String,
    val categoryName: String,
    val amount: Double,
    val percent: Double,
)

data class AnalyticsUiModel(
    val totalAmount: Double,
    val categoryStats: List<CategoryStatUiModel>,
)
