package com.example.pharmaos.roles.stockist.basket

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ComparisonTeal = Color(0xFF008080)
private val ComparisonBackground = Color(0xFFF1F5FF)
private val ComparisonDark = Color(0xFF333333)
private val ComparisonGrey = Color(0xFF777777)
private val ComparisonGreen = Color(0xFF15803D)
private val ComparisonRed = Color(0xFFDC2626)

@Composable
fun SupplierComparisonScreen() {

    val context = LocalContext.current

    val medicineNames = listOf(
        "Paracetamol",
        "Amoxicillin",
        "Pantoprazole",
        "Vitamin C"
    )

    var selectedMedicine by remember {
        mutableStateOf("Paracetamol")
    }

    var selectedOffer by remember {
        mutableStateOf<SupplierOffer?>(null)
    }

    val offers = remember(selectedMedicine) {
        SupplierOfferRepository.getOffersForMedicine(selectedMedicine)
    }

    val existingQuantity = selectedOffer?.let { offer ->
        BasketManager.items
            .filter {
                it.medicineName.equals(offer.medicineName, true) &&
                        it.brand.equals(offer.brand, true) &&
                        it.supplierName.equals(offer.supplierName, true)
            }
            .sumOf { it.quantity }
    } ?: 0

    val quantityToAdd = selectedOffer?.let { offer ->
        (offer.minimumOrderQuantity - existingQuantity).coerceAtLeast(0)
    } ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ComparisonBackground)
    ) {

        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(18.dp)
        ) {

            Text(
                text = "Supplier Comparison",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = ComparisonDark
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Compare prices, discounts, stock availability and delivery from different suppliers.",
                fontSize = 13.sp,
                color = ComparisonGrey
            )
        }

        // Medicine selection
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {

            Text(
                text = "Select Medicine",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = ComparisonDark,
                modifier = Modifier.padding(
                    start = 16.dp,
                    bottom = 10.dp
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                medicineNames.forEach { medicine ->

                    FilterChip(
                        selected = selectedMedicine == medicine,
                        onClick = {
                            selectedMedicine = medicine
                            selectedOffer = null
                        },
                        label = {
                            Text(medicine)
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ComparisonTeal,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Supplier offers
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Text(
                    text = "Available Supplier Offers",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = ComparisonDark
                )
            }

            if (offers.isEmpty()) {

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            text = "No supplier offers available for $selectedMedicine.",
                            modifier = Modifier.padding(18.dp),
                            color = ComparisonGrey
                        )
                    }
                }

            } else {

                items(offers) { offer ->

                    val isSelected = selectedOffer?.offerId == offer.offerId

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected)
                                Color(0xFFE6F4F4)
                            else
                                Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = offer.brand,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ComparisonDark
                                    )

                                    Text(
                                        text = offer.supplierName,
                                        fontSize = 13.sp,
                                        color = ComparisonGrey
                                    )
                                }

                                RadioButton(
                                    selected = isSelected,
                                    onClick = {
                                        selectedOffer = offer
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = ComparisonTeal
                                    )
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color(0xFFE5E7EB)
                            )

                            ComparisonDetailRow(
                                label = "Original price",
                                value = "₹${String.format("%.2f", offer.unitPrice)}"
                            )

                            ComparisonDetailRow(
                                label = "Discount",
                                value = "${offer.discountPercentage}%"
                            )

                            ComparisonDetailRow(
                                label = "Discounted price",
                                value = "₹${String.format("%.2f", offer.discountedUnitPrice)}",
                                valueColor = ComparisonGreen
                            )

                            ComparisonDetailRow(
                                label = "Minimum order quantity",
                                value = offer.minimumOrderQuantity.toString()
                            )

                            ComparisonDetailRow(
                                label = "Available stock",
                                value = offer.availableQuantity.toString()
                            )

                            ComparisonDetailRow(
                                label = "Delivery charge",
                                value = "₹${String.format("%.2f", offer.deliveryCharge)}"
                            )

                            ComparisonDetailRow(
                                label = "Estimated delivery",
                                value = "${offer.estimatedDeliveryDays} days"
                            )

                            ComparisonDetailRow(
                                label = "Pharma Trust Score",
                                value = "${offer.trustScore}/100",
                                valueColor = ComparisonTeal
                            )

                            if (offer.schemeDescription.isNotBlank()) {

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Offer: ${offer.schemeDescription}",
                                    fontSize = 12.sp,
                                    color = ComparisonTeal
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                val available = offer.isAvailable

                                Text(
                                    text = if (available)
                                        "In Stock"
                                    else
                                        "Insufficient Stock",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (available)
                                        ComparisonGreen
                                    else
                                        ComparisonRed
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = {
                                    selectedOffer = offer
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = ComparisonTeal
                                )
                            ) {
                                Text(
                                    text = if (isSelected)
                                        "Selected"
                                    else
                                        "Select This Offer"
                                )
                            }
                        }
                    }
                }
            }
        }

        // Selected offer bottom section
        if (selectedOffer != null) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Selected Supplier",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = ComparisonDark
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = selectedOffer!!.supplierName,
                        fontSize = 14.sp,
                        color = ComparisonGrey
                    )

                    Text(
                        text = "₹${String.format("%.2f", selectedOffer!!.discountedUnitPrice)} per unit",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = ComparisonTeal
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {

                            val offer = selectedOffer!!

                            if (!offer.isAvailable) {

                                Toast.makeText(
                                    context,
                                    "This offer does not have sufficient stock.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else if (quantityToAdd <= 0) {

                                Toast.makeText(
                                    context,
                                    "This offer already meets its minimum order quantity in your basket.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {

                                BasketManager.addMedicine(
                                    medicineName = offer.medicineName,
                                    brand = offer.brand,
                                    salt = "",
                                    strength = "",
                                    unitPrice = offer.discountedUnitPrice,
                                    quantity = quantityToAdd,
                                    supplierName = offer.supplierName
                                )

                                Toast.makeText(
                                    context,
                                    "$quantityToAdd units added to basket.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        enabled = selectedOffer!!.isAvailable &&
                                quantityToAdd > 0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ComparisonTeal
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Text(
                            text = when {
                                !selectedOffer!!.isAvailable ->
                                    "Insufficient Stock"

                                quantityToAdd == 0 ->
                                    "Minimum Quantity Already Added"

                                else ->
                                    "Add $quantityToAdd Units to Basket"
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ComparisonDetailRow(
    label: String,
    value: String,
    valueColor: Color = ComparisonDark
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = label,
            fontSize = 13.sp,
            color = ComparisonGrey,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = valueColor
        )
    }
}