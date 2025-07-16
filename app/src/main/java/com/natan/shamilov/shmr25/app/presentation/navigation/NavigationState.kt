package com.natan.shamilov.shmr25.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.natan.shamilov.shmr25.common.impl.domain.entity.Screen

/**
 * Класс для управления навигацией в приложении.
 * Предоставляет методы для различных типов навигации между экранами.
 *
 * @property navHostController Контроллер навигации Jetpack Compose
 */
class NavigationState(
    val navHostController: NavHostController
) {
    /**
     * Осуществляет навигацию по нижней панели навигации.
     * Сохраняет состояние предыдущего экрана и восстанавливает его при возврате.
     *
     * @param screen Экран назначения
     */
    fun bottomNavigate(screen: Screen) {
        navHostController.navigate(screen.route) {
            popUpTo(id = navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    /**
     * Осуществляет навигацию от сплэш-экрана.
     * Удаляет сплэш-экран из стека навигации.
     *
     * @param screen Экран назначения
     */
    fun splashNavigate(screen: Screen) {
        val currentRoute = navHostController.currentBackStackEntry?.destination?.route ?: return
        navHostController.navigate(screen.route) {
            popUpTo(currentRoute) {
                inclusive = true
            }
        }
    }

    /**
     * Осуществляет навигацию в режиме single top.
     * Гарантирует единственный экземпляр экрана в стеке.
     *
     * @param screen Экран назначения
     */
    fun navigateSingleTopTo(screen: Screen) {
        val currentRoute = navHostController.currentBackStackEntry?.destination?.route ?: return
        navHostController.navigate(screen.route) {
            popUpTo(currentRoute) {
                saveState = false
            }
            launchSingleTop = true
        }
    }
}

/**
 * Composable функция для создания и запоминания состояния навигации.
 *
 * @param navHostController Опциональный контроллер навигации
 * @return Объект NavigationState для управления навигацией
 */
@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}
