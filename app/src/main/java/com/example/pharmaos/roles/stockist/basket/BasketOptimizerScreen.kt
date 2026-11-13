package com.example.pharmaos.roles.stockist.basket

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Teal = Color(0xFF008080)
private val LightBackground = Color(0xFFF1F5FF)
private val DarkText = Color(0xFF333333)
private val GreyText = Color(0xFF777777)
private val Green = Color(0xFF15803D)
private val Orange = Color(0xFFB45309)

@Composable
fun BasketOptimizerScreen() {

    val context = LocalContext.current

    val basketItems = BasketManager.items.toList()

    val optimization = remember(basketItems) {
        BasketOptimizer.optimizeBasket(
            requestedItems = basketItems,
            allOffers = SupplierOfferRepository.getAllOffers()
        )
    }

    var recommendationsApplied by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
    ) {

        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Teal,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Best-Buy Basket Optimizer",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Compare supplier offers and find the most economical way to purchase your medicines.",
                fontSize = 13.sp,
                color = GreyText
            )
        }

        if (basketItems.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBasket,
                        contentDescription = null,
                        tint = GreyText,
                        modifier = Modifier.size(55.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Your basket is empty",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    Text(
                        text = "Add medicines to your basket to optimize your purchase.",
                        fontSize = 13.sp,
                        color = GreyText
                    )
                }
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                // Basket information
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
                                text = "Your Basket",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${basketItems.size} medicine items",
                                fontSize = 13.sp,
                                color = GreyText
                            )

                            Text(
                                text = "Total quantity: ${
                                    basketItems.sumOf { it.quantity }
                                }",
                                fontSize = 13.sp,
                                color = GreyText
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Current basket value: ₹${
                                    String.format(
                                        "%.2f",
                                        basketItems.sumOf { it.totalPrice }
                                    )
                                }",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Teal
                            )
                        }
                    }
                }

                // Recommendations heading
                item {
                    Text(
                        text = "Optimized Supplier Recommendations",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                }

                if (optimization.selections.isEmpty()) {

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Text(
                                text = "No suitable supplier offers were found for your current basket.",
                                modifier = Modifier.padding(16.dp),
                                color = GreyText,
                                fontSize = 14.sp
                            )
                        }
                    }

                } else {

                    items(optimization.selections) { selection ->

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
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
                                        text = selection.medicineName,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DarkText,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Surface(
                                        color = Color(0xFFE6F4EA),
                                        shape = RoundedCornerShape(20.dp)
                                    ) {
                                        Text(
                                            text = "Best Buy",
                                            color = Green,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(
                                                horizontal = 10.dp,
                                                vertical = 5.dp
                                            )
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                DetailRow(
                                    label = "Recommended brand",
                                    value = selection.brand
                                )

                                DetailRow(
                                    label = "Supplier",
                                    value = selection.supplierName
                                )

                                DetailRow(
                                    label = "Order quantity",
                                    value = selection.orderQuantity.toString()
                                )

                                DetailRow(
                                    label = "Unit price",
                                    value = "₹${String.format("%.2f", selection.unitPrice)}"
                                )

                                DetailRow(
                                    label = "Medicine cost",
                                    value = "₹${String.format("%.2f", selection.medicineCost)}"
                                )

                                DetailRow(
                                    label = "Delivery charge",
                                    value = "₹${String.format("%.2f", selection.deliveryCharge)}"
                                )

                                DetailRow(
                                    label = "Estimated delivery",
                                    value = "${selection.estimatedDeliveryDays} days"
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color = Color(0xFFE5E7EB)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Total cost",
                                        fontWeight = FontWeight.Bold,
                                        color = DarkText
                                    )

                                    Text(
                                        text = "₹${String.format("%.2f", selection.totalCost)}",
                                        fontWeight = FontWeight.Bold,
                                        color = Teal
                                    )
                                }
                            }
                        }
                    }
                }

                // Unavailable medicines
                if (optimization.unavailableMedicines.isNotEmpty()) {

                    item {

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFF7ED)
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = Orange
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "Offers Unavailable",
                                        fontWeight = FontWeight.Bold,
                                        color = Orange
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "No suitable offer was found for:",
                                    fontSize = 13.sp,
                                    color = DarkText
                                )

                                optimization.unavailableMedicines.forEach {
                                    Text(
                                        text = "• $it",
                                        fontSize = 13.sp,
                                        color = DarkText
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "These medicines will remain unchanged in your basket.",
                                    fontSize = 12.sp,
                                    color = GreyText
                                )
                            }
                        }
                    }
                }

                // Total
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
                                text = "Optimized Basket Summary",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Optimized total",
                                    fontSize = 15.sp,
                                    color = DarkText
                                )

                                Text(
                                    text = "₹${String.format("%.2f", optimization.totalCost)}",
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Teal
                                )
                            }
                        }
                    }
                }

                // Apply recommendations
                if (optimization.selections.isNotEmpty()) {

                    item {

                        Button(
                            onClick = {

                                BasketManager.applyOptimizedSelections(
                                    optimization.selections
                                )

                                recommendationsApplied = true

                                Toast.makeText(
                                    context,
                                    "Optimized recommendations applied to your basket",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Teal
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = if (recommendationsApplied)
                                    "Apply Recommendations Again"
                                else
                                    "Apply Recommendations",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Applying recommendations updates the supplier, brand, price and quantity in your basket. Unavailable medicines remain unchanged.",
                            fontSize = 12.sp,
                            color = GreyText
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = label,
            fontSize = 13.sp,
            color = GreyText,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = DarkText
        )
    }
}