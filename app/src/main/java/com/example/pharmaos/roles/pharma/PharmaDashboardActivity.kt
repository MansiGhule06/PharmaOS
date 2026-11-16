
package com.example.pharmaos.roles.pharma

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pharmaos.roles.RoleSelectionActivity
import kotlinx.coroutines.launch

private val PharmaTeal = Color(0xFF008080)
private val PharmaBackground = Color(0xFFF1F5FF)

class PharmaDashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PharmaDashboardScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PharmaDashboardScreen() {

    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White
            ) {
                Text(
                    text = "PharmaOS",
                    modifier = Modifier.padding(20.dp),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = PharmaTeal
                )

                Text(
                    text = "PHARMACEUTICAL COMPANY",
                    modifier = Modifier.padding(
                        start = 20.dp,
                        bottom = 16.dp
                    ),
                    color = Color.Gray,
                    fontSize = 11.sp
                )

                HorizontalDivider()

                DrawerOption("Home") {
                    scope.launch { drawerState.close() }
                }

                DrawerOption("Manage Medicines") {
                    scope.launch { drawerState.close() }
                    context.startActivity(
                        Intent(context, PharmaMedicineActivity::class.java)
                    )
                }

                DrawerOption("Orders") {
                    scope.launch { drawerState.close() }
                    context.startActivity(
                        Intent(context, PharmaOrdersActivity::class.java)
                    )
                }

                DrawerOption("Batch Management") {
                    scope.launch { drawerState.close() }
                    context.startActivity(
                        Intent(context, PharmaBatchActivity::class.java)
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                DrawerOption("My Profile") {
                    openPharmaPage(context, "My Profile")
                    scope.launch { drawerState.close() }
                }

                DrawerOption("Business Information") {
                    openPharmaPage(context, "Business Information")
                    scope.launch { drawerState.close() }
                }

                DrawerOption("Documents") {
                    openPharmaPage(context, "Documents")
                    scope.launch { drawerState.close() }
                }

                DrawerOption("Verification Status") {
                    openPharmaPage(context, "Verification Status")
                    scope.launch { drawerState.close() }
                }

                DrawerOption("Notifications") {
                    openPharmaPage(context, "Notifications")
                    scope.launch { drawerState.close() }
                }

                DrawerOption("Help & Support") {
                    openPharmaPage(context, "Help & Support")
                    scope.launch { drawerState.close() }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()

                DrawerOption("Logout") {
                    scope.launch { drawerState.close() }
                    val intent = Intent(
                        context,
                        RoleSelectionActivity::class.java
                    )
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Pharma Dashboard",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Open navigation drawer"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PharmaTeal,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            },
            containerColor = PharmaBackground
        ) { padding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        "Welcome, Pharma Company!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )

                    Text(
                        "Manage medicines, batches and stockist orders.",
                        color = Color.Gray
                    )
                }

                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DashboardStatCard(
                            "Products",
                            PharmaRepository.getTotalProducts().toString(),
                            Modifier.weight(1f)
                        )

                        DashboardStatCard(
                            "Total Stock",
                            PharmaRepository.getTotalStock().toString(),
                            Modifier.weight(1f)
                        )
                    }
                }

                item {
                    Text(
                        "Quick Actions",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = {
                            context.startActivity(
                                Intent(context, PharmaMedicineActivity::class.java)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PharmaTeal
                        )
                    ) {
                        Text("Manage Medicines")
                    }

                    Button(
                        onClick = {
                            context.startActivity(
                                Intent(context, PharmaOrdersActivity::class.java)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PharmaTeal
                        )
                    ) {
                        Text("View Stockist Orders")
                    }

                    OutlinedButton(
                        onClick = {
                            context.startActivity(
                                Intent(context, PharmaBatchActivity::class.java)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Manage Batches")
                    }
                }

                item {
                    Text(
                        "Medicine Catalogue",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(PharmaRepository.medicines.size) { index ->
                    val medicine = PharmaRepository.medicines[index]

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                medicine.medicineName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            Text("Brand: ${medicine.brand}")
                            Text("Strength: ${medicine.strength}")
                            Text("Price: ₹${medicine.price}")
                            Text("Available Stock: ${medicine.availableStock}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerOption(
    title: String,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(title) },
        selected = false,
        onClick = onClick,
        modifier = Modifier.padding(
            horizontal = 12.dp,
            vertical = 2.dp
        ),
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = Color(0xFF333333)
        )
    )
}

private fun openPharmaPage(
    context: android.content.Context,
    page: String
) {
    val intent = Intent(context, PharmaInfoActivity::class.java)
    intent.putExtra("PAGE_TITLE", page)
    context.startActivity(intent)
}

@Composable
fun DashboardStatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(title, color = Color.Gray)
            Text(
                value,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = PharmaTeal
            )
        }
    }
}
