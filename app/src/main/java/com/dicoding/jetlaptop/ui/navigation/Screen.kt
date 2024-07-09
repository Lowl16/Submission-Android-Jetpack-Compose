package com.dicoding.jetlaptop.ui.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object Favorite : Screen("favorite")

    object Profile : Screen("profile")

    object DetailLaptop : Screen("home/{laptopId}") {
        fun createRoute(laptopId: Int) = "home/$laptopId"
    }
}