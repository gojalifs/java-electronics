package com.ngapak.dev.javaelectronics.features.initial.presentation

import androidx.lifecycle.ViewModel
import com.ngapak.dev.javaelectronics.features.initial.domain.usecase.InitialUseCase

class InitialViewModel(private val initialUseCase: InitialUseCase) : ViewModel() {
    val checkSession = initialUseCase.checkSession()
}