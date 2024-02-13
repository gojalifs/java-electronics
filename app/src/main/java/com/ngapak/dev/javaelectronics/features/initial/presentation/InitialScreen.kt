package com.ngapak.dev.javaelectronics.features.initial.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ngapak.dev.javaelectronics.core.Injection
import com.ngapak.dev.javaelectronics.ui.navigation.JavaElectronicsNavigation

@Composable
fun InitialScreen(
    modifier: Modifier = Modifier,
    initViewModel: InitialViewModel = viewModel(
        factory = InitViewModelFactory(Injection.provideInitUseCase())
    ),
    navHostController: NavHostController,
) {
    LaunchedEffect(Unit) {
        if (initViewModel.checkSession) {
            navHostController.navigate(JavaElectronicsNavigation.HOME_ROUTE) {
                popUpTo(JavaElectronicsNavigation.INITIAL_ROUTE) { inclusive = true }
            }
        } else {
            navHostController.navigate(JavaElectronicsNavigation.LOGIN_ROUTE) {
                popUpTo(JavaElectronicsNavigation.INITIAL_ROUTE) { inclusive = true }
            }
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}