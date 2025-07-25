package com.natan.shamilov.shmr25.app.di

import com.natan.shamilov.shmr25.app.data.AppInfoReposiroryImpl
import com.natan.shamilov.shmr25.common.api.AppInfoProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppInfoModule {
    @Binds
    @Singleton
    abstract fun bindAppInfoProvider(
        impl: AppInfoReposiroryImpl,
    ): AppInfoProvider
}
