package com.ngapak.dev.javaelectronics.features.product.domain

import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.product.domain.model.ProductDetail
import com.ngapak.dev.javaelectronics.features.product.domain.repository.IProductDetailRepository
import com.ngapak.dev.javaelectronics.features.product.domain.usecase.ProductDetailUseCase
import kotlinx.coroutines.flow.Flow

class ProductDetailInteractor(private val productDetailRepository: IProductDetailRepository) :
    ProductDetailUseCase {
    override fun getProductDetail(id: String): Flow<Resource<ProductDetail>> {
        return productDetailRepository.getProductDetail(id)
    }
}