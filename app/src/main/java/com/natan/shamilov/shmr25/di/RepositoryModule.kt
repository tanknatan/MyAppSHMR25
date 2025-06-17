package com.natan.shamilov.shmr25.di


import com.natan.shamilov.shmr25.data.FinanceRepositoryImpl
import com.natan.shamilov.shmr25.domain.FinanceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(impl: FinanceRepositoryImpl): FinanceRepository
}