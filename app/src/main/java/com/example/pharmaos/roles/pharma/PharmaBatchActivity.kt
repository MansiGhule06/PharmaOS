
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PharmaBatch(
    val id: String,
    val medicineName: String,
    val batchNumber: String,
    val manufacturingDate: String,
    val expiryDate: String,
    val quantity: Int,
    val status: String
)

object PharmaBatchRepository {
    val batches = mutableStateListOf(
        PharmaBatch(
            "PB001", "Paracetamol", "BATCH001",
            "10/01/2026", "10/01/2028", 5000, "Approved"
        ),
        PharmaBatch(
            "PB002", "Azithromycin", "BATCH002",
            "15/03/2026", "15/03/2028", 3000, "Under Review"
        ),
        PharmaBatch(
            "PB003", "Pantoprazole", "BATCH003",
            "20/05/2026", "20/05/2028", 4000, "Approved"
        )
    )

    fun add(batch: PharmaBatch) {
        batches.add(batch)
    }

    fun update(batch: PharmaBatch) {
        val index = batches.indexOfFirst { it.id == batch.id }
        if (index != -1) batches[index] = batch
    }

    fun delete(id: String) {
        batches.removeAll { it.id == id }
    }
}

class PharmaBatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PharmaBatchScreen(
                    onBack = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PharmaBatchScreen(onBack: () -> Unit) {

    var showDialog by remember { mutableStateOf(false) }
    var editingBatch by remember { mutableStateOf<PharmaBatch?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Batch Management") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF008080),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingBatch = null
                    showDialog = true
                },
                containerColor = Color(0xFF008080),
                contentColor = Color.White
            ) {
                Text("+", fontSize = 24.sp)
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Track medicine batches, expiry and quality status.",
                    color = Color.Gray
                )
            }

            items(
                items = PharmaBatchRepository.batches,
                key = { it.id }
            ) { batch ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            batch.medicineName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Batch Number: ${batch.batchNumber}")
                        Text("Manufacturing Date: ${batch.manufacturingDate}")
                        Text("Expiry Date: ${batch.expiryDate}")
                        Text("Quantity: ${batch.quantity}")
                        Text("Status: ${batch.status}")

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TextButton(
                                onClick = {
                                    editingBatch = batch
                                    showDialog = true
                                }
                            ) {
                                Text("Edit")
                            }

                            TextButton(
                                onClick = {
                                    PharmaBatchRepository.delete(batch.id)
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
        PharmaBatchDialog(
            existingBatch = editingBatch,
            onDismiss = { showDialog = false },
            onSave = { batch ->
                if (editingBatch == null) {
                    PharmaBatchRepository.add(batch)
                } else {
                    PharmaBatchRepository.update(batch)
                }
                showDialog = false
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PharmaBatchDialog(
    existingBatch: PharmaBatch?,
    onDismiss: () -> Unit,
    onSave: (PharmaBatch) -> Unit
) {
    var medicineName by remember(existingBatch) {
        mutableStateOf(existingBatch?.medicineName ?: "")
    }
    var batchNumber by remember(existingBatch) {
        mutableStateOf(existingBatch?.batchNumber ?: "")
    }
    var manufacturingDate by remember(existingBatch) {
        mutableStateOf(existingBatch?.manufacturingDate ?: "")
    }
    var expiryDate by remember(existingBatch) {
        mutableStateOf(existingBatch?.expiryDate ?: "")
    }
    var quantity by remember(existingBatch) {
        mutableStateOf(existingBatch?.quantity?.toString() ?: "")
    }
    var status by remember(existingBatch) {
        mutableStateOf(existingBatch?.status ?: "Under Review")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (existingBatch == null) "Add Batch" else "Edit Batch")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    medicineName,
                    { medicineName = it },
                    label = { Text("Medicine Name") },
                    singleLine = true
                )
                OutlinedTextField(
                    batchNumber,
                    { batchNumber = it },
                    label = { Text("Batch Number") },
                    singleLine = true
                )
                OutlinedTextField(
                    manufacturingDate,
                    { manufacturingDate = it },
                    label = { Text("Manufacturing Date (DD/MM/YYYY)") },
                    singleLine = true
                )
                OutlinedTextField(
                    expiryDate,
                    { expiryDate = it },
                    label = { Text("Expiry Date (DD/MM/YYYY)") },
                    singleLine = true
                )
                OutlinedTextField(
                    quantity,
                    { quantity = it },
                    label = { Text("Quantity") },
                    singleLine = true
                )

                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = status,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Batch Status") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                        },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf(
                            "Approved",
                            "Under Review",
                            "Rejected",
                            "Recalled"
                        ).forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    status = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val parsedQuantity = quantity.toIntOrNull()
                    if (
                        medicineName.isNotBlank() &&
                        batchNumber.isNotBlank() &&
                        manufacturingDate.isNotBlank() &&
                        expiryDate.isNotBlank() &&
                        parsedQuantity != null &&
                        parsedQuantity > 0
                    ) {
                        onSave(
                            PharmaBatch(
                                id = existingBatch?.id
                                    ?: "PB${System.currentTimeMillis()}",
                                medicineName = medicineName.trim(),
                                batchNumber = batchNumber.trim(),
                                manufacturingDate = manufacturingDate.trim(),
                                expiryDate = expiryDate.trim(),
                                quantity = parsedQuantity,
                                status = status
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
