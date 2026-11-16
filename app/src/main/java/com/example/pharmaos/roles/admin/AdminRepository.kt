
package com.example.pharmaos.roles.admin

import androidx.compose.runtime.mutableStateListOf

object AdminRepository {

    val users = mutableStateListOf(

        AdminUser(
            "USR001",
            "Rahul Sharma",
            "rahul@example.com",
            "Manufacturer",
            "Sharma Pharmaceuticals",
            "Pending"
        ),

        AdminUser(
            "USR002",
            "Priya Patel",
            "priya@example.com",
            "Stockist / Distributor",
            "Patel Medical Distributors",
            "Verified"
        ),

        AdminUser(
            "USR003",
            "Amit Desai",
            "amit@example.com",
            "Pharmaceutical Company / Brand",
            "HealthCare Pharma",
            "Pending"
        ),

        AdminUser(
            "USR004",
            "Sneha Joshi",
            "sneha@example.com",
            "Stockist / Distributor",
            "Joshi Medical Store",
            "Rejected"
        ),

        AdminUser(
            "USR005",
            "Karan Mehta",
            "karan@example.com",
            "Manufacturer",
            "Mehta Laboratories",
            "Verified"
        )
    )

    fun approveUser(id: String) {
        val index = users.indexOfFirst { it.id == id }

        if (index != -1) {
            users[index] = users[index].copy(
                status = "Verified"
            )
        }
    }

    fun rejectUser(id: String) {
        val index = users.indexOfFirst { it.id == id }

        if (index != -1) {
            users[index] = users[index].copy(
                status = "Rejected"
            )
        }
    }

    fun updateUser(updatedUser: AdminUser) {
        val index = users.indexOfFirst {
            it.id == updatedUser.id
        }

        if (index != -1) {
            users[index] = updatedUser
        }
    }

    fun deleteUser(id: String) {
        users.removeAll { it.id == id }
    }

    fun getTotalUsers(): Int {
        return users.size
    }

    fun getPendingUsers(): Int {
        return users.count { it.status == "Pending" }
    }

    fun getVerifiedUsers(): Int {
        return users.count { it.status == "Verified" }
    }

    fun getRejectedUsers(): Int {
        return users.count { it.status == "Rejected" }
    }
}
