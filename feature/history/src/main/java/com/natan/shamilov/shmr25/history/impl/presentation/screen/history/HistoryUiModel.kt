package com.natan.shamilov.shmr25.history.impl.presentation.screen.history

import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction

/**
 * Модель данных для отображения на UI
 */
data class HistoryUiModel(
    val items: List<Transaction>,
    val totalAmount: Double,
)
