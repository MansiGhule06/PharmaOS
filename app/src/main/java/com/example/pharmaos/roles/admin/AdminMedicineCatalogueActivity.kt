
package com.example.pharmaos.roles.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pharmaos.roles.manufacturer.ManufacturerRepository
import com.example.pharmaos.roles.pharma.PharmaRepository

class AdminMedicineCatalogueActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AdminMedicineCatalogueScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminMedicineCatalogueScreen() {

    val manufacturerMedicines = ManufacturerRepository.medicines
    val pharmaMedicines = PharmaRepository.medicines

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medicine Catalogue") }
            )
        },
        containerColor = Color(0xFFF1F5FF)
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Manufacturer Medicines",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(manufacturerMedicines.toList()) { medicine ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            medicine.medicineName,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text("Brand: ${medicine.brand}")
                        Text("Salt: ${medicine.salt}")
                        Text("Strength: ${medicine.strength}")
                        Text("Stock: ${medicine.availableStock}")
                        Text("Batch: ${medicine.batchNumber}")
                    }
                }
            }

            item {
                Spacer(Modifier.height(12.dp))
                Text(
                    "Pharmaceutical Company Medicines",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(pharmaMedicines.toList()) { medicine ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            medicine.medicineName,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text("Brand: ${medicine.brand}")
                        Text("Salt: ${medicine.salt}")
                        Text("Strength: ${medicine.strength}")
                        Text("Category: ${medicine.category}")
                        Text("Stock: ${medicine.availableStock}")
                        Text("Status: ${medicine.status}")
                    }
                }
            }

            if (manufacturerMedicines.isEmpty() && pharmaMedicines.isEmpty()) {
                item {
                    Text("No medicines available.")
                }
            }
        }
    }
}
