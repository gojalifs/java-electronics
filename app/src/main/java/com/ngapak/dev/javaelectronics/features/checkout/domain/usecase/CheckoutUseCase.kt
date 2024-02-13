package com.ngapak.dev.javaelectronics.features.checkout.domain.usecase

import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Address
import kotlinx.coroutines.flow.Flow

interface CheckoutUseCase {
    fun getAddress(): Flow<Resource<List<Address>>>
}