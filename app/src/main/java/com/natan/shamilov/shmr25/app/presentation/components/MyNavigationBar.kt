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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import com.natan.shamilov.shmr25.app.presentation.navigation.NavigationItem
import com.natan.shamilov.shmr25.app.presentation.navigation.NavigationState
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.localizedString

@Composable
fun MyNavigationBar(
    navigationList: List<NavigationItem>,
    navigationState: NavigationState,
    navBackStackEntry: State<NavBackStackEntry?>,
    onHaptiv: () -> Unit,
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
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
                onClick = {
                    onHaptiv()
                    navigationState.bottomNavigate(item.screen)
                },
                icon = {
                    Icon(
                        painter = painterResource(item.iconId),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = localizedString(item.label),
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}
