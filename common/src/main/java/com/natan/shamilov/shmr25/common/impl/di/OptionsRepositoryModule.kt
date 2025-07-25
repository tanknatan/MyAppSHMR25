package com.natan.shamilov.shmr25.common.impl.di

import com.natan.shamilov.shmr25.common.api.OptionsProvider
import com.natan.shamilov.shmr25.common.impl.data.repository.OptionsPreferencesRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface OptionsRepositoryModule {

    @ViewModelFactoryScope
    @Binds
    fun bindOptionsPreferencesRepository(
        impl: OptionsPreferencesRepositoryImpl,
    ): OptionsProvider
}
