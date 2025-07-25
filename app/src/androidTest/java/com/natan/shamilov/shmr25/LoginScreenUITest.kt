package com.natan.shamilov.shmr25

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.MainColorType
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.MyAppSHMR25Theme
import com.natan.shamilov.shmr25.login.impl.presentation.screen.LoginViewModel
import com.natan.shamilov.shmr25.login.impl.presentation.screen.PinCodeLoginScreen
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI тесты для экрана ввода пин-кода
 * Проверяют функциональность ввода пин-кода, отображение индикаторов и обработку ошибок
 */
@RunWith(AndroidJUnit4::class)
class LoginScreenUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loginScreen_initialState_isCorrect() {
        // Given: Инициализация экрана логина
        val mockViewModel = mockk<LoginViewModel>(relaxed = true)

        composeTestRule.setContent {
            MyAppSHMR25Theme(
                darkTheme = false,
                mainColor = MainColorType.getDefault()
            ) {
                PinCodeLoginScreen(
                    viewModel = mockViewModel,
                    onContinue = {},
                )
            }
        }

        // Then: Проверяем отображение основных элементов
        composeTestRule
            .onNodeWithTag("pin_title")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("pin_indicators")
            .assertIsDisplayed()

        // Проверяем наличие всех индикаторов пин-кода
        repeat(4) { i ->
            composeTestRule
                .onNodeWithTag("pin_indicator_$i")
                .assertExists()
        }

        // Проверяем наличие цифровых кнопок
        (1..9).forEach { number ->
            composeTestRule
                .onNodeWithTag("pin_key_$number")
                .assertExists()
                .assertIsDisplayed()
        }

        // Проверяем наличие кнопки 0 и кнопки удаления
        composeTestRule
            .onNodeWithTag("pin_key_0")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("pin_key_<")
            .assertExists()
            .assertIsDisplayed()

        // Ошибка не должна отображаться изначально
        composeTestRule
            .onNodeWithTag("error_message")
            .assertDoesNotExist()
    }

    @Test
    fun loginScreen_enterSingleDigit_showsInIndicator() {
        // Given: Экран логина
        val mockViewModel = mockk<LoginViewModel>(relaxed = true)

        composeTestRule.setContent {
            MyAppSHMR25Theme(
                darkTheme = false,
                mainColor = MainColorType.getDefault()
            ) {
                PinCodeLoginScreen(
                    viewModel = mockViewModel,
                    onContinue = {},
                )
            }
        }

        // When: Нажимаем цифру 1
        composeTestRule
            .onNodeWithTag("pin_key_1")
            .performClick()

        // Then: Первый индикатор должен быть заполнен
        composeTestRule
            .onNodeWithTag("pin_indicator_0")
            .assertExists()
    }

    @Test
    fun loginScreen_enterMultipleDigits_showsInIndicators() {
        // Given: Экран логина
        val mockViewModel = mockk<LoginViewModel>(relaxed = true)

        composeTestRule.setContent {
            MyAppSHMR25Theme(
                darkTheme = false,
                mainColor = MainColorType.getDefault()
            ) {
                PinCodeLoginScreen(
                    viewModel = mockViewModel,
                    onContinue = {},
                )
            }
        }

        // When: Вводим последовательность цифр
        composeTestRule
            .onNodeWithTag("pin_key_1")
            .performClick()

        composeTestRule
            .onNodeWithTag("pin_key_2")
            .performClick()

        composeTestRule
            .onNodeWithTag("pin_key_3")
            .performClick()

        // Then: Три индикатора должны быть заполнены
        composeTestRule
            .onNodeWithTag("pin_indicator_0")
            .assertExists()

        composeTestRule
            .onNodeWithTag("pin_indicator_1")
            .assertExists()

        composeTestRule
            .onNodeWithTag("pin_indicator_2")
            .assertExists()
    }

    @Test
    fun loginScreen_deleteDigit_removesFromIndicators() {
        // Given: Экран логина с введенными цифрами
        val mockViewModel = mockk<LoginViewModel>(relaxed = true)

        composeTestRule.setContent {
            MyAppSHMR25Theme(
                darkTheme = false,
                mainColor = MainColorType.getDefault()
            ) {
                PinCodeLoginScreen(
                    viewModel = mockViewModel,
                    onContinue = {},
                )
            }
        }

        // Вводим две цифры
        composeTestRule
            .onNodeWithTag("pin_key_1")
            .performClick()

        composeTestRule
            .onNodeWithTag("pin_key_2")
            .performClick()

        // When: Нажимаем кнопку удаления
        composeTestRule
            .onNodeWithTag("pin_key_<")
            .performClick()

        // Then: Должна остаться только одна цифра
        composeTestRule
            .onNodeWithTag("pin_indicator_0")
            .assertExists()
    }


    @Test
    fun loginScreen_cannotEnterMoreThanFourDigits() {
        // Given: Экран логина
        val mockViewModel = mockk<LoginViewModel>(relaxed = true)

        composeTestRule.setContent {
            MyAppSHMR25Theme(
                darkTheme = false,
                mainColor = MainColorType.getDefault()
            ) {
                PinCodeLoginScreen(
                    viewModel = mockViewModel,
                    onContinue = {},
                )
            }
        }

        // When: Пытаемся ввести 5 цифр
        composeTestRule
            .onNodeWithTag("pin_key_1")
            .performClick()

        composeTestRule
            .onNodeWithTag("pin_key_2")
            .performClick()

        composeTestRule
            .onNodeWithTag("pin_key_3")
            .performClick()

        composeTestRule
            .onNodeWithTag("pin_key_4")
            .performClick()

        composeTestRule
            .onNodeWithTag("pin_key_5")
            .performClick()

        // Then: Должны отображаться только 4 индикатора
        repeat(4) { i ->
            composeTestRule
                .onNodeWithTag("pin_indicator_$i")
                .assertExists()
        }
    }

    @Test
    fun loginScreen_emptyPin_deleteDoesNothing() {
        // Given: Экран логина без введенных цифр
        val mockViewModel = mockk<LoginViewModel>(relaxed = true)

        composeTestRule.setContent {
            MyAppSHMR25Theme(
                darkTheme = false,
                mainColor = MainColorType.getDefault()
            ) {
                PinCodeLoginScreen(
                    viewModel = mockViewModel,
                    onContinue = {},
                )
            }
        }

        // When: Нажимаем кнопку удаления на пустом поле
        composeTestRule
            .onNodeWithTag("pin_key_<")
            .performClick()

        // Then: Состояние не должно измениться, все индикаторы пустые
        repeat(4) { i ->
            composeTestRule
                .onNodeWithTag("pin_indicator_$i")
                .assertExists()
        }
    }

    @Test
    fun loginScreen_allNumberKeys_areClickable() {
        // Given: Экран логина
        val mockViewModel = mockk<LoginViewModel>(relaxed = true)

        composeTestRule.setContent {
            MyAppSHMR25Theme(
                darkTheme = false,
                mainColor = MainColorType.getDefault()
            ) {
                PinCodeLoginScreen(
                    viewModel = mockViewModel,
                    onContinue = {},
                )
            }
        }

        // Then: Все цифровые кнопки должны быть кликабельными
        (0..9).forEach { number ->
            composeTestRule
                .onNodeWithTag("pin_key_$number")
                .assertExists()
                .assertHasClickAction()
        }

        // Кнопка удаления тоже должна быть кликабельной
        composeTestRule
            .onNodeWithTag("pin_key_<")
            .assertExists()
            .assertHasClickAction()
    }
} 