package com.natan.shamilov.shmr25.common.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data Transfer Object (DTO) для категории.
 * Ответственность: Представление категории в формате для сериализации/десериализации
 * при обмене данными с API. Используется библиотека Moshi для JSON преобразований.
 *
 * @property id Уникальный идентификатор категории
 * @property name Название категории
 * @property emoji Эмодзи-иконка категории
 * @property isIncome Флаг, указывающий является ли категория доходной (true) или расходной (false)
 */
@JsonClass(generateAdapter = true)
data class CategoryDto(
    /** Уникальный идентификатор категории */
    @Json(name = "id") val id: Long,

    /** Название категории */
    @Json(name = "name") val name: String,

    /** Эмодзи-иконка категории */
    @Json(name = "emoji") val emoji: String,

    /** Флаг типа категории: true - доход, false - расход */
    @Json(name = "isIncome") val isIncome: Boolean
)
