package com.ngapak.dev.javaelectronics.features.checkout.presentation

import android.util.Log
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

    private val _address = MutableStateFlow<Address?>(null)
    val address: StateFlow<Address?> get() = _address

    private var _pay = MutableStateFlow("")
    val pay: StateFlow<String> get() = _pay


    private var _receiverName = MutableStateFlow<String?>(null)
    val receiverName: StateFlow<String?> get() = _receiverName

    private var _phone = MutableStateFlow("")
    val phone: StateFlow<String> get() = _phone

    private var _fullAddress = MutableStateFlow("")
    val fullAddress: StateFlow<String> get() = _fullAddress

    private var _addressDetail = MutableStateFlow("")
    val addressDetail: StateFlow<String> get() = _addressDetail

    private var _note = MutableStateFlow("")
    val note: StateFlow<String> get() = _note

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

    fun setAddress() {
        val address = Address(
            receiverName = _receiverName.value,
            phone = _phone.value,
            address = _fullAddress.value,
            addressDetail = _addressDetail.value,
            note = _note.value,
        )
        Log.d("TAG", "setAddress: ${address.toString()}")
        _address.value = address
    }

    fun setReceiverName(receiver: String) {
        _receiverName.value = receiver
    }

    fun setPhoneNumber(phone: String) {
        _phone.value = phone
    }

    fun setFullAddress(fullAddress: String) {
        _fullAddress.value = fullAddress
    }

    fun setAddressDetail(addressDetail: String) {
        _addressDetail.value = addressDetail
    }

    fun setNote(note: String) {
        _note.value = note
    }

    fun checkout(transaction: Transaction) {
        _transaction.value = transaction
    }

    fun clearShippingAddress() {
        _receiverName.value = ""
        _phone.value = ""
        _fullAddress.value = ""
        _addressDetail.value = ""
        _note.value = ""

        _address.value = null
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