package com.example.pharmaos.roles.manufacturer

import androidx.compose.runtime.mutableStateListOf

object ManufacturerRepository {

    val medicines = mutableStateListOf(
        ManufacturerMedicine(
            id = "MED001",
            medicineName = "Paracetamol",
            brand = "ParaCare",
            salt = "Paracetamol",
            strength = "500 mg",
            category = "Tablet",
            price = 25.0,
            availableStock = 5000,
            batchNumber = "BTH1001",
            expiryDate = "12/2027"
        ),
        ManufacturerMedicine(
            id = "MED002",
            medicineName = "Amoxicillin",
            brand = "AmoxiPlus",
            salt = "Amoxicillin",
            strength = "500 mg",
            category = "Capsule",
            price = 85.0,
            availableStock = 2500,
            batchNumber = "BTH1002",
            expiryDate = "06/2027"
        ),
        ManufacturerMedicine(
            id = "MED003",
            medicineName = "Pantoprazole",
            brand = "PantoCare",
            salt = "Pantoprazole",
            strength = "40 mg",
            category = "Tablet",
            price = 55.0,
            availableStock = 3200,
            batchNumber = "BTH1003",
            expiryDate = "09/2027"
        ),
        ManufacturerMedicine(
            id = "MED004",
            medicineName = "Vitamin C",
            brand = "CeePlus",
            salt = "Ascorbic Acid",
            strength = "500 mg",
            category = "Tablet",
            price = 40.0,
            availableStock = 1800,
            batchNumber = "BTH1004",
            expiryDate = "03/2028"
        )
    )

    fun addMedicine(medicine: ManufacturerMedicine) {
        medicines.add(medicine)
    }

    fun updateMedicine(updatedMedicine: ManufacturerMedicine) {
        val index = medicines.indexOfFirst {
            it.id == updatedMedicine.id
        }

        if (index != -1) {
            medicines[index] = updatedMedicine
        }
    }

    fun deleteMedicine(id: String) {
        medicines.removeAll {
            it.id == id
        }
    }

    fun getTotalProducts(): Int {
        return medicines.size
    }

    fun getTotalStock(): Int {
        return medicines.sumOf {
            it.availableStock
        }
    }
}
