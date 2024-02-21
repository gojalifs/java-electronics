package com.ngapak.dev.javaelectronics.features.product.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Transaction
import com.ngapak.dev.javaelectronics.features.checkout.presentation.CheckoutViewModel
import com.ngapak.dev.javaelectronics.features.product.domain.model.ProductDetail
import com.ngapak.dev.javaelectronics.utils.Converter.toRupiah

@Composable
fun BottomSheetSelectQuantity(
    productDetail: ProductDetail,
    checkoutViewModel: CheckoutViewModel,
    navigateToCheckout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var price by remember { mutableStateOf(productDetail.price) }
    var qty by remember { mutableStateOf(1) }
    var isQtyMoreThanOne by remember { mutableStateOf(false) }

    Column(modifier.padding(16.dp, 16.dp)) {
        Row {
            Column(modifier.weight(1f)) {
                Text(text = "${productDetail.name}", fontSize = 24.sp)
                Text(text = "Price : ${productDetail.price?.toRupiah()}", fontSize = 18.sp)
                Text(text = "Stock : ${productDetail.stock}", fontSize = 18.sp)
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${productDetail.imageUrl}")
                    .crossfade(true)
                    .build(),
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
                    .size(96.dp),
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Quantity",
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
            IconButton(
                onClick = {
                    if (qty == 2) {
                        isQtyMoreThanOne = false
                    }

                    if (qty > 1) {
                        qty--
                        price = productDetail.price?.let { price?.minus(it) }
                    } else {
                        isQtyMoreThanOne = false
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically),
                enabled = isQtyMoreThanOne,
            ) {
                Icon(Icons.Rounded.Remove, contentDescription = "Decrease Quantity")
            }
            Text(text = "$qty", modifier = Modifier.align(Alignment.CenterVertically))
            IconButton(onClick = {
                qty++
                price = productDetail.price?.let { price?.plus(it) }
                if (!isQtyMoreThanOne) isQtyMoreThanOne = true
            }, modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(Icons.Rounded.Add, contentDescription = "Add Quantity by one")
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Total ")
            Text(
                text = "${price?.toRupiah()}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
            )
            Button(
                onClick = {
                    val transaction = Transaction(
                        product = productDetail,
                        totalPrice = price,
                        qty = qty,
                    )
                    checkoutViewModel.checkout(transaction)
                    navigateToCheckout()
                }) {
                Text(text = "Checkout")
            }
        }
    }
}