package com.example.pharmaos.roles.stockist.basket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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

private val BasketTeal = Color(0xFF008080)
private val BasketBackground = Color(0xFFF1F5FF)
private val BasketText = Color(0xFF333333)
private val BasketGrey = Color(0xFF999999)

@Composable
fun BasketScreen() {

    // Read shared basket data
    val basketItems = BasketManager.items
    val subtotal = BasketManager.getSubtotal()
    val totalQuantity = BasketManager.getTotalQuantity()

    var checkoutMessage by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BasketBackground)
            .padding(16.dp)
    ) {

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
                    Text("Clear All", color = Color.Red)
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (basketItems.isEmpty()) {

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

        } else {

            basketItems.toList().forEach { item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {

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

            // Basket summary
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
                        Text("Total quantity", color = BasketGrey)
                        Text(totalQuantity.toString())
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal", color = BasketGrey)
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

                    Button(
                        onClick = {
                            checkoutMessage = true
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

        if (checkoutMessage) {
            AlertDialog(
                onDismissRequest = {
                    checkoutMessage = false
                },
                title = {
                    Text("Checkout")
                },
                text = {
                    Text(
                        "Your basket is ready. The checkout workflow will be connected next."
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            checkoutMessage = false
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
