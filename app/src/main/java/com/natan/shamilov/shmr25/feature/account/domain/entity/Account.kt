package com.natan.shamilov.shmr25.feature.account.domain.entity

/**
 * Доменная модель банковского счета.
 * Ответственность: Представление бизнес-сущности счета с его основными атрибутами
 * в доменном слое приложения.
 *
 * @property id Уникальный идентификатор счета
 * @property name Название счета
 * @property balance Текущий баланс счета
 * @property currency Валюта счета
 */
data class Account(
    val id: Int,
    val name: String,
    val balance: Double,
    val currency: String
)
