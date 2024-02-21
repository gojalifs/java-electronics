package com.ngapak.dev.javaelectronics.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ngapak.dev.javaelectronics.features.auth.presentation.LoginScreen
import com.ngapak.dev.javaelectronics.features.auth.presentation.RegisterScreen
import com.ngapak.dev.javaelectronics.features.checkout.presentation.CheckoutScreen
import com.ngapak.dev.javaelectronics.features.checkout.presentation.CheckoutViewModel
import com.ngapak.dev.javaelectronics.features.checkout.presentation.CheckoutViewModelFactory
import com.ngapak.dev.javaelectronics.features.history.presentation.HistoryScreen
import com.ngapak.dev.javaelectronics.features.history.presentation.HistoryViewModel
import com.ngapak.dev.javaelectronics.features.history.presentation.OrderDetailScreen
import com.ngapak.dev.javaelectronics.features.home.presentation.MainAHomeAppScreen
import com.ngapak.dev.javaelectronics.features.initial.presentation.InitialScreen
import com.ngapak.dev.javaelectronics.features.product.presentation.ProductDetailScreen

@Composable
fun JavaElectronicaNavGraph(navHostController: NavHostController) {
    val checkoutViewModel: CheckoutViewModel = viewModel(
        factory = CheckoutViewModelFactory.getInstance()
    )

    val historyViewModel = HistoryViewModel()

    NavHost(
        navController = navHostController,
        startDestination = JavaElectronicsNavigation.INITIAL_ROUTE,
    ) {
        composable(JavaElectronicsNavigation.INITIAL_ROUTE) {
            InitialScreen(navHostController = navHostController)
        }
        composable(JavaElectronicsNavigation.LOGIN_ROUTE) {
            LoginScreen(
                navigateToRegister = {
                    navHostController.navigate(JavaElectronicsNavigation.REGISTER_ROUTE)
                },
                navigateToHome = {
                    navHostController.navigate(JavaElectronicsNavigation.HOME_ROUTE) {
                        popUpTo(JavaElectronicsNavigation.LOGIN_ROUTE) { inclusive = true }
                    }
                }
            )
        }
        composable(JavaElectronicsNavigation.REGISTER_ROUTE) {
            RegisterScreen()
        }
        composable(JavaElectronicsNavigation.HOME_ROUTE) {
            MainAHomeAppScreen(
                navigateToDetail = { id ->
                    navHostController.navigate("product/$id")
                },
                navigateToHistory = { navHostController.navigate(JavaElectronicsNavigation.HISTORY_ROUTE) },
                mainNavHostController = navHostController,
            )
        }

        composable(
            JavaElectronicsNavigation.PRODUCT_DETAIL_ROUTE,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) {
            ProductDetailScreen(
                it.arguments?.getString("productId"),
                checkoutViewModel = checkoutViewModel,
                navigateToCheckout = {
                    navHostController.navigate(JavaElectronicsNavigation.CHECKOUT_ROUTE)
                })
        }

        composable(JavaElectronicsNavigation.CHECKOUT_ROUTE) {
            CheckoutScreen(
                navigateUp = { navHostController.navigateUp() },
                navigateToOrder = {
                    navHostController.navigate(JavaElectronicsNavigation.HISTORY_ROUTE) {
                        popUpTo(JavaElectronicsNavigation.HOME_ROUTE)
                    }
                },
                checkoutViewModel = checkoutViewModel,
            )
        }

        composable(JavaElectronicsNavigation.HISTORY_ROUTE) {
            HistoryScreen(
                viewModel = historyViewModel,
                navigateToDetail = { navHostController.navigate(JavaElectronicsNavigation.HISTORY_DETAIL_ROUTE) },
                navigateUp = { navHostController.navigateUp() }
            )
        }

        composable(
            JavaElectronicsNavigation.HISTORY_DETAIL_ROUTE
        ) {
            OrderDetailScreen(
                navigateUp = { navHostController.navigateUp() },
                historyViewModel = historyViewModel,
            )
        }
    }
}