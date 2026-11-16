
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

private val PharmaTeal = Color(0xFF008080)

class PharmaMedicineActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                PharmaMedicineScreen(
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun PharmaMedicineScreen(onBack: () -> Unit) {

    val medicines = PharmaRepository.medicines
    var showDialog by remember { mutableStateOf(false) }
    var selectedMedicine by remember {
        mutableStateOf<PharmaMedicine?>(null)
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
            "Medicine Management",
            style = MaterialTheme.typography.headlineMedium,
            color = PharmaTeal
        )

        Text(
            "Manage your medicine catalogue",
            color = Color.Gray
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                selectedMedicine = null
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PharmaTeal
            )
        ) {
            Text("+ Add Medicine")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                medicines.toList(),
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
                        Text("Stock: ${medicine.availableStock}")

                        Row(
                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            TextButton(
                                onClick = {
                                    selectedMedicine = medicine
                                    showDialog = true
                                }
                            ) {
                                Text("Edit")
                            }

                            TextButton(
                                onClick = {
                                    PharmaRepository.deleteMedicine(
                                        medicine.id
                                    )
                                }
                            ) {
                                Text(
                                    "Delete",
                                    color = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        MedicineFormDialog(
            medicine = selectedMedicine,
            onDismiss = { showDialog = false },
            onSave = { medicine ->
                if (selectedMedicine == null) {
                    PharmaRepository.addMedicine(medicine)
                } else {
                    PharmaRepository.updateMedicine(medicine)
                }

                showDialog = false
            }
        )
    }
}

@Composable
private fun MedicineFormDialog(
    medicine: PharmaMedicine?,
    onDismiss: () -> Unit,
    onSave: (PharmaMedicine) -> Unit
) {

    var name by remember(medicine) {
        mutableStateOf(medicine?.medicineName ?: "")
    }

    var brand by remember(medicine) {
        mutableStateOf(medicine?.brand ?: "")
    }

    var salt by remember(medicine) {
        mutableStateOf(medicine?.salt ?: "")
    }

    var strength by remember(medicine) {
        mutableStateOf(medicine?.strength ?: "")
    }

    var category by remember(medicine) {
        mutableStateOf(medicine?.category ?: "")
    }

    var price by remember(medicine) {
        mutableStateOf(medicine?.price?.toString() ?: "")
    }

    var stock by remember(medicine) {
        mutableStateOf(medicine?.availableStock?.toString() ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (medicine == null) "Add Medicine"
                else "Edit Medicine"
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Medicine Name") }
                )

                OutlinedTextField(
                    value = brand,
                    onValueChange = { brand = it },
                    label = { Text("Brand") }
                )

                OutlinedTextField(
                    value = salt,
                    onValueChange = { salt = it },
                    label = { Text("Salt") }
                )

                OutlinedTextField(
                    value = strength,
                    onValueChange = { strength = it },
                    label = { Text("Strength") }
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") }
                )

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") }
                )

                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Available Stock") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val parsedPrice = price.toDoubleOrNull()
                    val parsedStock = stock.toIntOrNull()

                    if (
                        name.isNotBlank() &&
                        brand.isNotBlank() &&
                        salt.isNotBlank() &&
                        strength.isNotBlank() &&
                        category.isNotBlank() &&
                        parsedPrice != null &&
                        parsedPrice >= 0 &&
                        parsedStock != null &&
                        parsedStock >= 0
                    ) {
                        onSave(
                            PharmaMedicine(
                                id = medicine?.id
                                    ?: "PH${System.currentTimeMillis()}",
                                medicineName = name.trim(),
                                brand = brand.trim(),
                                salt = salt.trim(),
                                strength = strength.trim(),
                                category = category.trim(),
                                price = parsedPrice,
                                availableStock = parsedStock,
                                status = medicine?.status ?: "Active"
                            )
                        )
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
