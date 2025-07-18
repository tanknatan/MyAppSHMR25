package com.natan.shamilov.shmr25.common.impl.data.worker

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.natan.shamilov.shmr25.common.impl.di.ChildWorkerFactory
import javax.inject.Inject

/**
 * Фабрика для создания воркеров с поддержкой Dagger DI.
 */
class DaggerWorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards ChildWorkerFactory>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        Log.d("DaggerWorkerFactory", "========================================")
        Log.d("DaggerWorkerFactory", "Trying to create worker: $workerClassName")
        Log.d("DaggerWorkerFactory", "Worker parameters: $workerParameters")
        Log.d("DaggerWorkerFactory", "Worker parameters ID: ${workerParameters.id}")
        Log.d("DaggerWorkerFactory", "Worker parameters tags: ${workerParameters.tags}")
        Log.d("DaggerWorkerFactory", "Available factories: ${workerFactories.keys}")
        Log.d("DaggerWorkerFactory", "Factory map size: ${workerFactories.size}")
        
        workerFactories.forEach { (key, value) ->
            Log.d("DaggerWorkerFactory", "Factory: $key -> $value")
        }
        
        val workerClass = try {
            Class.forName(workerClassName)
        } catch (e: ClassNotFoundException) {
            Log.e("DaggerWorkerFactory", "Worker class not found: $workerClassName", e)
            return null
        }
        
        Log.d("DaggerWorkerFactory", "Worker class loaded: $workerClass")
        
        val foundEntry = workerFactories.entries.find { (key, _) ->
            val isAssignable = key.isAssignableFrom(workerClass)
            Log.d("DaggerWorkerFactory", "Checking: $key.isAssignableFrom($workerClass) = $isAssignable")
            isAssignable
        }
        
        Log.d("DaggerWorkerFactory", "Found entry: $foundEntry")
        
        val factoryProvider = foundEntry?.value
        if (factoryProvider == null) {
            Log.e("DaggerWorkerFactory", "No factory found for worker: $workerClassName")
            Log.e("DaggerWorkerFactory", "Available factories again:")
            workerFactories.keys.forEach { key ->
                Log.e("DaggerWorkerFactory", "  - $key")
            }
            return null
        }

        Log.d("DaggerWorkerFactory", "Creating worker with factory: ${factoryProvider.javaClass.simpleName}")
        return try {
            val createdWorker = factoryProvider.create(appContext, workerParameters)
            Log.d("DaggerWorkerFactory", "Successfully created worker: $createdWorker")
            createdWorker
        } catch (e: Exception) {
            Log.e("DaggerWorkerFactory", "Failed to create worker", e)
            null
        }
    }
}