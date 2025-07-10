package com.natan.shamilov.shmr25.feature.expenses.domain.entity

import com.natan.shamilov.shmr25.common.domain.entity.HistoryScreenEntity

/**
 * Доменная модель расхода.
 * Ответственность: Представление бизнес-сущности расхода с его основными атрибутами
 * в доменном слое приложения. Реализует интерфейс HistoryScreenEntity для возможности
 * отображения в истории операций.
 */
data class Expense(
    override val id: Long,
    override val name: String,
    override val emoji: String,
    override val amount: Double,
    override val currency: String,
    override val comment: String?,
    override val createdAt: String,
) : HistoryScreenEntity
