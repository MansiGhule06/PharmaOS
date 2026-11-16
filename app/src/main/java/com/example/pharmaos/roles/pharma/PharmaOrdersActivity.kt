
package com.example.pharmaos.roles.pharma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class PharmaOrdersActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                PharmaOrdersScreen(
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun PharmaOrdersScreen(onBack: () -> Unit) {

    val orders = PharmaOrderRepository.orders

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("← Back")
        }

        Text(
            "Stockist Orders",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF008080)
        )

        Text(
            "View and manage incoming stockist orders",
            color = Color.Gray
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                orders.toList(),
                key = { it.id }
            ) { order ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            order.id,
                            color = Color(0xFF008080)
                        )

                        Text(
                            order.stockistName,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text("Medicine: ${order.medicineName}")
                        Text("Quantity: ${order.quantity}")
                        Text("Amount: ₹${order.totalAmount}")
                        Text("Date: ${order.orderDate}")

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "Status: ${order.status}",
                            color = Color(0xFF008080)
                        )

                        var expanded by remember {
                            mutableStateOf(false)
                        }

                        Box {
                            OutlinedButton(
                                onClick = { expanded = true }
                            ) {
                                Text("Update Status")
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                }
                            ) {
                                listOf(
                                    "Pending",
                                    "Accepted",
                                    "Processing",
                                    "Dispatched",
                                    "Delivered",
                                    "Rejected"
                                ).forEach { status ->

                                    DropdownMenuItem(
                                        text = { Text(status) },
                                        onClick = {
                                            PharmaOrderRepository.updateStatus(
                                                order.id,
                                                status
                                            )
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
