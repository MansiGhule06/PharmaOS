
package com.example.pharmaos.roles.pharma

data class PharmaOrder(
    val id: String,
    val stockistName: String,
    val medicineName: String,
    val quantity: Int,
    val totalAmount: Double,
    val orderDate: String,
    val status: String
)
