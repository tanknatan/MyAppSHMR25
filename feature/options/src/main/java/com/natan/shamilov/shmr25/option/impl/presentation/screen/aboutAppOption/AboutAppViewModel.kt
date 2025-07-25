package com.natan.shamilov.shmr25.option.impl.presentation.screen.aboutAppOption

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.common.api.AppInfoProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AboutAppViewModel @Inject constructor(
    private val appInfoProvider: AppInfoProvider,
) : ViewModel() {

    private val _appInfo = MutableStateFlow(appInfoProvider.version)
    val appInfo = _appInfo.asStateFlow()
}