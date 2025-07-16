package com.natan.shamilov.shmr25.account.impl.di

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.common.impl.di.ViewModelKey
import com.natan.shamilov.shmr25.feature.account.presentation.screen.accounts.AccountViewModel
import com.natan.shamilov.shmr25.account.impl.presentation.screen.addAccount.AddAccountViewModel
import com.natan.shamilov.shmr25.account.impl.presentation.screen.editAccount.EditAccountViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AccountsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    fun bindAccountsViewModel(accountsViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddAccountViewModel::class)
    fun bindAddAccountViewModel(addAccountViewModel: AddAccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditAccountViewModel::class)
    fun bindEditAccountViewModel(editAccountViewModel: EditAccountViewModel): ViewModel
}