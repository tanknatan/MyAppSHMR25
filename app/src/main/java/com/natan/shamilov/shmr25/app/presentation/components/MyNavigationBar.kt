package com.natan.shamilov.shmr25.app.presentation.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import com.natan.shamilov.shmr25.app.presentation.navigation.NavigationItem
import com.natan.shamilov.shmr25.app.presentation.navigation.NavigationState
import com.natan.shamilov.shmr25.common.presentation.ui.theme.BottomBarBackground
import com.natan.shamilov.shmr25.common.presentation.ui.theme.rodotoFont

@Composable
fun MyNavigationBar(
    navigationList: List<NavigationItem>,
    navigationState: NavigationState,
    navBackStackEntry: State<NavBackStackEntry?>,
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = BottomBarBackground
    ) {
        navigationList.forEach { item ->
            val fromParam = navBackStackEntry.value?.arguments?.getString("from") ?: ""

            val selectedByHierarchy = navBackStackEntry.value?.destination?.hierarchy?.any { destination ->
                item.screen.route == destination.route
            } ?: false

            val selectedByFrom = when (item) {
                NavigationItem.Expenses -> fromParam == NavigationItem.Expenses.screen.route
                NavigationItem.Incomes -> fromParam == NavigationItem.Incomes.screen.route
                else -> false
            }

            val selected = selectedByHierarchy || selectedByFrom

            NavigationBarItem(
                selected = selected,
                onClick = { navigationState.bottomNavigate(item.screen) },
                icon = {
                    Icon(
                        painter = painterResource(item.iconId),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.label),
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = rodotoFont
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}
