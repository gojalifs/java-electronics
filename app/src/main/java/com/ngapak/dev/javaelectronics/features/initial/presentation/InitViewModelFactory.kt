package com.ngapak.dev.javaelectronics.features.initial.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngapak.dev.javaelectronics.features.auth.presentation.AuthViewModel
import com.ngapak.dev.javaelectronics.features.initial.domain.usecase.InitialUseCase

class InitViewModelFactory(private val useCase: InitialUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(InitialViewModel::class.java) -> InitialViewModel(useCase) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}