package com.natan.shamilov.shmr25.common.impl.di

import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.data.repository.BaseTransacrionsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class BaseTransactionsModule {
    @Binds
    @ViewModelFactoryScope
    abstract fun bindCategoriesProvider(
        impl: BaseTransacrionsRepositoryImpl,
    ): TransactionsProvider
}
