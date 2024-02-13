package com.ngapak.dev.javaelectronics.features.checkout.domain

import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.checkout.data.CheckoutRepository
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Address
import com.ngapak.dev.javaelectronics.features.checkout.domain.usecase.CheckoutUseCase
import kotlinx.coroutines.flow.Flow

class CheckoutInteractor(private val repository: CheckoutRepository) : CheckoutUseCase {
    override fun getAddress(): Flow<Resource<List<Address>>> = repository.getAddress()
}