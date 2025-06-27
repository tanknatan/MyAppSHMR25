package com.natan.shamilov.shmr25.di

import com.natan.shamilov.shmr25.data.repositorys.FinanceRepositoryImpl
import com.natan.shamilov.shmr25.domain.FinanceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Singleton Binds]
    fun bindRepository(impl: FinanceRepositoryImpl): FinanceRepository
}
