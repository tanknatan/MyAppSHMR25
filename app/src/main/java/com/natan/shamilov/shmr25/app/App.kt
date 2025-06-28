package com.natan.shamilov.shmr25.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Класс приложения.
 * Ответственность: Инициализация компонентов приложения и внедрения зависимостей через Hilt.
 *
 * Аннотация [HiltAndroidApp] указывает, что это точка входа для Hilt,
 * которая генерирует все необходимые компоненты для внедрения зависимостей.
 */
@HiltAndroidApp
class App : Application()
