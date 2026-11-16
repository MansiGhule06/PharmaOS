
package com.example.pharmaos.roles.manufacturer

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

data class ManufacturerOrder(
    val orderId: String,
    val buyerName: String,
    val medicineName: String,
    val quantity: Int,
    val totalAmount: Double,
    var status: String
)

class ManufacturerOrdersActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ManufacturerOrdersScreen(
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun ManufacturerOrdersScreen(onBack: () -> Unit) {

    val orders = remember {
        mutableStateListOf(
            ManufacturerOrder(
                "MFG-1001",
                "ABC Pharma Distributors",
                "Paracetamol",
                500,
                2500.0,
                "Pending"
            ),
            ManufacturerOrder(
                "MFG-1002",
                "HealthCare Medicals",
                "Amoxicillin",
                300,
                4500.0,
                "Accepted"
            ),
            ManufacturerOrder(
                "MFG-1003",
                "City Pharma Stockists",
                "Pantoprazole",
                200,
                3200.0,
                "Dispatched"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("← Back")
        }

        Text(
            "Incoming Orders",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            "Review and manage stockist orders",
            color = Color.Gray
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = orders,
                key = { it.orderId }
            ) { order ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            order.orderId,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF008080)
                        )

                        Spacer(Modifier.height(8.dp))

                        Text("Buyer: ${order.buyerName}")
                        Text("Medicine: ${order.medicineName}")
                        Text("Quantity: ${order.quantity}")
                        Text("Total: ₹${order.totalAmount}")

                        Spacer(Modifier.height(8.dp))

                        Text("Status: ${order.status}")

                        Spacer(Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (order.status == "Pending") {
                                Button(
                                    onClick = {
                                        val index = orders.indexOf(order)
                                        if (index >= 0) {
                                            orders[index] = order.copy(
                                                status = "Accepted"
                                            )
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF008080)
                                    )
                                ) {
                                    Text("Accept")
                                }

                                OutlinedButton(
                                    onClick = {
                                        val index = orders.indexOf(order)
                                        if (index >= 0) {
                                            orders[index] = order.copy(
                                                status = "Rejected"
                                            )
                                        }
                                    }
                                ) {
                                    Text("Reject")
                                }
                            }

                            if (order.status == "Accepted") {
                                Button(
                                    onClick = {
                                        val index = orders.indexOf(order)
                                        if (index >= 0) {
                                            orders[index] = order.copy(
                                                status = "Dispatched"
                                            )
                                        }
                                    }
                                ) {
                                    Text("Mark Dispatched")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
