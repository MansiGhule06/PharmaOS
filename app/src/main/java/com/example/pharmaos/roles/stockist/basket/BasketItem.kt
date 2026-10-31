package com.example.pharmaos.roles.stockist.basket

data class BasketItem(
    val id: String,
    val medicineName: String,
    val brand: String,
    val salt: String,
    val strength: String,
    val unitPrice: Double,
    val quantity: Int = 1
) {
    val totalPrice: Double
        get() = unitPrice * quantity
}
