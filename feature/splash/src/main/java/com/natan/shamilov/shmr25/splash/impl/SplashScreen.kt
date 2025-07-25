package com.natan.shamilov.shmr25.splash.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory

@Composable
fun SplashScreen(
    onMainScreen: () -> Unit,
    onLoginScreen: () -> Unit,
    viewModel: SplashViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("my_animation.json"))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1
    )
    val shouldNavigate by viewModel.uiState.collectAsStateWithLifecycle()
    val isPinCodeSet by viewModel.isPinCodeSet.collectAsStateWithLifecycle()

    LaunchedEffect(progress) {
        if (progress == 1f && shouldNavigate) {
            if (isPinCodeSet) {
                onLoginScreen()
            } else {
                onMainScreen()
            }
        }
    }

    LaunchedEffect(shouldNavigate) {
        if (progress == 1f && shouldNavigate) {
            if (isPinCodeSet) {
                onLoginScreen()
            } else {
                onMainScreen()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(240.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(220.dp)
            )
        }
    }
}
