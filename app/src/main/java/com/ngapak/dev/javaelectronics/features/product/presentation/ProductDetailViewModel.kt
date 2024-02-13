package com.ngapak.dev.javaelectronics.features.product.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.product.domain.model.ProductDetail
import com.ngapak.dev.javaelectronics.features.product.domain.usecase.ProductDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val productDetailUseCase: ProductDetailUseCase) : ViewModel() {
    private var _productDetail = MutableStateFlow<Resource<ProductDetail>>(Resource.Loading())
    val productDetail: StateFlow<Resource<ProductDetail>> get() = _productDetail

    fun getProductDetail(id: String) {
        val detail = productDetailUseCase.getProductDetail(id)
        viewModelScope.launch {
            detail
                .catch {
                    this.emit(Resource.Error(it.message.toString()))
                }
                .collectLatest {
                    Log.d("TAG DETAIL VM", "getProductDetail: viewmodel ${it.data}")
                    _productDetail.value = it
                }
        }
    }
}