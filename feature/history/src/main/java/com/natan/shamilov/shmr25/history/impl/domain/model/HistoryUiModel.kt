package com.natan.shamilov.shmr25.feature.history.domain.model

import com.natan.shamilov.shmr25.history.impl.domain.model.HistoryItem

/**
 * Модель данных для отображения на UI
 */
data class HistoryUiModel(
    val items: List<HistoryItem>,
    val totalAmount: Double,
)
