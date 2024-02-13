package com.ngapak.dev.javaelectronics.features.product.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.product.domain.model.ProductDetail
import com.ngapak.dev.javaelectronics.features.product.domain.repository.IProductDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProductDetailRepository(private val auth: FirebaseAuth) : IProductDetailRepository {
    override fun getProductDetail(id: String): Flow<Resource<ProductDetail>> = flow {
        emit(Resource.Loading())
        Log.d("TAG DETAIL", "getProductDetail: getting product detail")
        try {
            if (auth.currentUser != null) {
                val db = Firebase.firestore
                val product = db.collection("products").document(id).get().await()
                    .toObject(ProductDetail::class.java)
                product?.let {
                    emit(Resource.Success(it))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}