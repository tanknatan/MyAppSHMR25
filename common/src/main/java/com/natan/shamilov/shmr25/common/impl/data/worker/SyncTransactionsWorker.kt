package com.natan.shamilov.shmr25.common.impl.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.natan.shamilov.shmr25.common.api.NetworkChekerProvider
import com.natan.shamilov.shmr25.common.api.SyncPreferencesProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.domain.repository.SyncPreferencesRepository.Companion.SYNC_ERROR
import com.natan.shamilov.shmr25.common.impl.domain.repository.SyncPreferencesRepository.Companion.SYNC_SUCCESS
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlin.Exception
import kotlin.Unit
import com.natan.shamilov.shmr25.common.impl.data.model.Result as MyResult

/**
 * WorkManager воркер для периодической синхронизации транзакций с сервером.
 * Выполняется в фоновом режиме при наличии интернет-соединения.
 */
class SyncTransactionsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val baseTransactionsRepository: TransactionsProvider,
    private val networkRepository: NetworkChekerProvider,
    private val syncPreferencesRepository: SyncPreferencesProvider
) : CoroutineWorker(context, workerParams) {

    val currentTimeMillis = System.currentTimeMillis()

    override suspend fun doWork(): Result {
        return try {
            // Проверяем наличие интернет-соединения
            val networkAvailable = networkRepository.getNetworkStatus().value

            if (!networkAvailable) {
                return Result.retry()
            }
            // Выполняем синхронизацию транзакций
            when (val syncResult = baseTransactionsRepository.syncTransactions()) {
                is MyResult.Success<Unit> -> {
                    syncPreferencesRepository.saveLastSyncInfo(currentTimeMillis, SYNC_SUCCESS)
                    Result.success()
                }

                is MyResult.Error -> {
                    // Определяем, стоит ли повторить попытку
                    val shouldRetry = when {
                        syncResult.exception.message?.contains("network", ignoreCase = true) == true -> true
                        syncResult.exception.message?.contains("timeout", ignoreCase = true) == true -> true
                        runAttemptCount < 3 -> true
                        else -> false
                    }

                    if (shouldRetry) {
                        Result.retry()
                    } else {
                        syncPreferencesRepository.saveLastSyncInfo(currentTimeMillis, SYNC_ERROR)
                        Result.failure()
                    }
                }

                is MyResult.Loading -> {
                    Result.retry()
                }
            }
        } catch (exception: Exception) {
            // Повторяем при неожиданных ошибках, но не более 3 раз
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                syncPreferencesRepository.saveLastSyncInfo(currentTimeMillis, SYNC_ERROR)
                Result.failure()
            }
        }
    }

    companion object {
        const val WORK_NAME = "sync_transactions_periodic"
    }

    @AssistedFactory
    interface Factory {
        fun create(context: Context, params: WorkerParameters): SyncTransactionsWorker
    }
}
