package com.natan.shamilov.shmr25.common.impl.presentation.ui.theme

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.natan.shamilov.shmr25.common.impl.presentation.LocalAppLocale

@Composable
fun localizedString(@StringRes id: Int): String {
    val locale = LocalAppLocale.current
    val context = LocalContext.current
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    val localizedContext = context.createConfigurationContext(config)
    return localizedContext.getString(id)
}