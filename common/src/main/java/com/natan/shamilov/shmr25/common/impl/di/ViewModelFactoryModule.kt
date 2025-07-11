package com.natan.shamilov.shmr25.common.impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.di.ViewModelFactoryScope
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {

    @Binds
    @ViewModelFactoryScope
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
