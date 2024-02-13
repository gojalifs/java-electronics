package com.ngapak.dev.javaelectronics.features.history.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Transaction
import com.ngapak.dev.javaelectronics.utils.Converter.toRupiah
import kotlinx.coroutines.launch

private val tabs = listOf(
    TabItem(
        title = "Packing"
    ) { navigateToDetail, viewModel ->
        OrderHistoryBody(
            type = "packing",
            viewModel,
            { navigateToDetail() }
        )
    },
    TabItem(
        title = "Delivering",
        screen = { navigateToDetail, viewModel ->
            OrderHistoryBody(type = "delivering", viewModel, { navigateToDetail() })
        }
    ),
    TabItem(
        title = "Completed",
        screen = { navigateToDetail, viewModel ->
            OrderHistoryBody(type = "completed", viewModel, { navigateToDetail() })
        }
    ),
    TabItem(
        "Cancelled",
        screen = { navigateToDetail, viewModel ->
            OrderHistoryBody(type = "cancelled", viewModel, { navigateToDetail() })
        },
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    navigateToDetail: (isDeliv: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()
    Scaffold(modifier = modifier) {
        Column {
            TabRow(selectedTabIndex = pagerState.currentPage, modifier = modifier.padding(it)) {
                tabs.mapIndexed { index, tabItem ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        text = { Text(text = tabItem.title) },
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                beyondBoundsPageCount = tabs.size,
                modifier = modifier
            ) {
                if (pagerState.currentPage == 1) {
                    tabs[it].screen({ navigateToDetail(true) }, viewModel)
                } else {
                    tabs[it].screen({ navigateToDetail(false) }, viewModel)
                }
            }
        }
    }
}

@Composable
fun OrderHistoryBody(
    type: String,
    viewModel: HistoryViewModel,
    navigateToDetail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize()) {
        when (type) {
            "packing" -> viewModel.packing.map {
                TransactionCard(transaction = it, viewModel, { navigateToDetail() })
            }

            "delivering" -> viewModel.delivering.map {
                TransactionCard(
                    transaction = it,
                    viewModel,
                    { navigateToDetail() },
                    isDelivering = true
                )
            }

            "completed" -> viewModel.completed.map {
                TransactionCard(transaction = it, viewModel, { navigateToDetail() })
            }

            "cancelled" -> viewModel.cancelled.map {
                TransactionCard(transaction = it, viewModel, { navigateToDetail() })
            }
        }
    }
}

@Composable
fun TransactionCard(
    transaction: Transaction,
    historyViewModel: HistoryViewModel,
    navigateToDetail: () -> Unit,
    modifier: Modifier = Modifier,
    isDelivering: Boolean = false,
) {
    Card(onClick = {
        historyViewModel.setOrders(transaction)
        navigateToDetail()
    }, modifier = modifier.padding(16.dp, 8.dp)) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "${transaction.name}")
            Text(text = "${transaction.totalPrice?.toRupiah()}", color = Color(100, 100, 100, 255))
        }
    }
}