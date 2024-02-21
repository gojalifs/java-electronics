package com.ngapak.dev.javaelectronics.features.checkout.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ngapak.dev.javaelectronics.utils.Converter.toRupiah

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navigateUp: () -> Unit,
    navigateToOrder: () -> Unit,
    modifier: Modifier = Modifier,
    checkoutViewModel: CheckoutViewModel,

    ) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var openMinimalDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyAppBar(modifier, navigateUp)
        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    openMinimalDialog = true
//                    checkoutViewModel.pay()
//                }) {
//                Text(text = "PAY NOW", Modifier.padding(horizontal = 16.dp))
//            }
//        },
//        floatingActionButtonPosition = FabPosition.Center,
    ) {
        if (openMinimalDialog) {
            PayDialog(
                onDismiss = { openMinimalDialog = false },
                navigateToOrder = { navigateToOrder() }, viewModel = checkoutViewModel
            )
        }
        Column(
            modifier = modifier
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (showBottomSheet) {
                AddressModalBottom(
                    showBottomSheet = { isShow ->
                        showBottomSheet = isShow
                    },
                    checkoutViewModel = checkoutViewModel,
                )
            }
            AddressCard(
                modifier = modifier,
                checkoutViewModel = checkoutViewModel,
                showAddressModal = { showBottomSheet = true },
            )
            ItemDetail(modifier = modifier.fillMaxWidth(), checkoutViewModel)
            checkoutViewModel.address.collectAsState().value.let { address ->
                Button(
                    onClick = {
                    },
                    enabled = address != null,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Text(text = "Pay Now")
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(modifier: Modifier, navigateUp: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Checkout") },
        navigationIcon = {
            IconButton(onClick = {
                navigateUp()
            }) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back"
                )
            }
        },
        modifier = modifier,
    )
}

@Composable
fun ItemDetail(modifier: Modifier = Modifier, viewModel: CheckoutViewModel) {
    Card {
        viewModel.transaction.collectAsState().value.let { transaction ->
            Column(
                modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(modifier = modifier.height(IntrinsicSize.Max)) {
                    Column(modifier = modifier.weight(1f)) {
                        Text(
                            text = "${transaction.product?.name}",
                            fontSize = 20.sp,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = modifier.weight(1f),
                        )
                        Text(
                            text = "${transaction.product?.price?.toRupiah()}",
                            fontSize = 16.sp,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            color = Color.Gray,
                            modifier = modifier.weight(1f),
                        )
                    }
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("${transaction.product?.imageUrl}")
                            .crossfade(true).build(),
                        contentDescription = "${transaction.product?.name}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(
                                shape = RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp,
                                    bottomStart = 8.dp,
                                    bottomEnd = 8.dp
                                )
                            ),
                    )
                }
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Quantity")
                    Text(text = "${transaction.qty} pcs")
                }
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Sub Total")
                    Text(text = "${transaction.totalPrice?.toRupiah()}")
                }
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Delivery Fee")
                    Text(text = 0.toRupiah())
                }
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Grand Total",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                    Text(
                        text = "${transaction.totalPrice?.toRupiah()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun AddressCard(
    showAddressModal: () -> Unit,
    checkoutViewModel: CheckoutViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { showAddressModal() },
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(text = "Shipping Address")
            HorizontalDivider(modifier.padding(vertical = 4.dp))
            Text(
                text = checkoutViewModel.receiverName.collectAsState().value
                    ?: "Tap To Add Address",
                fontSize = 20.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Text(text = checkoutViewModel.phone.collectAsState().value)
            Text(text = checkoutViewModel.fullAddress.collectAsState().value, color = Color.Gray)
            Text(text = checkoutViewModel.note.collectAsState().value, color = Color.Gray)
        }
    }
}

@Composable
fun PayDialog(onDismiss: () -> Unit, navigateToOrder: () -> Unit, viewModel: CheckoutViewModel) {
    Dialog(onDismissRequest = { onDismiss() }) {
        viewModel.pay.collectAsState().value.let {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp, 8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (!it.contains("Paid")) {
                            CircularProgressIndicator(modifier = Modifier.padding(4.dp))
                            Text(text = it)
                        }
                    }
                }
                if (it.contains("Paid")) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                    )
                    Button(
                        onClick = {
                            onDismiss()
                            navigateToOrder()
                        },
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}
