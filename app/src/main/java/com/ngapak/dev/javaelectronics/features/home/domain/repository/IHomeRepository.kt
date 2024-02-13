package com.ngapak.dev.javaelectronics.features.home.domain.repository

import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.home.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface IHomeRepository {
    fun getAllProduct(): Flow<Resource<List<Product>>>
}