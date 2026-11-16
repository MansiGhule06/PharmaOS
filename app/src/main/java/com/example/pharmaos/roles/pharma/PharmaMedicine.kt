
package com.example.pharmaos.roles.pharma

data class PharmaMedicine(
    val id: String,
    val medicineName: String,
    val brand: String,
    val salt: String,
    val strength: String,
    val category: String,
    val price: Double,
    val availableStock: Int,
    val status: String
)
