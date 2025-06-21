package com.example.shmrfinance.ui.widget.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    visible: Boolean
) {
//    AnimatedVisibility(
//        visible = visible,
//        enter = slideInVertically(initialOffsetY = { it }),
//        exit = slideOutVertically(targetOffsetY = { it }),
//        content = {
//            val backStackEntry by navController.currentBackStackEntryAsState()
//            val currentRoute = backStackEntry?.destination?.route
//
//            NavigationBar {
//                BottomBarItems.items.forEach {
//                    val isSelected = currentRoute == it.route::class.java.canonicalName
//
//                    NavigationBarItem(
//                        selected = isSelected,
//                        onClick = {
//                            navController.navigate(it.route) {
//                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
//                                launchSingleTop = true
//                                restoreState = true
//                            }
//                        },
//                        icon = {
//                            val tint = if (isSelected)
//                                MaterialTheme.colorScheme.primary
//                            else
//                                LocalContentColor.current
//
//                            Icon(
//                                painter = painterResource(it.icon),
//                                contentDescription = null,
//                                tint = tint
//                            )
//                        },
//                        label = { Text(text = stringResource(it.title)) }
//                    )
//                }
//            }
//        }
//    )
}