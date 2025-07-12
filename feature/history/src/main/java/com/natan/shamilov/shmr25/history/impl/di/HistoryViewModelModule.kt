package com.natan.shamilov.shmr25.history.impl.di

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.common.impl.di.ViewModelKey
import com.natan.shamilov.shmr25.history.impl.presentation.screen.editHistory.EditHistoryViewModel
import com.natan.shamilov.shmr25.history.impl.presentation.screen.history.HistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HistoryViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    fun bindHistoryViewModel(historyViewModel: HistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditHistoryViewModel::class)
    fun bindEditHistoryViewModel(historyViewModel: EditHistoryViewModel): ViewModel
}