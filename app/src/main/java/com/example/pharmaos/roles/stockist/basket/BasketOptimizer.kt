package com.example.pharmaos.roles.stockist.basket

data class OptimizedOfferSelection(
    val medicineName: String,
    val supplierName: String,
    val brand: String,
    val orderQuantity: Int,
    val unitPrice: Double,
    val medicineCost: Double,
    val deliveryCharge: Double,
    val totalCost: Double,
    val estimatedDeliveryDays: Int
)

data class BasketOptimizationResult(
    val selections: List<OptimizedOfferSelection>,
    val totalCost: Double,
    val unavailableMedicines: List<String>
)

object BasketOptimizer {

    fun optimizeBasket(
        requestedItems: List<BasketItem>,
        allOffers: List<SupplierOffer>
    ): BasketOptimizationResult {

        val selections = mutableListOf<OptimizedOfferSelection>()
        val unavailableMedicines = mutableListOf<String>()

        // Group requested quantities by medicine name
        val medicineDemands = requestedItems.groupBy {
            it.medicineName
        }

        for ((medicineName, basketItems) in medicineDemands) {

            val requiredQuantity = basketItems.sumOf {
                it.quantity
            }

            val suitableOffers = allOffers.filter { offer ->
                offer.medicineName.equals(
                    medicineName,
                    ignoreCase = true
                ) &&
                        offer.availableQuantity >=
                        maxOf(requiredQuantity, offer.minimumOrderQuantity)
            }

            if (suitableOffers.isEmpty()) {
                unavailableMedicines.add(medicineName)
                continue
            }

            // Find the offer with the lowest total cost
            val bestOffer = suitableOffers.minByOrNull { offer ->

                val orderQuantity = maxOf(
                    requiredQuantity,
                    offer.minimumOrderQuantity
                )

                val medicineCost =
                    offer.discountedUnitPrice * orderQuantity

                medicineCost + offer.deliveryCharge
            } ?: continue

            val finalQuantity = maxOf(
                requiredQuantity,
                bestOffer.minimumOrderQuantity
            )

            val medicineCost =
                bestOffer.discountedUnitPrice * finalQuantity

            val totalCost =
                medicineCost + bestOffer.deliveryCharge

            selections.add(
                OptimizedOfferSelection(
                    medicineName = medicineName,
                    supplierName = bestOffer.supplierName,
                    brand = bestOffer.brand,
                    orderQuantity = finalQuantity,
                    unitPrice = bestOffer.discountedUnitPrice,
                    medicineCost = medicineCost,
                    deliveryCharge = bestOffer.deliveryCharge,
                    totalCost = totalCost,
                    estimatedDeliveryDays =
                        bestOffer.estimatedDeliveryDays
                )
            )
        }

        val grandTotal = selections.sumOf {
            it.totalCost
        }

        return BasketOptimizationResult(
            selections = selections,
            totalCost = grandTotal,
            unavailableMedicines = unavailableMedicines
        )
    }
}
