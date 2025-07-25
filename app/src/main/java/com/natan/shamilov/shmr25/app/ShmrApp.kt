package com.natan.shamilov.shmr25.app

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import com.natan.shamilov.shmr25.app.di.AppComponent
import com.natan.shamilov.shmr25.app.di.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Класс приложения.
 * Ответственность: Инициализация компонентов приложения и внедрения зависимостей через Dagger 2.
 */
class ShmrApp : Application() {
    lateinit var appComponent: AppComponent
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        initializeWorkManager()
        schedulePeriodicSync()
    }

    private fun initializeWorkManager() {
        try {
            val configuration = Configuration.Builder()
                .setWorkerFactory(appComponent.workerFactory())
                .setMinimumLoggingLevel(Log.DEBUG)
                .build()

            WorkManager.initialize(this, configuration)
            Log.d("FinanceApp", "WorkManager initialized successfully with DaggerWorkerFactory")
        } catch (e: Exception) {
            Log.e("FinanceApp", "Failed to initialize WorkManager", e)
        }
    }

    private fun schedulePeriodicSync() {
        applicationScope.launch {
            try {
                appComponent.workManagerProvider().schedulePeriodicSync()
                Log.d("FinanceApp", "Periodic sync scheduled successfully")
            } catch (e: Exception) {
                Log.e("FinanceApp", "Failed to schedule periodic sync", e)
            }
        }
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is ShmrApp -> appComponent
        else -> applicationContext.appComponent
    }