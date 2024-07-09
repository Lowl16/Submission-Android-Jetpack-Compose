package com.dicoding.jetlaptop

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.jetlaptop.model.LaptopData
import com.dicoding.jetlaptop.ui.navigation.Screen
import com.dicoding.jetlaptop.ui.theme.JetLaptopTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetLaptopAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetLaptopTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetLaptopApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("lazy_column").performScrollToIndex(0)
        composeTestRule.onNodeWithText(LaptopData.dummyLaptop[0].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailLaptop.route)
        composeTestRule.onNodeWithText(LaptopData.dummyLaptop[0].name).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navigateTo_ProfileScreen() {
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.name).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.email).assertIsDisplayed()
    }

    @Test
    fun searchShowEmptyListLaptop() {
        val incorrectSearch = "12345"
        composeTestRule.onNodeWithStringId(R.string.search_hint).performTextInput(incorrectSearch)
        composeTestRule.onNodeWithTag("empty_data").assertIsDisplayed()
    }

    @Test
    fun searchShowListLaptop() {
        val rightSearch = "HP"
        composeTestRule.onNodeWithStringId(R.string.search_hint).performTextInput(rightSearch)
        composeTestRule.onNodeWithText("HP").assertIsDisplayed()
    }

    @Test
    fun favoriteClickAndDeleteInHome_ShowAndNotShowInFavoriteScreen() {
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithTag("lazy_column").performScrollToIndex(0)
        composeTestRule.onNodeWithText(LaptopData.dummyLaptop[0].name).assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("icon_favorite").onFirst().performClick()
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(LaptopData.dummyLaptop[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithTag("lazy_column").performScrollToIndex(0)
        composeTestRule.onNodeWithText(LaptopData.dummyLaptop[0].name).assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("icon_favorite").onFirst().performClick()
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.empty_favorite).assertIsDisplayed()
    }

    @Test
    fun favoriteClickAndDeleteInDetailScreen_ShowAndNotShowInFavoriteScreen() {
        composeTestRule.onNodeWithText(LaptopData.dummyLaptop[0].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailLaptop.route)
        composeTestRule.onNodeWithTag("icon_button_favorite").performClick()
        composeTestRule.onNodeWithTag("icon_button_back").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(LaptopData.dummyLaptop[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithText(LaptopData.dummyLaptop[0].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailLaptop.route)
        composeTestRule.onNodeWithTag("icon_button_favorite").performClick()
        composeTestRule.onNodeWithTag("icon_button_back").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.empty_favorite).assertIsDisplayed()
    }
}