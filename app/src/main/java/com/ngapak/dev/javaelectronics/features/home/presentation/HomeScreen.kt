package com.ngapak.dev.javaelectronics.features.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.History
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ngapak.dev.javaelectronics.core.Injection
import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.home.domain.model.Product
import com.ngapak.dev.javaelectronics.features.product.presentation.ProductDetailScreen
import com.ngapak.dev.javaelectronics.ui.navigation.BottomBarItem
import com.ngapak.dev.javaelectronics.ui.navigation.JavaElectronicsNavigation
import com.ngapak.dev.javaelectronics.ui.navigation.Screen
import com.ngapak.dev.javaelectronics.ui.reusable.ShowError
import com.ngapak.dev.javaelectronics.ui.reusable.ShowLoading

@Composable
fun MainAHomeAppScreen(
    navigateToDetail: (id: String) -> Unit,
    navigateToHistory: () -> Unit,
    toOrderPage: Boolean = false,
    homeNavController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = { AppBar() },
        bottomBar = { BottomNavBar(homeNavController, navigateToHistory) }
    ) { padding ->
        NavHost(
            navController = homeNavController, startDestination = if (toOrderPage) {
                Screen.Orders.route
            } else {
                Screen.Home.route
            }
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToDetail, modifier = Modifier.padding(padding))
            }
            composable(Screen.Orders.route) {
                Text(text = "ORDER HISTORY", modifier = Modifier.padding(padding))
            }
            composable(Screen.Profile.route) {
                Text(text = "Profile Screen", modifier = Modifier.padding(padding))
            }
            composable(
                JavaElectronicsNavigation.PRODUCT_DETAIL_ROUTE, // product
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) {
                ProductDetailScreen(it.arguments?.getString("productId"))
            }
        }
    }
}

@Composable
fun HomeScreen(
    navigateToDetail: (id: String) -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(Injection.provideHomeUseCase())
    ),
    modifier: Modifier
) {
    viewModel.products.collectAsState(null).value.let { resource ->
        when (resource) {
            is Resource.Loading -> {
                ShowLoading(modifier = modifier)
            }

            is Resource.Success -> {
                ProductsGrid(
                    modifier = modifier,
                    products = resource.data ?: emptyList(),
                    toProductDetail = {
                        navigateToDetail(it.documentId ?: "kosong")
                    }
                )
            }

            is Resource.Error -> {
                ShowError(modifier = modifier, msg = resource.message.toString())
            }

            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(title = { Text(text = "Java Electronics") })
}

@Composable
fun BottomNavBar(navController: NavHostController, navigateToHistory: () -> Unit) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            BottomBarItem(
                title = "Home",
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            BottomBarItem(
                title = "Orders",
                icon = Icons.Rounded.History,
                screen = Screen.Orders
            ),
            BottomBarItem(
                title = "Profile",
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )

        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    if (item.screen.route == "orders") {
                        navigateToHistory()
                    } else {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                icon = { item.icon },
                label = { Text(text = item.title) },
            )
        }
    }
}

@Composable
fun ProductsGrid(
    modifier: Modifier = Modifier,
    products: List<Product>,
    toProductDetail: (Product) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        items(products, key = { it.documentId ?: "0" }) { product ->
            ProductCard(product = product, toProductDetail = toProductDetail)
        }
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    toProductDetail: (Product) -> Unit
) {
    Card(onClick = {
        toProductDetail(product)
    }, modifier = modifier.height(150.dp)) {
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://static.promediateknologi.id/crop/0x0:0x0/0x0/webp/photo/p2/01/2023/09/06/images-2023-09-05T003002821-2527323090.jpeg")
                    .crossfade(true)
                    .build(),
                contentDescription = product.name,
                contentScale = ContentScale.FillWidth,
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
            Text(
                text = "${product.name}",
                Modifier.align(Alignment.CenterHorizontally),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
