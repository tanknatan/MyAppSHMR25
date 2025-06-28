package com.natan.shamilov.shmr25.app.data.api

import kotlinx.coroutines.delay
import retrofit2.HttpException

/**
 * Sealed класс для обработки результатов сетевых запросов.
 * Ответственность: Унифицированное представление результатов асинхронных операций,
 * включая успешное выполнение, ошибки и состояние загрузки.
 */
sealed class Result<out T> {
    /**
     * Представляет успешный результат операции с данными
     */
    data class Success<T>(val data: T) : Result<T>()

    /**
     * Представляет ошибку при выполнении операции
     */
    data class Error(val exception: Exception) : Result<Nothing>()

    /**
     * Представляет состояние загрузки
     */
    object Loading : Result<Nothing>()

    companion object {
        private const val MAX_RETRIES = 3
        private const val RETRY_DELAY_MS = 2000L
        private const val ERROR_CODE_500 = 500

        /**
         * Выполняет асинхронную операцию с автоматическими повторными попытками при ошибках сервера.
         * @param block лямбда-функция, выполняющая асинхронную операцию
         * @return Result<T> результат выполнения операции
         */
        suspend fun <T> execute(block: suspend () -> T): Result<T> {
            var retryCount = 0
            while (true) {
                try {
                    return Success(block())
                } catch (e: Exception) {
                    if ((e is HttpException) && (e.code() == ERROR_CODE_500) && (retryCount < MAX_RETRIES)) {
                        retryCount++
                        delay(RETRY_DELAY_MS)
                        continue
                    }
                    return Error(e)
                }
            }
        }
    }
}
