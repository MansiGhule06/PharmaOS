
package com.example.pharmaos.roles.manufacturer

import androidx.compose.runtime.mutableStateListOf

object ManufacturerBatchRepository {

    val batches = mutableStateListOf(

        ManufacturerBatch(
            id = "BAT001",
            medicineName = "Paracetamol",
            batchNumber = "BTH1001",
            manufacturingDate = "01/2026",
            expiryDate = "12/2027",
            quantity = 2000,
            status = "Active"
        ),

        ManufacturerBatch(
            id = "BAT002",
            medicineName = "Amoxicillin",
            batchNumber = "BTH1002",
            manufacturingDate = "12/2025",
            expiryDate = "06/2027",
            quantity = 1500,
            status = "Active"
        ),

        ManufacturerBatch(
            id = "BAT003",
            medicineName = "Pantoprazole",
            batchNumber = "BTH1003",
            manufacturingDate = "09/2025",
            expiryDate = "09/2027",
            quantity = 1800,
            status = "Active"
        )
    )

    fun addBatch(batch: ManufacturerBatch) {
        batches.add(batch)
    }

    fun updateBatch(updatedBatch: ManufacturerBatch) {
        val index = batches.indexOfFirst {
            it.id == updatedBatch.id
        }

        if (index != -1) {
            batches[index] = updatedBatch
        }
    }

    fun deleteBatch(id: String) {
        batches.removeAll {
            it.id == id
        }
    }
}
