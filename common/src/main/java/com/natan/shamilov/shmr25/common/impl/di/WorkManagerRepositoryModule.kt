package com.natan.shamilov.shmr25.common.impl.di

import com.natan.shamilov.shmr25.common.impl.data.repository.WorkManagerRepositoryImpl
import com.natan.shamilov.shmr25.common.impl.domain.repository.WorkManagerRepository
import dagger.Binds
import dagger.Module

@Module
interface WorkManagerRepositoryModule {

    @ViewModelFactoryScope
    @Binds
    fun bindWorkManagerRepository(impl: WorkManagerRepositoryImpl): WorkManagerRepository
}