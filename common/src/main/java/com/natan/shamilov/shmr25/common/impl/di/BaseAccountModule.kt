package com.natan.shamilov.shmr25.common.impl.di

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.impl.data.repository.BaseAccountRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class BaseAccountModule {
    @Binds
    abstract fun bindAccountProvider(
        impl: BaseAccountRepositoryImpl,
    ): AccountProvider
}
