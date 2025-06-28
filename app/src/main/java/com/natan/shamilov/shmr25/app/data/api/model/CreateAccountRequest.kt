package com.natan.shamilov.shmr25.app.data.api.model

/**
 * Модель запроса для создания нового счета.
 * Ответственность: Представление данных, необходимых для создания нового счета
 * при отправке запроса к API.
 */
data class CreateAccountRequest(
    val name: String,
    val balance: String,
    val currency: String
)
