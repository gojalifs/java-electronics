package com.ngapak.dev.javaelectronics.features.checkout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Address
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Transaction
import com.ngapak.dev.javaelectronics.features.checkout.domain.usecase.CheckoutUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CheckoutViewModel(private val useCase: CheckoutUseCase) : ViewModel() {
    private var _addresses = MutableStateFlow<Resource<List<Address>>>(Resource.Loading())
    val addresses: StateFlow<Resource<List<Address>>> get() = _addresses

    private var _transaction = MutableStateFlow(Transaction())
    val transaction: StateFlow<Transaction> get() = _transaction

    private var _pay = MutableStateFlow<String>("")
    val pay: StateFlow<String> get() = _pay

    fun getAddress() {
        viewModelScope.launch {
            useCase.getAddress()
                .catch {
                    _addresses.value =
                        Resource.Error("Something error happened, ${it.localizedMessage}")
                }
                .collectLatest { resource ->
                    _addresses.value = resource
                }
        }
    }

    fun checkout(transaction: Transaction) {
        _transaction.value = transaction
    }

    fun pay() {
        viewModelScope.launch {
            _pay.value = "Please wait... Preparing your transaction"
            delay(3000L)
            _pay.value = "Paying..."
            delay(3000L)
            _pay.value = "Order Paid"
        }
    }
}