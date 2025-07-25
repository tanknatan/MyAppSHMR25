package com.natan.shamilov.shmr25.option.impl.di

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.common.impl.di.ViewModelKey
import com.natan.shamilov.shmr25.option.impl.presentation.screen.aboutAppOption.AboutAppViewModel
import com.natan.shamilov.shmr25.option.impl.presentation.screen.languageOption.ChangeLocaleViewModel
import com.natan.shamilov.shmr25.option.impl.presentation.screen.mainColorOption.ChangeMainColorViewModel
import com.natan.shamilov.shmr25.option.impl.presentation.screen.mainOption.OptionsViewModel
import com.natan.shamilov.shmr25.option.impl.presentation.screen.pinCodeOption.PinCodeViewModel
import com.natan.shamilov.shmr25.option.impl.presentation.screen.syncOption.SyncViewModel
import com.natan.shamilov.shmr25.option.impl.presentation.screen.vibrationOption.VibrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OptionsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChangeMainColorViewModel::class)
    fun bindChangeMainColorViewModel(changeMainColorViewModel: ChangeMainColorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OptionsViewModel::class)
    fun bindOptionsViewModel(optionsViewModel: OptionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SyncViewModel::class)
    fun bindSyncViewModel(syncViewModel: SyncViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VibrationViewModel::class)
    fun bindVibrationViewModel(syncViewModel: VibrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangeLocaleViewModel::class)
    fun bindChangeLocaleViewModel(changeLocaleViewModel: ChangeLocaleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AboutAppViewModel::class)
    fun bindAboutAppViewModel(aboutAppViewModel: AboutAppViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PinCodeViewModel::class)
    fun bindPinCodeViewModel(pinCodeViewModel: PinCodeViewModel): ViewModel
}