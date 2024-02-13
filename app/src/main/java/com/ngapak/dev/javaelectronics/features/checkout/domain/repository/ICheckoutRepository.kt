package com.ngapak.dev.javaelectronics.features.checkout.domain.repository

import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Address
import kotlinx.coroutines.flow.Flow

interface ICheckoutRepository {
    fun getAddress(): Flow<Resource<List<Address>>>
}