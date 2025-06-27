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
import com.natan.shamilov.shmr25.common.ui.theme.MyAppSHMR25Theme
import com.natan.shamilov.shmr25.app.navigation.AppGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val networkViewModel: NetworkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MyAppSHMR25Theme {
                AppGraph()
            }
        }
        initNetworkEvents()
    }
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
