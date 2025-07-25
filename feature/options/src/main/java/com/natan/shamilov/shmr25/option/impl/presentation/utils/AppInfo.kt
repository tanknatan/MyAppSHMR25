package com.natan.shamilov.shmr25.option.impl.presentation.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Context.getAppInfo(): String {
    val packageInfo = packageManager.getPackageInfo(packageName, 0)
    val lastUpdateTime = packageInfo.lastUpdateTime
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return formatter.format(Date(lastUpdateTime))
}