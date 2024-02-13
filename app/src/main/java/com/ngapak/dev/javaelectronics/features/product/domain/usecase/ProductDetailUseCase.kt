package com.ngapak.dev.javaelectronics.features.product.domain.usecase

import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.product.domain.model.ProductDetail
import kotlinx.coroutines.flow.Flow

interface ProductDetailUseCase {
    fun getProductDetail(id: String): Flow<Resource<ProductDetail>>
}