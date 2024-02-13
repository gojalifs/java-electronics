package com.ngapak.dev.javaelectronics.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Orders : Screen("orders")
    data object Profile : Screen("profile")
}