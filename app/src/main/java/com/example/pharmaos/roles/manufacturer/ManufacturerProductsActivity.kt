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

private val ManufacturerTeal = Color(0xFF008080)

class ManufacturerProductsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ManufacturerProductsScreen(
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun ManufacturerProductsScreen(onBack: () -> Unit) {

    var showAddDialog by remember { mutableStateOf(false) }

    var medicineName by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var salt by remember { mutableStateOf("") }
    var strength by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var batch by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }

    var refresh by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onBack) {
                Text("← Back")
            }

            Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ManufacturerTeal
                )
            ) {
                Text("+ Add Medicine")
            }
        }

        Text(
            text = "My Products",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Manage your pharmaceutical products",
            color = Color.Gray
        )

        Spacer(Modifier.height(16.dp))

        // Refresh list after add/delete
        val medicines = remember(refresh) {
            ManufacturerRepository.medicines.toList()
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = medicines,
                key = { it.id }
            ) { medicine ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            medicine.medicineName,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text("Brand: ${medicine.brand}")
                        Text("Salt: ${medicine.salt}")
                        Text("Strength: ${medicine.strength}")
                        Text("Category: ${medicine.category}")
                        Text("Price: ₹${medicine.price}")
                        Text("Available Stock: ${medicine.availableStock}")
                        Text("Batch: ${medicine.batchNumber}")
                        Text("Expiry: ${medicine.expiryDate}")

                        Spacer(Modifier.height(8.dp))

                        TextButton(
                            onClick = {
                                ManufacturerRepository.deleteMedicine(medicine.id)
                                refresh++
                            }
                        ) {
                            Text(
                                "Delete Product",
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add New Medicine") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        medicineName,
                        { medicineName = it },
                        label = { Text("Medicine Name") }
                    )
                    OutlinedTextField(
                        brand,
                        { brand = it },
                        label = { Text("Brand") }
                    )
                    OutlinedTextField(
                        salt,
                        { salt = it },
                        label = { Text("Active Salt") }
                    )
                    OutlinedTextField(
                        strength,
                        { strength = it },
                        label = { Text("Strength") }
                    )
                    OutlinedTextField(
                        category,
                        { category = it },
                        label = { Text("Category") }
                    )
                    OutlinedTextField(
                        price,
                        { price = it },
                        label = { Text("Price") }
                    )
                    OutlinedTextField(
                        stock,
                        { stock = it },
                        label = { Text("Available Stock") }
                    )
                    OutlinedTextField(
                        batch,
                        { batch = it },
                        label = { Text("Batch Number") }
                    )
                    OutlinedTextField(
                        expiry,
                        { expiry = it },
                        label = { Text("Expiry Date (DD-MM-YYYY)") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (
                            medicineName.isNotBlank() &&
                            brand.isNotBlank() &&
                            salt.isNotBlank() &&
                            strength.isNotBlank() &&
                            category.isNotBlank() &&
                            price.toDoubleOrNull() != null &&
                            stock.toIntOrNull() != null &&
                            batch.isNotBlank() &&
                            expiry.isNotBlank()
                        ) {
                            ManufacturerRepository.addMedicine(
                                ManufacturerMedicine(
                                    id = System.currentTimeMillis().toString(),
                                    medicineName = medicineName,
                                    brand = brand,
                                    salt = salt,
                                    strength = strength,
                                    category = category,
                                    price = price.toDouble(),
                                    availableStock = stock.toInt(),
                                    batchNumber = batch,
                                    expiryDate = expiry
                                )
                            )

                            refresh++
                            showAddDialog = false

                            medicineName = ""
                            brand = ""
                            salt = ""
                            strength = ""
                            category = ""
                            price = ""
                            stock = ""
                            batch = ""
                            expiry = ""
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showAddDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
