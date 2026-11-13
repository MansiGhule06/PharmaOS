package com.example.pharmaos.roles.stockist.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val OrdersTeal = Color(0xFF008080)
private val OrdersBackground = Color(0xFFF1F5FF)
private val OrdersText = Color(0xFF333333)
private val OrdersGrey = Color(0xFF999999)

@Composable
fun OrdersScreen(
    onOrderClick: (String) -> Unit = {}
) {
    val allOrders = OrderRepository.getAllOrders()

    var searchText by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val filters = listOf(
        "All",
        "Pending",
        "Confirmed",
        "Dispatched",
        "Delivered",
        "Cancelled"
    )

    val filteredOrders = allOrders.filter { order ->

        val matchesSearch =
            order.orderId.contains(searchText, ignoreCase = true) ||
                    order.supplierName.contains(searchText, ignoreCase = true)

        val matchesStatus =
            selectedFilter == "All" ||
                    order.status.name.equals(selectedFilter, ignoreCase = true)

        matchesSearch && matchesStatus
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OrdersBackground)
    ) {

        Text(
            text = "My Orders",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = OrdersText,
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp,
                bottom = 12.dp
            )
        )

        // Order Summary
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OrderSummaryCard(
                title = "Total",
                count = allOrders.size,
                modifier = Modifier.weight(1f)
            )

            OrderSummaryCard(
                title = "Pending",
                count = allOrders.count {
                    it.status == OrderStatus.PENDING
                },
                modifier = Modifier.weight(1f)
            )

            OrderSummaryCard(
                title = "Delivered",
                count = allOrders.count {
                    it.status == OrderStatus.DELIVERED
                },
                modifier = Modifier.weight(1f)
            )
        }

        // Search Bar
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {
                Text("Search order ID or supplier")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrdersTeal,
                unfocusedBorderColor = Color.LightGray
            )
        )

        // Status Filters
        androidx.compose.foundation.lazy.LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = OrdersTeal,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Orders List
        if (filteredOrders.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No orders found",
                    color = OrdersGrey,
                    fontSize = 16.sp
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = filteredOrders,
                    key = { it.orderId }
                ) { order ->

                    OrderCard(
                        order = order,
                        onClick = {
                            onOrderClick(order.orderId)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderSummaryCard(
    title: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = OrdersTeal
            )

            Text(
                text = title,
                fontSize = 12.sp,
                color = OrdersGrey
            )
        }
    }
}

@Composable
private fun OrderCard(
    order: Order,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.orderId,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = OrdersText
                )

                OrderStatusBadge(order.status)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = null,
                    tint = OrdersTeal,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = order.supplierName,
                    fontSize = 14.sp,
                    color = OrdersText
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${order.items.size} medicine(s) • ${order.orderDate}",
                fontSize = 12.sp,
                color = OrdersGrey
            )

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(color = Color(0xFFEEEEEE))

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total Amount",
                        fontSize = 12.sp,
                        color = OrdersGrey
                    )

                    Text(
                        text = "₹${"%.2f".format(order.totalAmount)}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrdersTeal
                    )
                }

                Text(
                    text = "View Details  →",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = OrdersTeal
                )
            }
        }
    }
}

@Composable
private fun OrderStatusBadge(status: OrderStatus) {

    val statusColor = when (status) {
        OrderStatus.PENDING -> Color(0xFFB45309)
        OrderStatus.CONFIRMED -> Color(0xFF2563EB)
        OrderStatus.DISPATCHED -> Color(0xFF7C3AED)
        OrderStatus.DELIVERED -> Color(0xFF15803D)
        OrderStatus.CANCELLED -> Color(0xFFB91C1C)
    }

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = statusColor.copy(alpha = 0.12f)
    ) {
        Text(
            text = status.name.lowercase()
                .replaceFirstChar { it.uppercase() },
            color = statusColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 6.dp
            )
        )
    }
}
