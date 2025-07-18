package com.natan.shamilov.shmr25.common.impl.di

import com.natan.shamilov.shmr25.common.api.WorkManagerProvider
import com.natan.shamilov.shmr25.common.impl.data.repository.WorkManagerRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class WorkManagerModule {
    @Binds
    @ViewModelFactoryScope
    abstract fun bindWorkManagerProvider(
        impl: WorkManagerRepositoryImpl,
    ): WorkManagerProvider
}
