package com.natan.shamilov.shmr25.splash.impl.di

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.common.impl.di.ViewModelKey
import com.natan.shamilov.shmr25.splash.impl.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SplashViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel
}
