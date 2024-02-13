package com.ngapak.dev.javaelectronics.features.home.domain

import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.home.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun getAllProduct(): Flow<Resource<List<Product>>>
}