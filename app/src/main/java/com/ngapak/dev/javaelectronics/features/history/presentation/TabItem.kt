package com.ngapak.dev.javaelectronics.features.history.presentation

import androidx.compose.runtime.Composable


data class TabItem(
    val title: String,
    val screen: @Composable (navigateToDetail: () -> Unit, historyViewModel: HistoryViewModel) -> Unit
)
