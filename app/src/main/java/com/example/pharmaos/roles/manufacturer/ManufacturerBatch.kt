
package com.example.pharmaos.roles.manufacturer

data class ManufacturerBatch(
    val id: String,
    val medicineName: String,
    val batchNumber: String,
    val manufacturingDate: String,
    val expiryDate: String,
    val quantity: Int,
    val status: String
)
