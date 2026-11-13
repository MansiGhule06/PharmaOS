
package com.example.pharmaos.roles.stockist.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pharmaos.roles.stockist.basket.BasketManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val CheckoutTeal = Color(0xFF008080)
private val CheckoutBackground = Color(0xFFF1F5FF)
private val CheckoutText = Color(0xFF333333)
private val CheckoutGrey = Color(0xFF999999)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    onBack: () -> Unit = {},
    onOrderPlaced: (List<String>) -> Unit = {}
) {

    var customerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var deliveryAddress by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val basketItems = BasketManager.items
    val subtotal = BasketManager.getSubtotal()
    val totalQuantity = BasketManager.getTotalQuantity()

    val supplierGroups = basketItems.groupBy {
        it.supplierName.ifBlank { "Supplier not assigned" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CheckoutBackground)
    ) {

        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = CheckoutTeal
                )
            }

            Text(
                text = "Checkout",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CheckoutText
            )
        }

        if (basketItems.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Your basket is empty.",
                    color = CheckoutGrey
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                // Delivery details
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = CheckoutTeal
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    "Delivery Details",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CheckoutText
                                )
                            }

                            OutlinedTextField(
                                value = customerName,
                                onValueChange = { customerName = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Contact Person Name") },
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = phoneNumber,
                                onValueChange = {
                                    phoneNumber = it.filter { char ->
                                        char.isDigit()
                                    }.take(10)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Mobile Number") },
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = deliveryAddress,
                                onValueChange = { deliveryAddress = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Complete Delivery Address") },
                                minLines = 3
                            )
                        }
                    }
                }

                // Order items heading
                item {
                    Text(
                        text = "Order Items",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = CheckoutText
                    )
                }

                // Medicine list
                items(
                    items = basketItems,
                    key = { it.id }
                ) { item ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    item.medicineName,
                                    fontWeight = FontWeight.Bold,
                                    color = CheckoutText
                                )

                                Text(
                                    "Brand: ${item.brand}",
                                    fontSize = 12.sp,
                                    color = CheckoutGrey
                                )

                                Text(
                                    "Quantity: ${item.quantity}",
                                    fontSize = 12.sp,
                                    color = CheckoutGrey
                                )

                                Text(
                                    "Supplier: ${item.supplierName.ifBlank { "Not assigned" }}",
                                    fontSize = 12.sp,
                                    color = CheckoutTeal
                                )
                            }

                            Text(
                                "₹${"%.2f".format(item.totalPrice)}",
                                fontWeight = FontWeight.Bold,
                                color = CheckoutTeal
                            )
                        }
                    }
                }

                // Supplier summary
                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Text(
                                "Supplier Summary",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = CheckoutText
                            )

                            supplierGroups.forEach { (supplier, items) ->

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Text(
                                        supplier,
                                        color = CheckoutGrey,
                                        fontSize = 12.sp,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Text(
                                        "₹${"%.2f".format(items.sumOf { it.totalPrice })}",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Payment summary
                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            Text(
                                "Payment Summary",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = CheckoutText
                            )

                            SummaryRow(
                                label = "Total items",
                                value = totalQuantity.toString()
                            )

                            SummaryRow(
                                label = "Subtotal",
                                value = "₹${"%.2f".format(subtotal)}"
                            )

                            HorizontalDivider()

                            SummaryRow(
                                label = "Total Amount",
                                value = "₹${"%.2f".format(subtotal)}",
                                isTotal = true
                            )

                            Text(
                                "Payment method: To be confirmed with supplier",
                                fontSize = 11.sp,
                                color = CheckoutGrey
                            )
                        }
                    }
                }

                // Validation error
                if (errorMessage.isNotEmpty()) {
                    item {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Place order button
            Surface(
                color = Color.White,
                shadowElevation = 6.dp
            ) {

                Button(
                    onClick = {

                        when {

                            customerName.isBlank() -> {
                                errorMessage =
                                    "Please enter the contact person's name."
                            }

                            phoneNumber.length != 10 -> {
                                errorMessage =
                                    "Please enter a valid 10-digit mobile number."
                            }

                            deliveryAddress.isBlank() -> {
                                errorMessage =
                                    "Please enter the delivery address."
                            }

                            basketItems.isEmpty() -> {
                                errorMessage = "Your basket is empty."
                            }

                            else -> {

                                errorMessage = ""

                                val orderDateFormat = SimpleDateFormat(
                                    "dd MMM yyyy",
                                    Locale.getDefault()
                                )

                                val orderDate =
                                    orderDateFormat.format(Date())

                                val calendar = Calendar.getInstance()
                                calendar.add(Calendar.DAY_OF_YEAR, 3)

                                val expectedDelivery =
                                    orderDateFormat.format(calendar.time)

                                val placedOrderIds = mutableListOf<String>()

                                supplierGroups.forEach { (supplier, items) ->

                                    val order = Order(
                                        orderId = OrderRepository.generateOrderId(),
                                        supplierName = supplier,
                                        orderDate = orderDate,
                                        expectedDelivery = expectedDelivery,
                                        totalAmount = items.sumOf {
                                            it.totalPrice
                                        },
                                        status = OrderStatus.PENDING,

                                        items = items.map { item ->
                                            OrderItem(
                                                medicineName = item.medicineName,
                                                brand = item.brand,
                                                quantity = item.quantity,
                                                unitPrice = item.unitPrice
                                            )
                                        },

                                        // Save delivery details
                                        deliveryContactName = customerName.trim(),
                                        deliveryPhone = phoneNumber.trim(),
                                        deliveryAddress = deliveryAddress.trim()
                                    )

                                    OrderRepository.addOrder(order)

                                    placedOrderIds.add(order.orderId)
                                }

                                BasketManager.clearBasket()

                                onOrderPlaced(placedOrderIds)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(52.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CheckoutTeal
                    ),
                    enabled = basketItems.isNotEmpty()
                ) {

                    Icon(
                        Icons.Default.ShoppingBag,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        "Place Order",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    isTotal: Boolean = false
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            label,
            color = if (isTotal) CheckoutText else CheckoutGrey,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )

        Text(
            value,
            color = if (isTotal) CheckoutTeal else CheckoutText,
            fontWeight = FontWeight.Bold,
            fontSize = if (isTotal) 18.sp else 14.sp
        )
    }
}
