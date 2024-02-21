package com.ngapak.dev.javaelectronics.features.product.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCartCheckout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ngapak.dev.javaelectronics.core.Injection
import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.checkout.presentation.CheckoutViewModel
import com.ngapak.dev.javaelectronics.features.checkout.presentation.CheckoutViewModelFactory
import com.ngapak.dev.javaelectronics.features.product.domain.model.ProductDetail
import com.ngapak.dev.javaelectronics.ui.reusable.ShowError
import com.ngapak.dev.javaelectronics.ui.reusable.ShowLoading
import com.ngapak.dev.javaelectronics.utils.Converter.toRupiah

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String?,
    modifier: Modifier = Modifier,
    navigateToCheckout: () -> Unit = { },
    checkoutViewModel: CheckoutViewModel = viewModel(factory = CheckoutViewModelFactory.getInstance()),
    viewModel: ProductDetailViewModel = viewModel(
        factory = ProductDetailViewModelFactory(Injection.provideProductDetailUseCase())
    ),
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (productId != null)
            viewModel.getProductDetail(productId)
    }

    Scaffold(
        floatingActionButton = { BuyFab(onClick = { showBottomSheet = true }) },
        modifier = modifier.padding(16.dp)
    ) {
        viewModel.productDetail.collectAsState().value.let { resource ->
            when (resource) {
                is Resource.Loading -> {
                    ShowLoading(modifier = modifier)
                }

                is Resource.Success -> {
                    resource.data?.let { productDetail ->
                        if (showBottomSheet) {
                            ModalBottomSheet(
                                onDismissRequest = { showBottomSheet = false },
                                sheetState = sheetState
                            ) {
                                BottomSheetSelectQuantity(
                                    productDetail = productDetail,
                                    modifier = modifier.padding(
                                        bottom = WindowInsets.navigationBars.asPaddingValues()
                                            .calculateBottomPadding()
                                    ),
                                    navigateToCheckout = navigateToCheckout,
                                    checkoutViewModel = checkoutViewModel,
                                )
                            }
                        }
                        ProductDetailBody(
                            modifier = modifier.padding(it),
                            productDetail = productDetail,
                        )
                    }
                }

                is Resource.Error -> {
                    ShowError(modifier = modifier, msg = resource.message.toString())
                }
            }
        }
    }
}

@Composable
fun BuyFab(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text(text = "BUY NOW") },
        icon = { Icon(Icons.Rounded.ShoppingCartCheckout, contentDescription = "BUY NOW") },
        onClick = {
            onClick()
        })
}

@Composable
fun ProductDetailBody(modifier: Modifier, productDetail: ProductDetail) {
    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${productDetail.imageUrl}")
                .crossfade(true)
                .build(),
            contentDescription = "${productDetail.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .align(Alignment.CenterHorizontally),
        )
        Text(text = "${productDetail.name}", fontSize = 30.sp)
        Text(text = "${productDetail.price?.toRupiah()}", color = Color.Black.copy(alpha = 0.8f))
        Text(text = "Description", fontSize = 20.sp, modifier = modifier.padding(top = 8.dp))
        HorizontalDivider()
        Text(text = "${productDetail.description}")
    }
}