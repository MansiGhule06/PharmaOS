package com.example.pharmaos.roles.stockist.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TrackingTeal = Color(0xFF008080)
private val TrackingBackground = Color(0xFFF1F5FF)
private val TrackingText = Color(0xFF333333)
private val TrackingGrey = Color(0xFF999999)

@Composable
fun OrderTrackingScreen(
    order: Order,
    onBack: () -> Unit = {}
) {

    val steps = listOf(
        OrderStatus.PENDING,
        OrderStatus.CONFIRMED,
        OrderStatus.DISPATCHED,
        OrderStatus.DELIVERED
    )

    val currentStep = when (order.status) {
        OrderStatus.PENDING -> 0
        OrderStatus.CONFIRMED -> 1
        OrderStatus.DISPATCHED -> 2
        OrderStatus.DELIVERED -> 3
        OrderStatus.CANCELLED -> -1
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TrackingBackground)
    ) {

        // Header
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
                    tint = TrackingText
                )
            }

            Text(
                text = "Track Order",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TrackingText
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Order ID",
                        color = TrackingGrey,
                        fontSize = 13.sp
                    )

                    Text(
                        text = order.orderId,
                        color = TrackingText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Supplier",
                        color = TrackingGrey,
                        fontSize = 13.sp
                    )

                    Text(
                        text = order.supplierName,
                        color = TrackingText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Expected Delivery",
                        color = TrackingGrey,
                        fontSize = 13.sp
                    )

                    Text(
                        text = order.expectedDelivery,
                        color = TrackingTeal,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Tracking Progress
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Order Progress",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TrackingText
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    if (order.status == OrderStatus.CANCELLED) {

                        Text(
                            text = "Order Cancelled",
                            color = Color(0xFFB91C1C),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "This order has been cancelled.",
                            color = TrackingGrey,
                            fontSize = 13.sp
                        )

                    } else {

                        steps.forEachIndexed { index, status ->

                            val completed = index <= currentStep

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top
                            ) {

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .background(
                                                color = if (completed)
                                                    TrackingTeal
                                                else
                                                    Color(0xFFE5E7EB),
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        if (completed) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Completed",
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        } else {
                                            Text(
                                                text = (index + 1).toString(),
                                                color = TrackingGrey,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    if (index != steps.lastIndex) {
                                        Box(
                                            modifier = Modifier
                                                .width(2.dp)
                                                .height(48.dp)
                                                .background(
                                                    if (index < currentStep)
                                                        TrackingTeal
                                                    else
                                                        Color(0xFFE5E7EB)
                                                )
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(
                                    modifier = Modifier.padding(
                                        top = 3.dp,
                                        bottom = 30.dp
                                    )
                                ) {

                                    Text(
                                        text = when (status) {
                                            OrderStatus.PENDING -> "Order Placed"
                                            OrderStatus.CONFIRMED -> "Order Confirmed"
                                            OrderStatus.DISPATCHED -> "Order Dispatched"
                                            OrderStatus.DELIVERED -> "Order Delivered"
                                            else -> ""
                                        },
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (completed)
                                            TrackingText
                                        else
                                            TrackingGrey
                                    )

                                    Text(
                                        text = when (status) {
                                            OrderStatus.PENDING ->
                                                "Your order has been received."
                                            OrderStatus.CONFIRMED ->
                                                "Supplier has confirmed your order."
                                            OrderStatus.DISPATCHED ->
                                                "Your order is on its way."
                                            OrderStatus.DELIVERED ->
                                                "Your order has been delivered."
                                            else -> ""
                                        },
                                        fontSize = 12.sp,
                                        color = TrackingGrey
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Order Amount
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {
                        Text(
                            text = "Order Amount",
                            fontSize = 13.sp,
                            color = TrackingGrey
                        )

                        Text(
                            text = "₹${"%.2f".format(order.totalAmount)}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TrackingTeal
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.LocalShipping,
                        contentDescription = null,
                        tint = TrackingTeal,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}
