
package com.example.pharmaos.roles.manufacturer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.Calendar

private val Teal = Color(0xFF008080)

class ManufacturerBatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ManufacturerBatchesScreen(onBack = { finish() })
            }
        }
    }
}

@Composable
fun ManufacturerBatchesScreen(onBack: () -> Unit) {

    val batches = ManufacturerBatchRepository.batches

    var showAddDialog by remember { mutableStateOf(false) }
    var editingBatch by remember { mutableStateOf<ManufacturerBatch?>(null) }
    var deletingBatch by remember { mutableStateOf<ManufacturerBatch?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5FF))
            .padding(16.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("← Back", color = Teal)
        }

        Text(
            "Batch Management",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            "Manage medicine batches, stock and expiry dates",
            color = Color.Gray
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Total Batches: ${batches.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Teal)
            ) {
                Text("+ Add Batch")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (batches.isEmpty()) {
            Text("No batches available.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = batches,
                    key = { it.id }
                ) { batch ->

                    BatchCard(
                        batch = batch,
                        onEdit = { editingBatch = batch },
                        onDelete = { deletingBatch = batch }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        BatchFormDialog(
            title = "Add New Batch",
            onDismiss = { showAddDialog = false },
            onSave = { medicine, number, manufacturing, expiry, quantity ->

                ManufacturerBatchRepository.addBatch(
                    ManufacturerBatch(
                        id = "BAT${System.currentTimeMillis()}",
                        medicineName = medicine,
                        batchNumber = number,
                        manufacturingDate = manufacturing,
                        expiryDate = expiry,
                        quantity = quantity,
                        status = "Active"
                    )
                )

                showAddDialog = false
            }
        )
    }

    editingBatch?.let { batch ->
        BatchFormDialog(
            title = "Edit Batch",
            existingBatch = batch,
            onDismiss = { editingBatch = null },
            onSave = { medicine, number, manufacturing, expiry, quantity ->

                ManufacturerBatchRepository.updateBatch(
                    batch.copy(
                        medicineName = medicine,
                        batchNumber = number,
                        manufacturingDate = manufacturing,
                        expiryDate = expiry,
                        quantity = quantity
                    )
                )

                editingBatch = null
            }
        )
    }

    deletingBatch?.let { batch ->
        AlertDialog(
            onDismissRequest = { deletingBatch = null },
            title = { Text("Delete Batch?") },
            text = {
                Text("Are you sure you want to delete ${batch.batchNumber}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        ManufacturerBatchRepository.deleteBatch(batch.id)
                        deletingBatch = null
                    }
                ) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingBatch = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun BatchCard(
    batch: ManufacturerBatch,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val status = getBatchExpiryStatus(batch.expiryDate)

    val statusColor = when (status) {
        "Expired" -> Color(0xFFC62828)
        "Near Expiry" -> Color(0xFFE68A00)
        else -> Color(0xFF008080)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                batch.medicineName,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(8.dp))

            Text("Batch Number: ${batch.batchNumber}")
            Text("Manufacturing Date: ${batch.manufacturingDate}")
            Text("Expiry Date: ${batch.expiryDate}")
            Text("Available Quantity: ${batch.quantity}")

            Spacer(Modifier.height(10.dp))

            Surface(
                color = statusColor.copy(alpha = 0.12f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    status,
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 6.dp
                    ),
                    color = statusColor
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                OutlinedButton(onClick = onEdit) {
                    Text("Edit", color = Teal)
                }

                OutlinedButton(onClick = onDelete) {
                    Text("Delete", color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun BatchFormDialog(
    title: String,
    existingBatch: ManufacturerBatch? = null,
    onDismiss: () -> Unit,
    onSave: (
        String,
        String,
        String,
        String,
        Int
    ) -> Unit
) {

    var medicine by remember(existingBatch?.id) {
        mutableStateOf(existingBatch?.medicineName ?: "")
    }

    var batchNumber by remember(existingBatch?.id) {
        mutableStateOf(existingBatch?.batchNumber ?: "")
    }

    var manufacturingDate by remember(existingBatch?.id) {
        mutableStateOf(existingBatch?.manufacturingDate ?: "")
    }

    var expiryDate by remember(existingBatch?.id) {
        mutableStateOf(existingBatch?.expiryDate ?: "")
    }

    var quantity by remember(existingBatch?.id) {
        mutableStateOf(existingBatch?.quantity?.toString() ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedTextField(
                    value = medicine,
                    onValueChange = { medicine = it },
                    label = { Text("Medicine Name") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = batchNumber,
                    onValueChange = { batchNumber = it },
                    label = { Text("Batch Number") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = manufacturingDate,
                    onValueChange = { manufacturingDate = it },
                    label = { Text("Manufacturing Date (MM/YYYY)") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { expiryDate = it },
                    label = { Text("Expiry Date (MM/YYYY)") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Quantity") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val qty = quantity.toIntOrNull()

                    if (
                        medicine.isNotBlank() &&
                        batchNumber.isNotBlank() &&
                        isValidMonthYear(manufacturingDate) &&
                        isValidMonthYear(expiryDate) &&
                        qty != null &&
                        qty > 0
                    ) {
                        onSave(
                            medicine.trim(),
                            batchNumber.trim(),
                            manufacturingDate.trim(),
                            expiryDate.trim(),
                            qty
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Teal)
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

fun isValidMonthYear(value: String): Boolean {
    val parts = value.trim().split("/")

    if (parts.size != 2) return false

    val month = parts[0].toIntOrNull() ?: return false
    val year = parts[1].toIntOrNull() ?: return false

    return month in 1..12 && year in 2000..2100
}

fun getBatchExpiryStatus(expiryDate: String): String {

    val parts = expiryDate.split("/")
    if (parts.size != 2) return "Unknown"

    val month = parts[0].toIntOrNull() ?: return "Unknown"
    val year = parts[1].toIntOrNull() ?: return "Unknown"

    if (month !in 1..12) return "Unknown"

    val today = Calendar.getInstance()

    val currentMonth = today.get(Calendar.MONTH) + 1
    val currentYear = today.get(Calendar.YEAR)

    val monthsRemaining =
        (year - currentYear) * 12 + (month - currentMonth)

    return when {
        monthsRemaining < 0 -> "Expired"
        monthsRemaining <= 6 -> "Near Expiry"
        else -> "Active"
    }
}
