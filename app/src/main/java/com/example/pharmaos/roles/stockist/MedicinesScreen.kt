
package com.example.pharmaos.roles.stockist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pharmaos.roles.stockist.basket.BasketManager

private val MedicinesTeal = Color(0xFF008080)
private val MedicinesBackground = Color(0xFFF1F5FF)
private val MedicinesText = Color(0xFF333333)
private val MedicinesGrey = Color(0xFF999999)

private data class Medicine(
    val name: String,
    val brand: String,
    val salt: String,
    val strength: String,
    val category: String,
    val price: String,
    val alternatives: List<String>
)

@Composable
fun MedicinesScreen() {

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    // Get basket quantity from shared BasketManager
    val basketCount = BasketManager.getTotalQuantity()

    val categories = listOf(
        "All",
        "Pain Relief",
        "Antibiotic",
        "Gastric",
        "Vitamins"
    )

    // Temporary medicine data
    val medicines = listOf(
        Medicine(
            "Paracetamol",
            "Brand A",
            "Paracetamol",
            "500 mg",
            "Pain Relief",
            "₹25",
            listOf("Brand B", "Brand C")
        ),
        Medicine(
            "Amoxicillin",
            "Brand D",
            "Amoxicillin",
            "500 mg",
            "Antibiotic",
            "₹85",
            listOf("Brand E", "Brand F")
        ),
        Medicine(
            "Pantoprazole",
            "Brand G",
            "Pantoprazole",
            "40 mg",
            "Gastric",
            "₹55",
            listOf("Brand H", "Brand I")
        ),
        Medicine(
            "Vitamin C",
            "Brand J",
            "Ascorbic Acid",
            "500 mg",
            "Vitamins",
            "₹40",
            listOf("Brand K", "Brand L")
        )
    )

    val filteredMedicines = medicines.filter { medicine ->

        val matchesSearch =
            medicine.name.contains(searchQuery, ignoreCase = true) ||
                    medicine.brand.contains(searchQuery, ignoreCase = true) ||
                    medicine.salt.contains(searchQuery, ignoreCase = true)

        val matchesCategory =
            selectedCategory == "All" ||
                    medicine.category == selectedCategory

        matchesSearch && matchesCategory
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MedicinesBackground)
            .padding(16.dp)
    ) {

        Text(
            text = "Medicine Catalogue",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MedicinesText
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Search and compare available medicines",
            fontSize = 13.sp,
            color = MedicinesGrey
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Search medicine
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White,
                    RoundedCornerShape(12.dp)
                )
                .padding(
                    horizontal = 14.dp,
                    vertical = 15.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MedicinesGrey
            )

            Spacer(modifier = Modifier.width(10.dp))

            BasicTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                modifier = Modifier.weight(1f),
                singleLine = true,
                decorationBox = { innerTextField ->

                    if (searchQuery.isEmpty()) {
                        Text(
                            "Search medicine, brand or salt...",
                            color = MedicinesGrey,
                            fontSize = 13.sp
                        )
                    }

                    innerTextField()
                }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            "Therapeutic Categories",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MedicinesText
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Category filters
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {

            categories.forEach { category ->

                val selected = selectedCategory == category

                Surface(
                    onClick = {
                        selectedCategory = category
                    },
                    shape = RoundedCornerShape(20.dp),
                    color = if (selected) {
                        MedicinesTeal
                    } else {
                        Color.White
                    }
                ) {

                    Text(
                        text = category,
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 9.dp
                        ),
                        color = if (selected) {
                            Color.White
                        } else {
                            MedicinesText
                        },
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Available medicines heading
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "Available Medicines",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MedicinesText
            )

            Text(
                "Basket: $basketCount",
                color = MedicinesTeal,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (filteredMedicines.isEmpty()) {

            Text(
                "No medicines found.",
                color = MedicinesGrey,
                modifier = Modifier.padding(vertical = 20.dp)
            )

        } else {

            filteredMedicines.forEach { medicine ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    medicine.name,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MedicinesText
                                )

                                Text(
                                    "Brand: ${medicine.brand}",
                                    fontSize = 12.sp,
                                    color = MedicinesGrey
                                )
                            }

                            Text(
                                medicine.price,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedicinesTeal
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            "Salt: ${medicine.salt}",
                            fontSize = 12.sp,
                            color = MedicinesText
                        )

                        Text(
                            "Strength: ${medicine.strength}",
                            fontSize = 12.sp,
                            color = MedicinesText
                        )

                        Text(
                            "Category: ${medicine.category}",
                            fontSize = 12.sp,
                            color = MedicinesText
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            "Alternative brands (same listed active ingredient)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MedicinesTeal
                        )

                        Text(
                            medicine.alternatives.joinToString("  •  "),
                            fontSize = 12.sp,
                            color = MedicinesGrey
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // ADD TO BASKET BUTTON
                        Button(
                            onClick = {

                                BasketManager.addMedicine(
                                    medicineName = medicine.name,
                                    brand = medicine.brand,
                                    salt = medicine.salt,
                                    strength = medicine.strength,
                                    unitPrice = medicine.price
                                        .replace("₹", "")
                                        .toDoubleOrNull() ?: 0.0
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MedicinesTeal
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {

                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text("Add to Basket")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Note: Prices and brands are sample data. Confirm strength, dosage form, licensing and professional requirements before procurement. Similar active ingredients alone do not establish interchangeability.",
            fontSize = 11.sp,
            color = MedicinesGrey,
            lineHeight = 16.sp
        )
    }
}
