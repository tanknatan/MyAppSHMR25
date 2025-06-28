package com.natan.shamilov.shmr25.feature.account.domain.repository

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.feature.account.domain.entity.Account

/**
 * Интерфейс репозитория для работы со счетами.
 * Ответственность: Определение контракта для операций со счетами в доменном слое,
 * включая получение списка счетов, создание и удаление счетов.
 */
interface AccountRepository {
    /**
     * Получает список всех счетов из локального кэша
     * @return список счетов
     */
    suspend fun getAccountsList(): List<Account>

    /**
     * Загружает список счетов с сервера
     * @return результат операции
     */
    suspend fun loadAccountsList(): Result<Unit>

    /**
     * Создает новый счет
     * @param name название счета
     * @param balance начальный баланс
     * @param currency валюта счета
     * @return результат операции
     */
    suspend fun createAccount(name: String, balance: String, currency: String): Result<Unit>

    /**
     * Удаляет счет по идентификатору
     * @param id идентификатор счета
     * @return результат операции
     */
    suspend fun deleteAccount(id: Int): Result<Unit>
}
