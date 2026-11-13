package com.example.pharmaos.roles.stockist.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DetailTeal = Color(0xFF008080)
private val DetailBackground = Color(0xFFF1F5FF)
private val DetailText = Color(0xFF333333)
private val DetailGrey = Color(0xFF999999)

@Composable
fun OrderDetailsScreen(
    order: Order,
    onBack: () -> Unit = {},
    onTrackOrder: (String) -> Unit = {},
    onReorder: (Order) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DetailBackground)
    ) {

        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = DetailText
                )
            }

            Text(
                text = "Order Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DetailText
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // Order Information
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
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
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = DetailText
                        )

                        DetailStatusBadge(order.status)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    DetailInfoRow(
                        label = "Order Date",
                        value = order.orderDate
                    )

                    DetailInfoRow(
                        label = "Expected Delivery",
                        value = order.expectedDelivery
                    )

                    DetailInfoRow(
                        label = "Supplier",
                        value = order.supplierName
                    )
                }
            }

            // Medicine Items
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Ordered Medicines",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = DetailText
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    order.items.forEach { item ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = null,
                                tint = DetailTeal,
                                modifier = Modifier.size(24.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = item.medicineName,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    color = DetailText
                                )

                                Text(
                                    text = item.brand,
                                    fontSize = 12.sp,
                                    color = DetailGrey
                                )

                                Text(
                                    text = "Qty: ${item.quantity} × ₹${"%.2f".format(item.unitPrice)}",
                                    fontSize = 12.sp,
                                    color = DetailGrey
                                )
                            }

                            Text(
                                text = "₹${"%.2f".format(item.totalPrice)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = DetailText
                            )
                        }

                        HorizontalDivider(
                            color = Color(0xFFEEEEEE)
                        )
                    }
                }
            }

            // Payment Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Order Summary",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = DetailText
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    DetailInfoRow(
                        label = "Total Medicines",
                        value = order.items.sumOf { it.quantity }.toString()
                    )

                    DetailInfoRow(
                        label = "Total Items",
                        value = order.items.size.toString()
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Color(0xFFEEEEEE)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Amount",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DetailText
                        )

                        Text(
                            text = "₹${"%.2f".format(order.totalAmount)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = DetailTeal
                        )
                    }
                }
            }

            // Track Order Button
            if (order.status != OrderStatus.CANCELLED &&
                order.status != OrderStatus.DELIVERED
            ) {
                Button(
                    onClick = {
                        onTrackOrder(order.orderId)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DetailTeal
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalShipping,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Track Order")
                }
            }

            // Reorder Button
            if (order.status == OrderStatus.DELIVERED ||
                order.status == OrderStatus.CANCELLED
            ) {
                OutlinedButton(
                    onClick = {
                        onReorder(order)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = DetailTeal
                    )
                ) {
                    Text("Reorder Medicines")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun DetailInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = DetailGrey
        )

        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = DetailText
        )
    }
}

@Composable
private fun DetailStatusBadge(
    status: OrderStatus
) {

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
