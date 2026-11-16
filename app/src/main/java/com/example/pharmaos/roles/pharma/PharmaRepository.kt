
package com.example.pharmaos.roles.pharma

import androidx.compose.runtime.mutableStateListOf

object PharmaRepository {

    val medicines = mutableStateListOf(
        PharmaMedicine(
            "PH001",
            "Paracetamol",
            "DoloCare",
            "Paracetamol",
            "500 mg",
            "Tablet",
            28.0,
            5000,
            "Active"
        ),
        PharmaMedicine(
            "PH002",
            "Azithromycin",
            "AziPlus",
            "Azithromycin",
            "500 mg",
            "Tablet",
            95.0,
            2500,
            "Active"
        ),
        PharmaMedicine(
            "PH003",
            "Pantoprazole",
            "PantoLife",
            "Pantoprazole",
            "40 mg",
            "Tablet",
            60.0,
            3200,
            "Active"
        )
    )

    fun addMedicine(medicine: PharmaMedicine) {
        medicines.add(medicine)
    }

    fun updateMedicine(updated: PharmaMedicine) {
        val index = medicines.indexOfFirst {
            it.id == updated.id
        }

        if (index != -1) {
            medicines[index] = updated
        }
    }

    fun deleteMedicine(id: String) {
        medicines.removeAll { it.id == id }
    }

    fun getTotalProducts(): Int = medicines.size

    fun getTotalStock(): Int =
        medicines.sumOf { it.availableStock }
}
