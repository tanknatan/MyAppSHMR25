package com.natan.shamilov.shmr25.ui

import android.util.Log
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import com.natan.shamilov.shmr25.presentation.navigation.NavigationItem
import com.natan.shamilov.shmr25.presentation.navigation.NavigationState
import com.natan.shamilov.shmr25.ui.theme.BottomBarBackground
import com.natan.shamilov.shmr25.ui.theme.rodotoFont

@Composable
fun MyNavigationBar(
    navigationList : List<NavigationItem>,
    navigationState: NavigationState,
    navBackStackEntry: State<NavBackStackEntry?>
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = BottomBarBackground
    ) {
        navigationList.forEach { item ->
            val selected =
                navBackStackEntry.value?.destination?.hierarchy?.any { destination ->
                    item.screen.route == destination.route
                } ?: false
            NavigationBarItem(
                selected = selected,
                onClick = {
                    val result = navBackStackEntry.value?.destination?.hierarchy?.map {
                        it.route
                    }
                    if (!selected) navigationState.bottomNavigate(item.screen)

                    if (result != null) {
                        Log.d("Navigation", result.toList().toString())
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.iconId),
                        contentDescription = null,

                        )
                },
                label = {
                    Text(
                        text = stringResource(item.label),
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = rodotoFont,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.secondary,
                )
            )
        }
    }
}