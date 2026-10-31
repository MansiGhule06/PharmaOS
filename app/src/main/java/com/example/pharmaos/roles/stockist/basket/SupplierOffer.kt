package com.example.pharmaos.roles.stockist.basket

data class SupplierOffer(
    val offerId: String,
    val medicineName: String,
    val brand: String,
    val supplierName: String,

    // Pricing details
    val unitPrice: Double,
    val minimumOrderQuantity: Int,
    val availableQuantity: Int,

    // Discount and scheme details
    val discountPercentage: Double = 0.0,
    val schemeDescription: String = "",

    // Delivery details
    val deliveryCharge: Double = 0.0,
    val estimatedDeliveryDays: Int = 0,

    // Supplier performance indicator
    val trustScore: Double = 0.0
) {
    val discountedUnitPrice: Double
        get() = unitPrice - (unitPrice * discountPercentage / 100.0)

    val isAvailable: Boolean
        get() = availableQuantity >= minimumOrderQuantity
}
