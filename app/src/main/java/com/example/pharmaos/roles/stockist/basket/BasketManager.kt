package com.example.pharmaos.roles.stockist.basket

import androidx.compose.runtime.mutableStateListOf

object BasketManager {

    val items = mutableStateListOf<BasketItem>()

    fun addMedicine(
        medicineName: String,
        brand: String,
        salt: String,
        strength: String,
        unitPrice: Double,
        quantity: Int = 1,
        supplierName: String = ""
    ) {
        if (quantity <= 0) return

        val existingIndex = items.indexOfFirst {
            it.medicineName.equals(medicineName, ignoreCase = true) &&
                    it.brand.equals(brand, ignoreCase = true) &&
                    it.supplierName.equals(supplierName, ignoreCase = true)
        }

        if (existingIndex != -1) {
            val existingItem = items[existingIndex]

            items[existingIndex] = existingItem.copy(
                quantity = existingItem.quantity + quantity,
                unitPrice = unitPrice
            )
        } else {
            items.add(
                BasketItem(
                    id = "${medicineName}_${brand}_${supplierName}",
                    medicineName = medicineName,
                    brand = brand,
                    salt = salt,
                    strength = strength,
                    unitPrice = unitPrice,
                    quantity = quantity,
                    supplierName = supplierName
                )
            )
        }
    }

    fun increaseQuantity(id: String) {
        val index = items.indexOfFirst { it.id == id }

        if (index != -1) {
            items[index] = items[index].copy(
                quantity = items[index].quantity + 1
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

    // Apply the optimizer's recommendations to the existing basket.
    // Unavailable medicines are kept unchanged.
    fun applyOptimizedSelections(
        selections: List<OptimizedOfferSelection>
    ) {
        selections.forEach { selection ->

            val matchingItems = items.filter {
                it.medicineName.equals(
                    selection.medicineName,
                    ignoreCase = true
                )
            }

            if (matchingItems.isNotEmpty()) {

                val firstItem = matchingItems.first()

                // Remove all existing entries for this medicine.
                items.removeAll {
                    it.medicineName.equals(
                        selection.medicineName,
                        ignoreCase = true
                    )
                }

                // Add the optimized supplier offer.
                items.add(
                    BasketItem(
                        id = "${selection.medicineName}_${selection.brand}_${selection.supplierName}",
                        medicineName = selection.medicineName,
                        brand = selection.brand,
                        salt = firstItem.salt,
                        strength = firstItem.strength,
                        unitPrice = selection.unitPrice,
                        quantity = selection.orderQuantity,
                        supplierName = selection.supplierName
                    )
                )
            }
        }
    }
}