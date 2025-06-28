package com.natan.shamilov.shmr25.feature.expenses.domain.entity

import com.natan.shamilov.shmr25.common.Category
import com.natan.shamilov.shmr25.common.HistoryScreenEntity

/**
 * Доменная модель расхода.
 * Ответственность: Представление бизнес-сущности расхода с его основными атрибутами
 * в доменном слое приложения. Реализует интерфейс HistoryScreenEntity для возможности
 * отображения в истории операций.
 *
 * @property id Уникальный идентификатор расхода
 * @property category Категория расхода
 * @property amount Сумма расхода
 * @property comment Комментарий к расходу (опционально)
 * @property createdAt Дата создания расхода
 */
data class Expense(
    override val id: Long,
    override val category: Category,
    override val amount: Double,
    override val comment: String = "",
    override val createdAt: String
) : HistoryScreenEntity
