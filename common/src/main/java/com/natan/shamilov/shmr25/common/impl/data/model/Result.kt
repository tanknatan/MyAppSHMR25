package com.natan.shamilov.shmr25.common.impl.data.model

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
    data class Success<T>(val data: T) : com.natan.shamilov.shmr25.common.impl.data.model.Result<T>()

    /**
     * Представляет ошибку при выполнении операции
     */
    data class Error(val exception: Exception) : com.natan.shamilov.shmr25.common.impl.data.model.Result<Nothing>()

    /**
     * Представляет состояние загрузки
     */
    object Loading : com.natan.shamilov.shmr25.common.impl.data.model.Result<Nothing>()

    companion object {
        private const val MAX_RETRIES = 3
        private const val RETRY_DELAY_MS = 2000L
        private const val ERROR_CODE_500 = 500

        /**
         * Выполняет асинхронную операцию с автоматическими повторными попытками при ошибках сервера.
         * @param block лямбда-функция, выполняющая асинхронную операцию
         * @return Result<T> результат выполнения операции
         */
        suspend fun <T> execute(block: suspend () -> T): com.natan.shamilov.shmr25.common.impl.data.model.Result<T> {
            var retryCount = 0
            while (true) {
                try {
                    return com.natan.shamilov.shmr25.common.impl.data.model.Result.Success(block())
                } catch (e: Exception) {
                    if ((e is HttpException) && (e.code() == ERROR_CODE_500) && (retryCount < MAX_RETRIES)) {
                        retryCount++
                        delay(com.natan.shamilov.shmr25.common.impl.data.model.Result.Companion.RETRY_DELAY_MS)
                        continue
                    }
                    return com.natan.shamilov.shmr25.common.impl.data.model.Result.Error(e)
                }
            }
        }
    }
}
