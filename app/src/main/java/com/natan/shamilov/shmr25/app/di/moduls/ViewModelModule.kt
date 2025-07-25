package com.natan.shamilov.shmr25.app.di.moduls

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.app.presentation.viewModel.NetworkViewModel
import com.natan.shamilov.shmr25.app.presentation.viewModel.ThemeViewModel
import com.natan.shamilov.shmr25.common.impl.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NetworkViewModel::class)
    fun bindNetworkViewModel(networkViewModel: NetworkViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ThemeViewModel::class)
    fun bindThemeViewModel(themeViewModel: ThemeViewModel): ViewModel
}
