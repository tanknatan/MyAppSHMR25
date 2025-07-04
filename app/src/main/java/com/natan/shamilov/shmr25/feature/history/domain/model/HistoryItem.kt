package com.natan.shamilov.shmr25.feature.history.domain.model

import com.natan.shamilov.shmr25.common.domain.entity.HistoryScreenEntity

/**
 * Элемент истории для отображения
 */
data class HistoryItem(
    override val id: Long,
    override val name: String,
    override val emoji: String,
    override val amount: Double,
    override val currency: String,
    override val comment: String = "",
    override val createdAt: String,
) : HistoryScreenEntity
