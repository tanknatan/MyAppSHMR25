package com.natan.shamilov.shmr25.login.impl.di

import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactory
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryModule
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryScope
import com.natan.shamilov.shmr25.login.api.LoginDependencies
import dagger.Component

@LoginScope
@ViewModelFactoryScope
@Component(
    dependencies = [
        LoginDependencies::class
    ],
    modules = [
        ViewModelFactoryModule::class,
        LoginViewModelModule::class
    ]
)
interface LoginComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(deps: LoginDependencies): LoginComponent
    }
}
