package com.ngapak.dev.javaelectronics.features.home.presentation

import androidx.lifecycle.ViewModel
import com.ngapak.dev.javaelectronics.features.home.domain.HomeUseCase

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {
    val products = homeUseCase.getAllProduct()
}