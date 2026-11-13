
package com.example.pharmaos.roles.stockist.basket

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pharmaos.roles.stockist.orders.CheckoutScreen

private val BasketTeal = Color(0xFF008080)
private val BasketBackground = Color(0xFFF1F5FF)
private val BasketText = Color(0xFF333333)
private val BasketGrey = Color(0xFF999999)

@Composable
fun BasketScreen(
    onOrderPlaced: (List<String>) -> Unit = {}
) {

    var showOptimizer by remember { mutableStateOf(false) }
    var showCheckout by remember { mutableStateOf(false) }

    // Handle Android back button
    BackHandler(enabled = showOptimizer || showCheckout) {
        if (showCheckout) {
            showCheckout = false
        } else if (showOptimizer) {
            showOptimizer = false
        }
    }

    // CHECKOUT SCREEN
    if (showCheckout) {

        CheckoutScreen(
            onBack = {
                showCheckout = false
            },
            onOrderPlaced = { orderIds ->
                showCheckout = false
                onOrderPlaced(orderIds)
            }
        )

    }

    // BEST-BUY OPTIMIZER SCREEN
    else if (showOptimizer) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BasketBackground)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        showOptimizer = false
                    }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back to basket",
                        tint = BasketTeal
                    )
                }

                Text(
                    text = "Back to My Basket",
                    color = BasketTeal,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                BasketOptimizerScreen()
            }
        }

    }

    // MAIN BASKET SCREEN
    else {

        val basketItems = BasketManager.items
        val subtotal = BasketManager.getSubtotal()
        val totalQuantity = BasketManager.getTotalQuantity()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BasketBackground),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Basket Header
            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {

                        Text(
                            "My Smart Basket",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = BasketText
                        )

                        Text(
                            "$totalQuantity items selected",
                            fontSize = 13.sp,
                            color = BasketGrey
                        )
                    }

                    if (basketItems.isNotEmpty()) {

                        TextButton(
                            onClick = {
                                BasketManager.clearBasket()
                            }
                        ) {
                            Text(
                                "Clear All",
                                color = Color.Red
                            )
                        }
                    }
                }
            }

            // Best-Buy Basket Optimizer
            item {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showOptimizer = true
                        },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = BasketTeal
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                "Best-Buy Basket Optimizer",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                "Compare supplier offers and find a cost-effective basket.",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 12.sp
                            )
                        }

                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = "Open optimizer",
                            tint = Color.White
                        )
                    }
                }
            }

            // Empty Basket
            if (basketItems.isEmpty()) {

                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                "Your basket is empty",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BasketText
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                "Add medicines from the catalogue to start your order.",
                                fontSize = 13.sp,
                                color = BasketGrey
                            )
                        }
                    }
                }

            } else {

                // Medicine Cards
                items(
                    items = basketItems,
                    key = { it.id }
                ) { item ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(15.dp)
                        ) {

                            // Medicine details
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        item.medicineName,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BasketText
                                    )

                                    Text(
                                        "Brand: ${item.brand}",
                                        fontSize = 12.sp,
                                        color = BasketGrey
                                    )

                                    if (item.supplierName.isNotBlank()) {
                                        Text(
                                            "Supplier: ${item.supplierName}",
                                            fontSize = 12.sp,
                                            color = BasketTeal,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }

                                    Text(
                                        "Salt: ${item.salt}",
                                        fontSize = 12.sp,
                                        color = BasketGrey
                                    )

                                    Text(
                                        "Strength: ${item.strength}",
                                        fontSize = 12.sp,
                                        color = BasketGrey
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        BasketManager.removeMedicine(item.id)
                                    }
                                ) {

                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Remove medicine",
                                        tint = Color.Red
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Price and quantity
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column {

                                    Text(
                                        "₹${"%.2f".format(item.unitPrice)} / unit",
                                        fontSize = 12.sp,
                                        color = BasketGrey
                                    )

                                    Text(
                                        "₹${"%.2f".format(item.totalPrice)}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BasketTeal
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    IconButton(
                                        onClick = {
                                            BasketManager.decreaseQuantity(item.id)
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.Remove,
                                            contentDescription = "Decrease quantity"
                                        )
                                    }

                                    Text(
                                        item.quantity.toString(),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    IconButton(
                                        onClick = {
                                            BasketManager.increaseQuantity(item.id)
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = "Increase quantity"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Basket Summary
                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                "Order Summary",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = BasketText
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    "Total quantity",
                                    color = BasketGrey
                                )

                                Text(totalQuantity.toString())
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    "Subtotal",
                                    color = BasketGrey
                                )

                                Text("₹${"%.2f".format(subtotal)}")
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            HorizontalDivider()

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    "Estimated Total",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    "₹${"%.2f".format(subtotal)}",
                                    fontWeight = FontWeight.Bold,
                                    color = BasketTeal,
                                    fontSize = 19.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            // CONNECTED CHECKOUT BUTTON
                            Button(
                                onClick = {
                                    showCheckout = true
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BasketTeal
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {

                                Text("Proceed to Checkout")
                            }
                        }
                    }
                }
            }

            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
