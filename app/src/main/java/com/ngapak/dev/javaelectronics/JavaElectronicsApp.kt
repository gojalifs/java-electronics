package com.ngapak.dev.javaelectronics

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ngapak.dev.javaelectronics.ui.navigation.JavaElectronicaNavGraph

@Composable
fun JavaElectronicsApp(navHostController: NavHostController = rememberNavController()) {
    JavaElectronicaNavGraph(navHostController)
}