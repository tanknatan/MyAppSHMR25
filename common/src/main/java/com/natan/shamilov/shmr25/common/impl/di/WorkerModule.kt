package com.natan.shamilov.shmr25.common.impl.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.natan.shamilov.shmr25.common.impl.data.worker.DaggerWorkerFactory
import com.natan.shamilov.shmr25.common.impl.data.worker.SyncTransactionsWorker
import com.natan.shamilov.shmr25.common.impl.data.worker.SyncTransactionsWorkerFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

/**
 * Dagger модуль для настройки WorkManager с dependency injection.
 * Обеспечивает создание воркеров с внедрением зависимостей.
 */
@Module
abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(SyncTransactionsWorker::class)
    abstract fun bindSyncTransactionsWorker(factory: SyncTransactionsWorkerFactory): ChildWorkerFactory

    @Binds
    @ViewModelFactoryScope
    abstract fun bindWorkerFactory(factory: DaggerWorkerFactory): WorkerFactory
}

/**
 * Интерфейс для создания конкретных воркеров.
 */
interface ChildWorkerFactory {
    fun create(context: Context, params: WorkerParameters): ListenableWorker
}

/**
 * Аннотация для маппинга воркеров в Dagger Map.
 */
@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)
