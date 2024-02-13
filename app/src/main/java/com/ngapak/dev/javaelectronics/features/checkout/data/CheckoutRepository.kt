package com.ngapak.dev.javaelectronics.features.checkout.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Address
import com.ngapak.dev.javaelectronics.features.checkout.domain.repository.ICheckoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class CheckoutRepository(private val auth: FirebaseAuth?, private val db: FirebaseFirestore) :
    ICheckoutRepository {
    override fun getAddress(): Flow<Resource<List<Address>>> = flow {
        if (auth?.currentUser != null) {
            emit(Resource.Loading())

            try {
                val user = db.collection("users").document(auth.currentUser!!.uid)
                val addressesSnapshot = user.collection("address").get().await()
                val addresses = addressesSnapshot.toObjects(Address::class.java)

                emit(Resource.Success(addresses))
            } catch (e: Exception) {
                emit(Resource.Error("Error, {e.localizedMessage}"))
            }
        }
    }
}