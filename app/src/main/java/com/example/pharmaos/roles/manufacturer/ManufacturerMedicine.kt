package com.example.pharmaos.roles.manufacturer

data class ManufacturerMedicine(
    val id: String,
    val medicineName: String,
    val brand: String,
    val salt: String,
    val strength: String,
    val category: String,
    val price: Double,
    val availableStock: Int,
    val batchNumber: String,
    val expiryDate: String
)
