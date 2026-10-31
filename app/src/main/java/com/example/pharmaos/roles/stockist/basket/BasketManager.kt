package com.example.pharmaos.roles.stockist.basket

import androidx.compose.runtime.mutableStateListOf

object BasketManager {

    val items = mutableStateListOf<BasketItem>()

    fun addMedicine(
        medicineName: String,
        brand: String,
        salt: String,
        strength: String,
        unitPrice: Double
    ) {
        val existingIndex = items.indexOfFirst {
            it.medicineName == medicineName && it.brand == brand
        }

        if (existingIndex != -1) {
            val existingItem = items[existingIndex]
            items[existingIndex] = existingItem.copy(
                quantity = existingItem.quantity + 1
            )
        } else {
            items.add(
                BasketItem(
                    id = "${medicineName}_${brand}",
                    medicineName = medicineName,
                    brand = brand,
                    salt = salt,
                    strength = strength,
                    unitPrice = unitPrice
                )
            )
        }
    }

    fun increaseQuantity(id: String) {
        val index = items.indexOfFirst { it.id == id }

        if (index != -1) {
            val item = items[index]
            items[index] = item.copy(
                quantity = item.quantity + 1
            )
        }
    }

    fun decreaseQuantity(id: String) {
        val index = items.indexOfFirst { it.id == id }

        if (index != -1) {
            val item = items[index]

            if (item.quantity > 1) {
                items[index] = item.copy(
                    quantity = item.quantity - 1
                )
            } else {
                items.removeAt(index)
            }
        }
    }

    fun removeMedicine(id: String) {
        items.removeAll { it.id == id }
    }

    fun clearBasket() {
        items.clear()
    }

    fun getSubtotal(): Double {
        return items.sumOf { it.totalPrice }
    }

    fun getTotalQuantity(): Int {
        return items.sumOf { it.quantity }
    }
}
