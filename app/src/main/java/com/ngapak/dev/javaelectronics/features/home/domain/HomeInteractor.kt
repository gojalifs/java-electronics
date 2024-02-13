package com.ngapak.dev.javaelectronics.features.home.domain

import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.home.domain.model.Product
import com.ngapak.dev.javaelectronics.features.home.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow

class HomeInteractor(private val homeRepository: IHomeRepository) : HomeUseCase {
    override fun getAllProduct(): Flow<Resource<List<Product>>> {
        return homeRepository.getAllProduct()
    }
}