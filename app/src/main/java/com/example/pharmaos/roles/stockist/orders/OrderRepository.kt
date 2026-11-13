
package com.example.pharmaos.roles.stockist.orders

import androidx.compose.runtime.mutableStateListOf

object OrderRepository {

    private val orders = mutableStateListOf(

        Order(
            orderId = "ORD-1001",
            supplierName = "ABC Pharma Distributors",
            orderDate = "09 Nov 2026",
            expectedDelivery = "12 Nov 2026",
            totalAmount = 2500.00,
            status = OrderStatus.PENDING,
            items = listOf(
                OrderItem(
                    medicineName = "Paracetamol",
                    brand = "Dolo 650",
                    quantity = 10,
                    unitPrice = 25.00
                ),
                OrderItem(
                    medicineName = "Vitamin C",
                    brand = "Limcee",
                    quantity = 20,
                    unitPrice = 11.25
                )
            )
        ),

        Order(
            orderId = "ORD-1002",
            supplierName = "MediCare Suppliers",
            orderDate = "07 Nov 2026",
            expectedDelivery = "10 Nov 2026",
            totalAmount = 1800.00,
            status = OrderStatus.CONFIRMED,
            items = listOf(
                OrderItem(
                    medicineName = "Pantoprazole",
                    brand = "Pan 40",
                    quantity = 20,
                    unitPrice = 90.00
                )
            )
        ),

        Order(
            orderId = "ORD-1003",
            supplierName = "HealthPlus Distributors",
            orderDate = "05 Nov 2026",
            expectedDelivery = "08 Nov 2026",
            totalAmount = 3200.00,
            status = OrderStatus.DISPATCHED,
            items = listOf(
                OrderItem(
                    medicineName = "Amoxicillin",
                    brand = "Mox 500",
                    quantity = 20,
                    unitPrice = 160.00
                )
            )
        ),

        Order(
            orderId = "ORD-1004",
            supplierName = "ABC Pharma Distributors",
            orderDate = "01 Nov 2026",
            expectedDelivery = "04 Nov 2026",
            totalAmount = 1250.00,
            status = OrderStatus.DELIVERED,
            items = listOf(
                OrderItem(
                    medicineName = "Paracetamol",
                    brand = "Dolo 650",
                    quantity = 50,
                    unitPrice = 25.00
                )
            )
        ),

        Order(
            orderId = "ORD-1005",
            supplierName = "MediCare Suppliers",
            orderDate = "30 Oct 2026",
            expectedDelivery = "02 Nov 2026",
            totalAmount = 900.00,
            status = OrderStatus.CANCELLED,
            items = listOf(
                OrderItem(
                    medicineName = "Vitamin C",
                    brand = "Limcee",
                    quantity = 40,
                    unitPrice = 22.50
                )
            )
        )
    )


    // Get all orders
    fun getAllOrders(): List<Order> {
        return orders
    }


    // Get order using Order ID
    fun getOrderById(orderId: String): Order? {

        return orders.find {
            it.orderId == orderId
        }
    }


    // Generate a new unique Order ID
    fun generateOrderId(): String {

        val lastNumber = orders.maxOfOrNull {

            it.orderId
                .substringAfter("-")
                .toIntOrNull() ?: 1000

        } ?: 1000

        return "ORD-${lastNumber + 1}"
    }


    // Add newly placed order
    fun addOrder(order: Order) {

        orders.add(0, order)
    }

}
