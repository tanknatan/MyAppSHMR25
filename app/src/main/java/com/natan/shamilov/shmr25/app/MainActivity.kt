package com.natan.shamilov.shmr25.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.natan.shamilov.shmr25.app.network.NetworkEvent
import com.natan.shamilov.shmr25.app.network.NetworkViewModel
import com.natan.shamilov.shmr25.app.presentation.navigation.AppGraph
import com.natan.shamilov.shmr25.common.presentation.ui.theme.MyAppSHMR25Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    @Inject lateinit var networkViewModel: NetworkViewModel

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
        (application as App).appComponent.inject(this)
        // networkViewModel теперь должен быть проинициализирован через Dagger
        setContent {
            MyAppSHMR25Theme {
                AppGraph()
            }
        }
        initNetworkEvents()
    }

    /**
     * Инициализация обработки сетевых событий.
     * Запускает корутину для отслеживания состояния сети и отображения
     * уведомлений при отсутствии подключения.
     */
    private fun initNetworkEvents() {
        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                networkViewModel.events.collect { event ->
                    when (event) {
                        is NetworkEvent.ShowNoConnectionToast -> {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Отсутствует подключение к интернету",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
