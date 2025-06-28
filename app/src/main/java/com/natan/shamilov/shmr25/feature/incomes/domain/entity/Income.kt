package com.natan.shamilov.shmr25.feature.incomes.domain.entity

import com.natan.shamilov.shmr25.common.Category
import com.natan.shamilov.shmr25.common.HistoryScreenEntity

/**
 * Доменная модель дохода.
 * Ответственность: Представление бизнес-сущности дохода с его основными атрибутами
 * в доменном слое приложения. Реализует интерфейс HistoryScreenEntity для возможности
 * отображения в истории операций.
 */
data class Income(
    override val id: Long,
    override val category: Category,
    override val amount: Double,
    override val comment: String = "",
    override val createdAt: String
) : HistoryScreenEntity
