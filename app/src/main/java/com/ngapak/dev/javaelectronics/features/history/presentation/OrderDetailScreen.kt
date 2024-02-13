package com.ngapak.dev.javaelectronics.features.history.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ngapak.dev.javaelectronics.utils.Converter.toRupiah

@Composable
fun OrderDetailScreen(
    navigateUp: () -> Unit,
    historyViewModel: HistoryViewModel,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        Log.d("TAG", "OrderDetailScreen: ${historyViewModel.orders.toString()}")
    }
    Scaffold(
        topBar = {
            MyAppBar(modifier, navigateUp)
        },
        floatingActionButton = {
            if (historyViewModel.orders.deliveryStatus == "delivering") {
                FloatingActionButton(onClick = {
                    historyViewModel.orderReceived(historyViewModel.orders)
                    navigateUp()
                }) {
                    Text(text = "Order Received", modifier = Modifier.padding(8.dp))
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AddressCard(modifier = modifier)
            OrderDetail(modifier = modifier.fillMaxWidth(), historyViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(modifier: Modifier, navigateUp: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Order Detail") },
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
fun AddressCard(modifier: Modifier = Modifier) {
    Card(
        onClick = {}, modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(text = "Your Address")
            HorizontalDivider(modifier.padding(vertical = 4.dp))
            Text(
                text = "Fajar",
                fontSize = 20.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Text(text = "082345732")
            Text(text = "Gang Ngapak Perum GCC Sakura H7 No.17, Cikarang Utara", color = Color.Gray)
            Text(text = "Optik Satria Jaya(masukin aquarium)", color = Color.Gray)
        }
    }
}


@Composable
fun OrderDetail(modifier: Modifier = Modifier, viewModel: HistoryViewModel) {
    val order = viewModel.orders
    Card {
        Column(
            modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row {
                Text(
                    text = "${order.name}",
                    fontSize = 20.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = modifier.weight(1f),
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://static.promediateknologi.id/crop/0x0:0x0/0x0/webp/photo/p2/01/2023/09/06/images-2023-09-05T003002821-2527323090.jpeg")
                        .crossfade(true).build(),
                    contentDescription = "Product",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        )
                        .size(48.dp),
                )
            }
            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Quantity")
                Text(text = "${order.qty}")
            }
            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Sub Total")
                Text(text = "${order.totalPrice?.toRupiah()}")
            }
            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Delivery Fee")
                Text(text = 0.toRupiah())
            }
            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Grand Total")
                Text(text = "${order.totalPrice?.toRupiah()}")
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OrderDetailScreenPreview() {
    OrderDetailScreen({}, HistoryViewModel())
}