package com.ngapak.dev.javaelectronics.features.product.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngapak.dev.javaelectronics.features.product.domain.usecase.ProductDetailUseCase

class ProductDetailViewModelFactory(private val productDetailUseCase: ProductDetailUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> ProductDetailViewModel(
                productDetailUseCase
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}