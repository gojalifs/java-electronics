package com.ngapak.dev.javaelectronics.features.auth.data

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.auth.domain.repository.IAuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepository : IAuthRepository {
    override fun signInWithPassword(email: String, password: String): Flow<Resource<FirebaseUser?>> =
        flow {
            Log.d("TAG1", "signInWithEmail:logging in")

            try {
                val auth = Firebase.auth
                Log.d("TAG2 Repository", "signInWithPassword: awaiting")
                delay(2000L)
                val task = auth.signInWithEmailAndPassword(email, password).await()
//                    .addOnCompleteListener { task ->
//                if (task.) {
                val user = task.user
                Log.d("TAG3a Repository listener", "signInWithPassword: success login")
//                } else {
//                Log.d(
//                    "TAG Repository listener",
//                    "signInWithPassword: error login ${task.exception?.message}"
//                )
//                    errorMsg = task.exception?.message.toString()
//                }

                Log.d(
                    "TAG4a Repository",
                    "signInWithPassword: await closed, ${user?.email}"
                )
//                if (errorMsg.isNotEmpty()) {
                Log.d("TAG5a Repository", "signInWithPassword: success login message")
                emit(Resource.Success(user))
//                } else {
            } catch (e: Exception) {
                Log.d(
                    "TAG3b Repository",
                    "signInWithPassword: error login, message ${e.message}"
                )
                emit(Resource.Error(e.message.toString()))
            }
//            emit(Result.Error("Something error, ${e.message}"))
        }

    override fun signUpWithPassword(email: String, password: String): Flow<Resource<Boolean>> =
        flow {
            Log.d("TAG", "signInWithEmail:logging in")

            try {
                val auth = Firebase.auth
                Log.d("TAG Repository", "signUpWithPassword: awaiting")
                delay(2000L)
                auth.createUserWithEmailAndPassword(email, password)
                Log.d("TAG Repository", "signUpWithPassword: await closed")

                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error("Something error, ${e.message}"))

            }
        }
}