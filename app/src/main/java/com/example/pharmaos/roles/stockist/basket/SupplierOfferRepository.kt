package com.example.pharmaos.roles.stockist.basket

object SupplierOfferRepository {

    // Demo supplier offers.
    // Later, these offers will come from the Spring Boot backend.

    private val offers = listOf(

        // Paracetamol offers
        SupplierOffer(
            offerId = "OFF001",
            medicineName = "Paracetamol",
            brand = "Brand A",
            supplierName = "ABC Pharma Distributors",
            unitPrice = 25.0,
            minimumOrderQuantity = 10,
            availableQuantity = 500,
            discountPercentage = 5.0,
            schemeDescription = "5% discount",
            deliveryCharge = 30.0,
            estimatedDeliveryDays = 2,
            trustScore = 92.0
        ),

        SupplierOffer(
            offerId = "OFF002",
            medicineName = "Paracetamol",
            brand = "Brand B",
            supplierName = "MediCare Suppliers",
            unitPrice = 23.0,
            minimumOrderQuantity = 20,
            availableQuantity = 300,
            discountPercentage = 2.0,
            schemeDescription = "2% discount",
            deliveryCharge = 20.0,
            estimatedDeliveryDays = 3,
            trustScore = 88.0
        ),

        SupplierOffer(
            offerId = "OFF003",
            medicineName = "Paracetamol",
            brand = "Brand C",
            supplierName = "HealthPlus Distributors",
            unitPrice = 24.0,
            minimumOrderQuantity = 10,
            availableQuantity = 200,
            discountPercentage = 0.0,
            schemeDescription = "No scheme",
            deliveryCharge = 0.0,
            estimatedDeliveryDays = 1,
            trustScore = 95.0
        ),

        // Amoxicillin offers
        SupplierOffer(
            offerId = "OFF004",
            medicineName = "Amoxicillin",
            brand = "Brand D",
            supplierName = "ABC Pharma Distributors",
            unitPrice = 85.0,
            minimumOrderQuantity = 10,
            availableQuantity = 150,
            discountPercentage = 5.0,
            schemeDescription = "5% discount",
            deliveryCharge = 40.0,
            estimatedDeliveryDays = 2,
            trustScore = 92.0
        ),

        SupplierOffer(
            offerId = "OFF005",
            medicineName = "Amoxicillin",
            brand = "Brand E",
            supplierName = "MediCare Suppliers",
            unitPrice = 80.0,
            minimumOrderQuantity = 25,
            availableQuantity = 100,
            discountPercentage = 3.0,
            schemeDescription = "3% discount",
            deliveryCharge = 25.0,
            estimatedDeliveryDays = 3,
            trustScore = 88.0
        ),

        // Pantoprazole offers
        SupplierOffer(
            offerId = "OFF006",
            medicineName = "Pantoprazole",
            brand = "Brand G",
            supplierName = "HealthPlus Distributors",
            unitPrice = 55.0,
            minimumOrderQuantity = 10,
            availableQuantity = 250,
            discountPercentage = 4.0,
            schemeDescription = "4% discount",
            deliveryCharge = 20.0,
            estimatedDeliveryDays = 1,
            trustScore = 95.0
        ),

        SupplierOffer(
            offerId = "OFF007",
            medicineName = "Pantoprazole",
            brand = "Brand H",
            supplierName = "MediCare Suppliers",
            unitPrice = 52.0,
            minimumOrderQuantity = 20,
            availableQuantity = 180,
            discountPercentage = 0.0,
            schemeDescription = "No scheme",
            deliveryCharge = 30.0,
            estimatedDeliveryDays = 2,
            trustScore = 88.0
        ),

        // Vitamin C offers
        SupplierOffer(
            offerId = "OFF008",
            medicineName = "Vitamin C",
            brand = "Brand J",
            supplierName = "ABC Pharma Distributors",
            unitPrice = 40.0,
            minimumOrderQuantity = 10,
            availableQuantity = 400,
            discountPercentage = 5.0,
            schemeDescription = "5% discount",
            deliveryCharge = 20.0,
            estimatedDeliveryDays = 2,
            trustScore = 92.0
        ),

        SupplierOffer(
            offerId = "OFF009",
            medicineName = "Vitamin C",
            brand = "Brand K",
            supplierName = "HealthPlus Distributors",
            unitPrice = 38.0,
            minimumOrderQuantity = 15,
            availableQuantity = 250,
            discountPercentage = 2.0,
            schemeDescription = "2% discount",
            deliveryCharge = 0.0,
            estimatedDeliveryDays = 1,
            trustScore = 95.0
        )
    )

    // Get all offers
    fun getAllOffers(): List<SupplierOffer> {
        return offers
    }

    // Get offers for a particular medicine
    fun getOffersForMedicine(
        medicineName: String
    ): List<SupplierOffer> {
        return offers.filter {
            it.medicineName.equals(
                medicineName,
                ignoreCase = true
            )
        }
    }

    // Get only offers with sufficient available stock
    fun getAvailableOffers(
        medicineName: String
    ): List<SupplierOffer> {
        return getOffersForMedicine(medicineName)
            .filter { it.isAvailable }
    }
}
