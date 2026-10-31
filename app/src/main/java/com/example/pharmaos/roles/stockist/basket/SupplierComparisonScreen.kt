package com.example.pharmaos.roles.stockist.basket

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val PharmaTeal = Color(0xFF008080)
private val PharmaBackground = Color(0xFFF1F5FF)
private val PharmaGreen = Color(0xFF15803D)
private val PharmaAmber = Color(0xFFB45309)

@Composable
fun SupplierComparisonScreen() {

    var selectedMedicine by remember {
        mutableStateOf("Paracetamol")
    }

    var selectedOfferId by remember {
        mutableStateOf("")
    }

    val medicineNames = listOf(
        "Paracetamol",
        "Amoxicillin",
        "Pantoprazole",
        "Vitamin C"
    )

    val offers = SupplierOfferRepository
        .getOffersForMedicine(selectedMedicine)

    val selectedOffer = offers.firstOrNull {
        it.offerId == selectedOfferId
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Text(
            text = "Supplier Comparison",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        Text(
            text = "Compare prices, discounts, stock and delivery before selecting a supplier.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Text(
            text = "Select Medicine",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Row(
            modifier = Modifier.horizontalScroll(
                rememberScrollState()
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            medicineNames.forEach { medicine ->

                if (selectedMedicine == medicine) {
                    Button(
                        onClick = {
                            selectedMedicine = medicine
                            selectedOfferId = ""
                        }
                    ) {
                        Text(medicine)
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            selectedMedicine = medicine
                            selectedOfferId = ""
                        }
                    ) {
                        Text(medicine)
                    }
                }
            }
        }

        Text(
            text = "Available Supplier Offers (${offers.size})",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        if (offers.isEmpty()) {
            Text("No supplier offers available.")
        }

        offers.forEach { offer ->

            val isSelected = selectedOfferId == offer.offerId

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) {
                        Color(0xFFE0F2F1)
                    } else {
                        Color.White
                    }
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {

                    Text(
                        text = offer.supplierName,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = PharmaTeal
                    )

                    Text(
                        text = "Brand: ${offer.brand}",
                        fontSize = 14.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Original Price")
                        Text("₹${"%.2f".format(offer.unitPrice)}")
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Discounted Price",
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "₹${"%.2f".format(offer.discountedUnitPrice)}",
                            color = PharmaGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "Discount: ${offer.discountPercentage}%",
                        color = PharmaGreen
                    )

                    Text(
                        text = "Scheme: ${offer.schemeDescription}"
                    )

                    Text(
                        text = "Minimum Order Quantity: ${offer.minimumOrderQuantity}"
                    )

                    Text(
                        text = "Available Stock: ${offer.availableQuantity}"
                    )

                    Text(
                        text = "Delivery Charge: ₹${"%.2f".format(offer.deliveryCharge)}"
                    )

                    Text(
                        text = "Estimated Delivery: ${offer.estimatedDeliveryDays} day(s)"
                    )

                    Text(
                        text = "Supplier Trust Score: ${offer.trustScore}/100",
                        color = PharmaAmber,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = if (offer.isAvailable) {
                            "Stock Status: Available"
                        } else {
                            "Stock Status: Insufficient stock for MOQ"
                        },
                        color = if (offer.isAvailable) {
                            PharmaGreen
                        } else {
                            Color.Red
                        },
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Button(
                        onClick = {
                            selectedOfferId = offer.offerId
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = offer.isAvailable
                    ) {
                        Text(
                            if (isSelected) {
                                "Selected"
                            } else {
                                "Select This Offer"
                            }
                        )
                    }
                }
            }
        }

        if (selectedOffer != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = PharmaBackground
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Selected Supplier",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    Text("Supplier: ${selectedOffer.supplierName}")
                    Text("Medicine: ${selectedOffer.medicineName}")
                    Text("Brand: ${selectedOffer.brand}")
                    Text(
                        "Discounted Unit Price: ₹${"%.2f".format(selectedOffer.discountedUnitPrice)}"
                    )

                    Text(
                        text = "Offer selected successfully.",
                        color = PharmaGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
