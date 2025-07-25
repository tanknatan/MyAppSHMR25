package com.natan.shamilov.shmr25.common.impl.di

import com.natan.shamilov.shmr25.common.api.HapticProvider
import com.natan.shamilov.shmr25.common.impl.data.haptic.HapticManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface HapticModule {

    @ViewModelFactoryScope
    @Binds
    fun bindHapticManager(impl: HapticManagerImpl): HapticProvider
}
