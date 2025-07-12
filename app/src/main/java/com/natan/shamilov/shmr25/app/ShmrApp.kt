package com.natan.shamilov.shmr25.app

import android.app.Application
import android.content.Context
import com.natan.shamilov.shmr25.app.di.AppComponent
import com.natan.shamilov.shmr25.app.di.DaggerAppComponent

/**
 * Класс приложения.
 * Ответственность: Инициализация компонентов приложения и внедрения зависимостей через Dagger 2.
 */
class ShmrApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is ShmrApp -> appComponent
        else -> applicationContext.appComponent
    }