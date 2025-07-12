package com.natan.shamilov.shmr25.account.impl.di

import com.natan.shamilov.shmr25.account.impl.data.repository.AccountRepositoryImpl
import com.natan.shamilov.shmr25.account.impl.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module

/**
 * Модуль Hilt для предоставления репозиториев модуля счетов.
 * Связывает интерфейс репозитория счетов с его реализацией.
 */
@Module
interface RepositoryModule {

    @Binds
    @AccountsScope
    fun bindAccountsRepository(impl: AccountRepositoryImpl): AccountRepository
}