package com.ngapak.dev.javaelectronics.core

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngapak.dev.javaelectronics.features.auth.data.AuthRepository
import com.ngapak.dev.javaelectronics.features.auth.domain.AuthInteractor
import com.ngapak.dev.javaelectronics.features.auth.domain.usecase.AuthUseCase
import com.ngapak.dev.javaelectronics.features.checkout.data.CheckoutRepository
import com.ngapak.dev.javaelectronics.features.checkout.domain.CheckoutInteractor
import com.ngapak.dev.javaelectronics.features.checkout.domain.usecase.CheckoutUseCase
import com.ngapak.dev.javaelectronics.features.home.data.HomeRepository
import com.ngapak.dev.javaelectronics.features.home.domain.HomeInteractor
import com.ngapak.dev.javaelectronics.features.home.domain.HomeUseCase
import com.ngapak.dev.javaelectronics.features.initial.data.InitialRepository
import com.ngapak.dev.javaelectronics.features.initial.domain.usecase.InitialInteractor
import com.ngapak.dev.javaelectronics.features.initial.domain.usecase.InitialUseCase
import com.ngapak.dev.javaelectronics.features.product.data.ProductDetailRepository
import com.ngapak.dev.javaelectronics.features.product.domain.ProductDetailInteractor
import com.ngapak.dev.javaelectronics.features.product.domain.usecase.ProductDetailUseCase

object Injection {
    fun provideAuthUseCase(): AuthUseCase {
        return AuthInteractor(AuthRepository())
    }

    fun provideInitUseCase(): InitialUseCase {
        return InitialInteractor(InitialRepository())
    }

    fun provideHomeUseCase(): HomeUseCase {
        return HomeInteractor(HomeRepository())
    }

    fun provideProductDetailUseCase(): ProductDetailUseCase {
        val auth = Firebase.auth
        return ProductDetailInteractor(ProductDetailRepository(auth))
    }

    fun provideCheckoutUseCase(): CheckoutUseCase {
        val auth = Firebase.auth
        val db = Firebase.firestore
        return CheckoutInteractor(CheckoutRepository(auth, db))
    }
}