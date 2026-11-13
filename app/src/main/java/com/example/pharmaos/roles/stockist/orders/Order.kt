
package com.example.pharmaos.roles.stockist.orders

data class Order(
    val orderId: String,
    val supplierName: String,
    val orderDate: String,
    val expectedDelivery: String,
    val totalAmount: Double,
    val status: OrderStatus,
    val items: List<OrderItem>,

    // Delivery details
    val deliveryContactName: String = "",
    val deliveryPhone: String = "",
    val deliveryAddress: String = ""
)

data class OrderItem(
    val medicineName: String,
    val brand: String,
    val quantity: Int,
    val unitPrice: Double
) {
    val totalPrice: Double
        get() = quantity * unitPrice
}

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    DISPATCHED,
    DELIVERED,
    CANCELLED
}
