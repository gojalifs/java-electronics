package com.ngapak.dev.javaelectronics.features.checkout.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressModalBottom(
    showBottomSheet: (Boolean) -> Unit,
    checkoutViewModel: CheckoutViewModel,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet(false)
            checkoutViewModel.clearShippingAddress()
        },
        windowInsets = WindowInsets.navigationBars,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TextField(
                value = checkoutViewModel.receiverName.collectAsState().value ?: "",
                onValueChange = { checkoutViewModel.setReceiverName(it) },
                label = { Text(text = "Receiver Name") },
                modifier = modifier.fillMaxWidth(),
            )
            TextField(
                value = checkoutViewModel.phone.collectAsState().value,
                onValueChange = { checkoutViewModel.setPhoneNumber(it) },
                label = { Text(text = "Phone Number") },
                modifier = modifier.fillMaxWidth(),
            )
            TextField(
                value = checkoutViewModel.fullAddress.collectAsState().value,
                onValueChange = { checkoutViewModel.setFullAddress(it) },
                label = { Text(text = "Full Address") },
                modifier = modifier.fillMaxWidth(),
            )
            TextField(
                value = checkoutViewModel.addressDetail.collectAsState().value,
                onValueChange = { checkoutViewModel.setAddressDetail(it) },
                label = { Text(text = "Detail Address") },
                modifier = modifier.fillMaxWidth(),
            )
            TextField(
                value = checkoutViewModel.note.collectAsState().value,
                onValueChange = { checkoutViewModel.setNote(it) },
                label = { Text(text = "Note") },
                modifier = modifier.fillMaxWidth(),
            )
            Button(onClick = {
                checkoutViewModel.setAddress()
                showBottomSheet(false)
            }, modifier = modifier.fillMaxWidth()) {
                Text(text = "Save")
            }
        }
    }
}