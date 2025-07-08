package com.natan.shamilov.shmr25.app

import android.app.Application
import com.natan.shamilov.shmr25.app.di.AppComponent
import com.natan.shamilov.shmr25.app.di.ApplicationHolder
import com.natan.shamilov.shmr25.app.di.DaggerAppComponent

/**
 * Класс приложения.
 * Ответственность: Инициализация компонентов приложения и внедрения зависимостей через Dagger 2.
 */
class App : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        ApplicationHolder.application = this
    }
}
