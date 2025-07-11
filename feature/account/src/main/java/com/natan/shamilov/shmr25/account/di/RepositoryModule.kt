package com.natan.shamilov.shmr25.account.di

import com.natan.shamilov.shmr25.account.data.repository.AccountRepositoryImpl
import com.natan.shamilov.shmr25.account.domain.repository.AccountRepository
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