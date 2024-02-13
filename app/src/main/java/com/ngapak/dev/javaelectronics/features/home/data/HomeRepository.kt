package com.ngapak.dev.javaelectronics.features.home.data

import com.google.firebase.FirebaseException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.home.domain.model.Product
import com.ngapak.dev.javaelectronics.features.home.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class HomeRepository : IHomeRepository {
    override fun getAllProduct(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val auth = Firebase.auth

            val user = auth.currentUser
            if (user != null) {
                val db = Firebase.firestore
                val result = db.collection("products").get().await().toObjects(Product::class.java)
                emit(Resource.Success(result))
            } else {
                emit(Resource.Error("You're logged out. Please login again"))
            }
        } catch (e: FirebaseException) {
            emit(Resource.Error("Error getting data. Message : ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error Happened. Try Again Later ${e.localizedMessage}"))
        }
    }
}