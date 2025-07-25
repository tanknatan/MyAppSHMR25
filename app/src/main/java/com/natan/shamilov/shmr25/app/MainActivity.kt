package com.natan.shamilov.shmr25.app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.natan.shamilov.shmr25.app.network.NetworkEvent
import com.natan.shamilov.shmr25.app.presentation.navigation.AppGraph
import com.natan.shamilov.shmr25.app.presentation.viewModel.NetworkViewModel
import com.natan.shamilov.shmr25.app.presentation.viewModel.ThemeViewModel
import com.natan.shamilov.shmr25.common.impl.presentation.LocalAppLocale
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.MyAppSHMR25Theme
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

/**
 * Главная активность приложения.
 * Ответственность: Точка входа в приложение, инициализация основного UI
 * и обработка событий сетевого подключения.
 *
 * Использует Jetpack Compose для отображения UI и Dagger 2 для внедрения зависимостей.
 * Отслеживает состояние сетевого подключения и показывает соответствующие уведомления.
 */
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var networkViewModel: NetworkViewModel
    private lateinit var themeViewModel: ThemeViewModel

    /**
     * Инициализация активности.
     * Настраивает отображение контента через Compose и запускает отслеживание
     * сетевых событий.
     *
     * @param savedInstanceState Сохраненное состояние активности
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        networkViewModel = ViewModelProvider(this, viewModelFactory)[NetworkViewModel::class]
        themeViewModel = ViewModelProvider(this, viewModelFactory)[ThemeViewModel::class]
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    networkViewModel.events.collect { event ->
                        when (event) {
                            is NetworkEvent.ShowNoConnectionToast -> {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Отсутствует подключение к интернету",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                launch {
                    themeViewModel.locale.collect { locale ->
                        handleLocaleChangeSafe(locale)
                    }
                }
            }
        }
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsStateWithLifecycle()
            val mainColor by themeViewModel.mainColor.collectAsStateWithLifecycle()
            val locale by themeViewModel.locale.collectAsStateWithLifecycle()

            MyAppSHMR25Theme(
                darkTheme = isDarkTheme,
                mainColor = mainColor
            ) {
                CompositionLocalProvider(
                    LocalViewModelFactory provides appComponent.viewModelFactory(),
                    LocalAppLocale provides Locale(locale)
                ) {
                    AppGraph()
                }
            }
        }
    }

    private var currentLocale: String? = null
    private var isFirstLocaleLoad = true

    private fun handleLocaleChangeSafe(newLocale: String) {
        Log.d("LocaleChange", "Locale received: $newLocale, current: $currentLocale, isFirst: $isFirstLocaleLoad")

        if (isFirstLocaleLoad) {
            currentLocale = newLocale
            isFirstLocaleLoad = false
            applyLocaleToApplication(newLocale)
            Log.d("LocaleChange", "Initial locale set to: $newLocale")
            return
        }

        if (currentLocale == newLocale) {
            Log.d("LocaleChange", "Same locale, skipping")
            return
        }

        Log.d("LocaleChange", "Locale changed from $currentLocale to $newLocale")

        val previousLocale = currentLocale
        currentLocale = newLocale

        applyLocaleToApplication(newLocale)

        showLocaleChangedNotification(newLocale, previousLocale)

        Log.d("LocaleChange", "Locale applied successfully")
    }

    private fun applyLocaleToApplication(locale: String) {
        Log.d("LocaleChange", "Applying locale: $locale")
        val updatedContext = themeViewModel.updateLocaleContext(applicationContext)

        val configuration = updatedContext.resources.configuration
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun showLocaleChangedNotification(newLocale: String, previousLocale: String?) {
        val languageName = when (newLocale) {
            "ru" -> "Русский"
            "en" -> "English"
            else -> newLocale.uppercase()
        }

        if (previousLocale != null) {
            Toast.makeText(
                this,
                "Язык изменен на $languageName",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
