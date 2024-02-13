package com.ngapak.dev.javaelectronics.features.product.domain.repository

import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.product.domain.model.ProductDetail
import kotlinx.coroutines.flow.Flow

interface IProductDetailRepository {
    fun getProductDetail(id: String): Flow<Resource<ProductDetail>>
}