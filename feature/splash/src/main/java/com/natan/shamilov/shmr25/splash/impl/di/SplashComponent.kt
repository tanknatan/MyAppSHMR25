package com.natan.shamilov.shmr25.splash.impl.di

import com.example.core.di.ViewModelFactoryScope
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactory
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryModule
import com.natan.shamilov.shmr25.splash.api.SplashDependencies
import dagger.Component

@SplashScope
@ViewModelFactoryScope
@Component(
    dependencies = [
        SplashDependencies::class
    ],
    modules = [
        ViewModelFactoryModule::class,
        SplashViewModelModule::class,
    ]
)
interface SplashComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(dependencies: SplashDependencies): SplashComponent
    }
}
