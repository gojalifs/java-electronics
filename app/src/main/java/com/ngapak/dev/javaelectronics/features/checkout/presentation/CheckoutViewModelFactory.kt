package com.ngapak.dev.javaelectronics.features.checkout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngapak.dev.javaelectronics.core.Injection
import com.ngapak.dev.javaelectronics.features.checkout.domain.usecase.CheckoutUseCase

class CheckoutViewModelFactory(private val checkoutUseCase: CheckoutUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CheckoutViewModel::class.java) -> CheckoutViewModel(
                checkoutUseCase
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: CheckoutViewModelFactory? = null

        fun getInstance(): CheckoutViewModelFactory = instance ?: synchronized(this) {
            instance ?: CheckoutViewModelFactory(Injection.provideCheckoutUseCase())
        }.also { instance = it }
    }
}