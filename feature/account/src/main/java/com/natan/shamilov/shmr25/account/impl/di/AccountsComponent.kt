package com.natan.shamilov.shmr25.account.impl.di

import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryScope
import com.natan.shamilov.shmr25.account.api.AccountDependencies
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactory
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryModule
import dagger.Component

@AccountsScope
@ViewModelFactoryScope
@Component(
    dependencies = [
        AccountDependencies::class
    ],
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        AccountsViewModelModule::class,
        ViewModelFactoryModule::class,
    ]
)
interface AccountsComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(deps: AccountDependencies): AccountsComponent
    }
}