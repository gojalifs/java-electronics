package com.ngapak.dev.javaelectronics.features.auth.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.auth.domain.repository.IAuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth) : IAuthRepository {
    override fun signInWithPassword(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> =
        flow {
            try {
                delay(2000L)
                val task = auth.signInWithEmailAndPassword(email, password).await()
                val user = task.user
                emit(Resource.Success(user))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }

    override fun signUpWithPassword(email: String, password: String): Flow<Resource<Boolean>> =
        flow {
            Log.d("TAG", "signInWithEmail:logging in")

            try {
                Log.d("TAG Repository", "signUpWithPassword: awaiting")
                delay(2000L)
                auth.createUserWithEmailAndPassword(email, password)
                Log.d("TAG Repository", "signUpWithPassword: await closed")

                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error("Something error, ${e.message}"))

            }
        }

    override fun signOut(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val a = auth.signOut()
            Log.d("TAG", "signOut: Logged Out repository")
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error("Error logging out. Message : ${e.localizedMessage}"))
        }
    }
}