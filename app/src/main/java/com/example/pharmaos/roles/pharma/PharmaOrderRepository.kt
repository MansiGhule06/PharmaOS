
package com.example.pharmaos.roles.pharma

import androidx.compose.runtime.mutableStateListOf

object PharmaOrderRepository {

    val orders = mutableStateListOf(
        PharmaOrder(
            "ORD001",
            "City Medical Store",
            "Paracetamol",
            500,
            14000.0,
            "14/11/2026",
            "Pending"
        ),
        PharmaOrder(
            "ORD002",
            "HealthCare Distributors",
            "Azithromycin",
            200,
            19000.0,
            "13/11/2026",
            "Accepted"
        ),
        PharmaOrder(
            "ORD003",
            "Wellness Pharmacy",
            "Pantoprazole",
            300,
            18000.0,
            "12/11/2026",
            "Dispatched"
        )
    )

    fun updateStatus(id: String, newStatus: String) {
        val index = orders.indexOfFirst { it.id == id }

        if (index != -1) {
            orders[index] = orders[index].copy(
                status = newStatus
            )
        }
    }
}
