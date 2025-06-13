package com.natan.shamilov.shmr25.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.natan.shamilov.shmr25.presentation.navigation.AppGraph
import com.natan.shamilov.shmr25.ui.theme.MyAppSHMR25Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MyAppSHMR25Theme {
                AppGraph()
            }
        }
    }
}
