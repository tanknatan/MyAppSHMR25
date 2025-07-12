package com.natan.shamilov.shmr25.history.impl.domain.model

import com.natan.shamilov.shmr25.common.impl.domain.entity.HistoryScreenEntity

/**
 * Элемент истории для отображения
 */
data class HistoryItem(
    override val id: Long,
    override val name: String,
    override val emoji: String,
    override val categoryId: Int,
    override val amount: Double,
    override val currency: String,
    override val comment: String?,
    override val createdAt: String,
    override val accountId: Int,
    override val isIncome: Boolean
) : HistoryScreenEntity
