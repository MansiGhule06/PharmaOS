
package com.example.pharmaos.roles.manufacturer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import com.example.pharmaos.roles.RoleSelectionActivity
import com.example.pharmaos.roles.manufacturer.ManufacturerBatchActivity

private val ManufacturerTeal = Color(0xFF008080)
private val ManufacturerBackground = Color(0xFFF1F5FF)
private val ManufacturerDark = Color(0xFF333333)
private val ManufacturerGrey = Color(0xFF777777)

// --------------------------------------------------
// ACTIVITY
// --------------------------------------------------

class ManufacturerDashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ManufacturerDashboardScreen()
            }
        }
    }
}

// --------------------------------------------------
// DEMO ORDER MODEL
// --------------------------------------------------

private data class DashboardManufacturerOrder(
    val orderId: String,
    val stockistName: String,
    val medicineName: String,
    val quantity: Int,
    val orderDate: String,
    val status: String
)

// --------------------------------------------------
// MAIN DASHBOARD
// --------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManufacturerDashboardScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    var selectedTab by remember { mutableIntStateOf(0) }

    var selectedDrawerItem by remember {
        mutableStateOf("")
    }

    var showMedicineDialog by remember {
        mutableStateOf(false)
    }

    var editingMedicine by remember {
        mutableStateOf<ManufacturerMedicine?>(null)
    }

    var medicineToDelete by remember {
        mutableStateOf<ManufacturerMedicine?>(null)
    }

    val orders = remember {
        mutableStateListOf(
            DashboardManufacturerOrder(
                "ORD-M001",
                "ABC Pharma Distributors",
                "Paracetamol",
                500,
                "13 Nov 2026",
                "Pending"
            ),
            DashboardManufacturerOrder(
                "ORD-M002",
                "HealthCare Medical Store",
                "Amoxicillin",
                300,
                "12 Nov 2026",
                "Accepted"
            ),
            DashboardManufacturerOrder(
                "ORD-M003",
                "City Medical Distributors",
                "Pantoprazole",
                200,
                "11 Nov 2026",
                "Dispatched"
            )
        )
    }

    val drawerItems = listOf(
        "My Profile" to Icons.Default.Person,
        "Business Information" to Icons.Default.Business,
        "Batch Management" to Icons.Default.Inventory2,
        "Change Password" to Icons.Default.Lock,
        "Documents" to Icons.Default.Description,
        "Verification Status" to Icons.Default.Verified,
        "Notifications" to Icons.Default.Notifications,
        "Help & Support" to Icons.Default.Help
    )

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                drawerShape = RoundedCornerShape(
                    topEnd = 20.dp,
                    bottomEnd = 20.dp
                )
            ) {

                // DRAWER HEADER

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ManufacturerTeal)
                        .padding(20.dp)
                ) {

                    Icon(
                        Icons.Default.Business,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Manufacturer Name",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "manufacturer@example.com",
                        color = Color.White,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            "Manufacturer Account",
                            color = Color.White,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 5.dp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "MENU",
                    color = ManufacturerGrey,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 8.dp
                    )
                )

                drawerItems.forEach { item ->

                    NavigationDrawerItem(
                        label = {
                            Text(item.first)
                        },

                        selected = selectedDrawerItem == item.first,

                        icon = {
                            Icon(
                                item.second,
                                contentDescription = item.first
                            )
                        },

                        onClick = {

                            scope.launch {
                                drawerState.close()
                            }

                            if (item.first == "Batch Management") {

                                context.startActivity(
                                    Intent(
                                        context,
                                        ManufacturerBatchActivity::class.java
                                    )
                                )

                            } else {
                                selectedDrawerItem = item.first
                            }
                        },

                        modifier = Modifier.padding(
                            horizontal = 12.dp
                        ),

                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFFE0F2F1),
                            selectedIconColor = ManufacturerTeal,
                            selectedTextColor = ManufacturerTeal,
                            unselectedIconColor = ManufacturerGrey,
                            unselectedTextColor = ManufacturerDark
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                HorizontalDivider()

                NavigationDrawerItem(
                    label = {
                        Text(
                            "Logout",
                            color = Color(0xFFB91C1C)
                        )
                    },

                    selected = false,

                    icon = {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "Logout",
                            tint = Color(0xFFB91C1C)
                        )
                    },

                    onClick = {

                        val intent = Intent(
                            context,
                            RoleSelectionActivity::class.java
                        )

                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK

                        context.startActivity(intent)
                    },

                    modifier = Modifier.padding(
                        horizontal = 12.dp
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    ) {

        Scaffold(
            containerColor = ManufacturerBackground,

            topBar = {

                TopAppBar(
                    title = {
                        Column {
                            Text(
                                "PharmaOS",
                                fontWeight = FontWeight.Bold,
                                color = ManufacturerTeal
                            )

                            Text(
                                "Manufacturer Portal",
                                fontSize = 12.sp,
                                color = ManufacturerGrey
                            )
                        }
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
                                contentDescription = "Open Menu",
                                tint = ManufacturerTeal
                            )
                        }
                    },

                    actions = {
                        IconButton(
                            onClick = {
                                selectedDrawerItem = "Notifications"
                            }
                        ) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = ManufacturerTeal
                            )
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
            },

            bottomBar = {

                NavigationBar(
                    containerColor = Color.White
                ) {

                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = {
                            Icon(Icons.Default.Home, null)
                        },
                        label = { Text("Home") }
                    )

                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = {
                            Icon(Icons.Default.Inventory, null)
                        },
                        label = { Text("Products") }
                    )

                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = {
                            Icon(Icons.Default.ShoppingCart, null)
                        },
                        label = { Text("Orders") }
                    )

                    NavigationBarItem(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        icon = {
                            Icon(Icons.Default.Person, null)
                        },
                        label = { Text("Profile") }
                    )
                }
            }
        ) { padding ->

            when (selectedTab) {

                0 -> ManufacturerHomeScreen(
                    modifier = Modifier.padding(padding),
                    orders = orders,
                    onManageProducts = {
                        selectedTab = 1
                    },
                    onViewOrders = {
                        selectedTab = 2
                    }
                )

                1 -> ManufacturerProductsScreen(
                    modifier = Modifier.padding(padding),
                    onAddMedicine = {
                        editingMedicine = null
                        showMedicineDialog = true
                    },
                    onEditMedicine = { medicine ->
                        editingMedicine = medicine
                        showMedicineDialog = true
                    },
                    onDeleteMedicine = { medicine ->
                        medicineToDelete = medicine
                    }
                )

                2 -> ManufacturerOrdersScreen(
                    modifier = Modifier.padding(padding),
                    orders = orders,
                    onUpdateStatus = { order, newStatus ->

                        val index = orders.indexOf(order)

                        if (index != -1) {
                            orders[index] = order.copy(
                                status = newStatus
                            )
                        }
                    }
                )

                3 -> ManufacturerProfileScreen(
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }

    // DRAWER INFORMATION DIALOG

    if (selectedDrawerItem.isNotEmpty()) {

        AlertDialog(
            onDismissRequest = {
                selectedDrawerItem = ""
            },

            title = {
                Text(selectedDrawerItem)
            },

            text = {
                Text(
                    when (selectedDrawerItem) {
                        "My Profile" ->
                            "View and manage your manufacturer profile."

                        "Business Information" ->
                            "Manage your company and business details."

                        "Change Password" ->
                            "Password management will be connected here."

                        "Documents" ->
                            "Manage your business registration documents."

                        "Verification Status" ->
                            "View your manufacturer verification status."

                        "Notifications" ->
                            "Your notifications will appear here."

                        "Help & Support" ->
                            "Contact PharmaOS support for assistance."

                        else -> ""
                    }
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        selectedDrawerItem = ""
                    }
                ) {
                    Text("Close")
                }
            }
        )
    }

    // ADD / EDIT MEDICINE DIALOG

    if (showMedicineDialog) {

        AddEditManufacturerMedicineDialog(
            existingMedicine = editingMedicine,

            onDismiss = {
                showMedicineDialog = false
                editingMedicine = null
            },

            onSave = { medicine ->

                if (editingMedicine == null) {
                    ManufacturerRepository.addMedicine(medicine)
                } else {
                    ManufacturerRepository.updateMedicine(medicine)
                }

                showMedicineDialog = false
                editingMedicine = null
            }
        )
    }

    // DELETE CONFIRMATION

    medicineToDelete?.let { medicine ->

        AlertDialog(
            onDismissRequest = {
                medicineToDelete = null
            },

            title = {
                Text("Delete Medicine")
            },

            text = {
                Text(
                    "Are you sure you want to delete ${medicine.medicineName}?"
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        ManufacturerRepository.deleteMedicine(medicine.id)
                        medicineToDelete = null
                    }
                ) {
                    Text("Delete", color = Color.Red)
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        medicineToDelete = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

// --------------------------------------------------
// HOME SCREEN
// --------------------------------------------------

@Composable
private fun ManufacturerHomeScreen(
    modifier: Modifier = Modifier,
    orders: List<DashboardManufacturerOrder>,
    onManageProducts: () -> Unit,
    onViewOrders: () -> Unit
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                "Welcome, Manufacturer!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = ManufacturerDark
            )

            Text(
                "Manage your medicines and business.",
                color = ManufacturerGrey
            )
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatisticCard(
                    "Total Products",
                    ManufacturerRepository.getTotalProducts().toString(),
                    Icons.Default.Inventory,
                    Modifier.weight(1f)
                )

                StatisticCard(
                    "Total Stock",
                    ManufacturerRepository.getTotalStock().toString(),
                    Icons.Default.Warehouse,
                    Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatisticCard(
                    "Total Orders",
                    orders.size.toString(),
                    Icons.Default.ShoppingCart,
                    Modifier.weight(1f)
                )

                StatisticCard(
                    "Pending Orders",
                    orders.count { it.status == "Pending" }.toString(),
                    Icons.Default.PendingActions,
                    Modifier.weight(1f)
                )
            }
        }

        item {
            Text(
                "Quick Actions",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ManufacturerDark
            )
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    "Manage Products",
                    Icons.Default.Medication,
                    Modifier.weight(1f),
                    onManageProducts
                )

                QuickActionCard(
                    "Manage Orders",
                    Icons.Default.ReceiptLong,
                    Modifier.weight(1f),
                    onViewOrders
                )
            }
        }

        item {
            Text(
                "Recent Products",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ManufacturerDark
            )
        }

        items(ManufacturerRepository.medicines.take(3)) { medicine ->
            ManufacturerMedicineCard(
                medicine = medicine,
                onEdit = onManageProducts,
                onDelete = {}
            )
        }
    }
}

// --------------------------------------------------
// STATISTIC CARD
// --------------------------------------------------

@Composable
private fun StatisticCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = ManufacturerTeal,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = ManufacturerDark
            )

            Text(
                title,
                fontSize = 12.sp,
                color = ManufacturerGrey
            )
        }
    }
}

// --------------------------------------------------
// QUICK ACTION CARD
// --------------------------------------------------

@Composable
private fun QuickActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = ManufacturerTeal,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                title,
                fontWeight = FontWeight.SemiBold,
                color = ManufacturerDark
            )
        }
    }
}

// --------------------------------------------------
// PRODUCTS SCREEN
// --------------------------------------------------

@Composable
private fun ManufacturerProductsScreen(
    modifier: Modifier = Modifier,
    onAddMedicine: () -> Unit,
    onEditMedicine: (ManufacturerMedicine) -> Unit,
    onDeleteMedicine: (ManufacturerMedicine) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "My Products",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = ManufacturerDark
                    )

                    Text(
                        "${ManufacturerRepository.medicines.size} medicines listed",
                        color = ManufacturerGrey
                    )
                }

                Button(
                    onClick = onAddMedicine,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ManufacturerTeal
                    )
                ) {
                    Icon(Icons.Default.Add, null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add")
                }
            }
        }

        if (ManufacturerRepository.medicines.isEmpty()) {
            item {
                Text(
                    "No medicines added yet.",
                    color = ManufacturerGrey
                )
            }
        }

        items(
            ManufacturerRepository.medicines,
            key = { it.id }
        ) { medicine ->

            ManufacturerMedicineCard(
                medicine = medicine,
                onEdit = {
                    onEditMedicine(medicine)
                },
                onDelete = {
                    onDeleteMedicine(medicine)
                }
            )
        }
    }
}

// --------------------------------------------------
// MEDICINE CARD
// --------------------------------------------------

@Composable
private fun ManufacturerMedicineCard(
    medicine: ManufacturerMedicine,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        medicine.medicineName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = ManufacturerDark
                    )

                    Text(
                        medicine.brand,
                        color = ManufacturerTeal,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    "₹${medicine.price}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ManufacturerTeal
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp)
            )

            MedicineDetail("Salt", medicine.salt)
            MedicineDetail("Strength", medicine.strength)
            MedicineDetail("Category", medicine.category)
            MedicineDetail("Available Stock", medicine.availableStock.toString())
            MedicineDetail("Batch Number", medicine.batchNumber)
            MedicineDetail("Expiry Date", medicine.expiryDate)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Edit")
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Red
                    )
                ) {
                    Icon(Icons.Default.Delete, null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Delete")
                }
            }
        }
    }
}

// --------------------------------------------------
// MEDICINE DETAIL
// --------------------------------------------------

@Composable
private fun MedicineDetail(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            color = ManufacturerGrey,
            fontSize = 13.sp
        )

        Text(
            value,
            color = ManufacturerDark,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// --------------------------------------------------
// ADD / EDIT MEDICINE DIALOG
// --------------------------------------------------

@Composable
private fun AddEditManufacturerMedicineDialog(
    existingMedicine: ManufacturerMedicine?,
    onDismiss: () -> Unit,
    onSave: (ManufacturerMedicine) -> Unit
) {
    var medicineName by remember(existingMedicine?.id) {
        mutableStateOf(existingMedicine?.medicineName ?: "")
    }

    var brand by remember(existingMedicine?.id) {
        mutableStateOf(existingMedicine?.brand ?: "")
    }

    var salt by remember(existingMedicine?.id) {
        mutableStateOf(existingMedicine?.salt ?: "")
    }

    var strength by remember(existingMedicine?.id) {
        mutableStateOf(existingMedicine?.strength ?: "")
    }

    var category by remember(existingMedicine?.id) {
        mutableStateOf(existingMedicine?.category ?: "")
    }

    var price by remember(existingMedicine?.id) {
        mutableStateOf(existingMedicine?.price?.toString() ?: "")
    }

    var stock by remember(existingMedicine?.id) {
        mutableStateOf(existingMedicine?.availableStock?.toString() ?: "")
    }

    var batchNumber by remember(existingMedicine?.id) {
        mutableStateOf(existingMedicine?.batchNumber ?: "")
    }

    var expiryDate by remember(existingMedicine?.id) {
        mutableStateOf(existingMedicine?.expiryDate ?: "")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                if (existingMedicine == null) {
                    "Add Medicine"
                } else {
                    "Edit Medicine"
                }
            )
        },

        text = {
            LazyColumn(
                modifier = Modifier.heightIn(max = 450.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    MedicineInput("Medicine Name", medicineName) {
                        medicineName = it
                    }
                }

                item {
                    MedicineInput("Brand Name", brand) {
                        brand = it
                    }
                }

                item {
                    MedicineInput("Salt / Active Ingredient", salt) {
                        salt = it
                    }
                }

                item {
                    MedicineInput("Strength", strength) {
                        strength = it
                    }
                }

                item {
                    MedicineInput("Category", category) {
                        category = it
                    }
                }

                item {
                    MedicineInput(
                        "Price",
                        price,
                        KeyboardType.Decimal
                    ) {
                        price = it
                    }
                }

                item {
                    MedicineInput(
                        "Available Stock",
                        stock,
                        KeyboardType.Number
                    ) {
                        stock = it
                    }
                }

                item {
                    MedicineInput("Batch Number", batchNumber) {
                        batchNumber = it
                    }
                }

                item {
                    MedicineInput("Expiry Date (MM/YYYY)", expiryDate) {
                        expiryDate = it
                    }
                }

                if (errorMessage.isNotEmpty()) {
                    item {
                        Text(
                            errorMessage,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        },

        confirmButton = {
            Button(
                onClick = {

                    val parsedPrice = price.toDoubleOrNull()
                    val parsedStock = stock.toIntOrNull()

                    if (
                        medicineName.isBlank() ||
                        brand.isBlank() ||
                        salt.isBlank() ||
                        strength.isBlank() ||
                        category.isBlank() ||
                        batchNumber.isBlank() ||
                        expiryDate.isBlank()
                    ) {
                        errorMessage = "Please fill all fields."
                    } else if (
                        parsedPrice == null ||
                        parsedPrice < 0 ||
                        parsedStock == null ||
                        parsedStock < 0
                    ) {
                        errorMessage = "Enter a valid price and stock."
                    } else {
                        onSave(
                            ManufacturerMedicine(
                                id = existingMedicine?.id
                                    ?: "MED${System.currentTimeMillis()}",
                                medicineName = medicineName.trim(),
                                brand = brand.trim(),
                                salt = salt.trim(),
                                strength = strength.trim(),
                                category = category.trim(),
                                price = parsedPrice,
                                availableStock = parsedStock,
                                batchNumber = batchNumber.trim(),
                                expiryDate = expiryDate.trim()
                            )
                        )
                    }
                },

                colors = ButtonDefaults.buttonColors(
                    containerColor = ManufacturerTeal
                )
            ) {
                Text("Save")
            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// --------------------------------------------------
// MEDICINE INPUT
// --------------------------------------------------

@Composable
private fun MedicineInput(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}

// --------------------------------------------------
// ORDERS SCREEN
// --------------------------------------------------

@Composable
private fun ManufacturerOrdersScreen(
    modifier: Modifier = Modifier,
    orders: List<DashboardManufacturerOrder>,
    onUpdateStatus: (DashboardManufacturerOrder, String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "Incoming Orders",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = ManufacturerDark
            )

            Text(
                "${orders.size} orders received",
                color = ManufacturerGrey
            )
        }

        if (orders.isEmpty()) {
            item {
                Text(
                    "No orders received yet.",
                    color = ManufacturerGrey
                )
            }
        }

        items(
            orders,
            key = { it.orderId }
        ) { order ->
            ManufacturerOrderCard(
                order = order,
                onUpdateStatus = { newStatus ->
                    onUpdateStatus(order, newStatus)
                }
            )
        }
    }
}

// --------------------------------------------------
// ORDER CARD
// --------------------------------------------------

@Composable
private fun ManufacturerOrderCard(
    order: DashboardManufacturerOrder,
    onUpdateStatus: (String) -> Unit
) {
    val nextStatus = when (order.status) {
        "Pending" -> "Accepted"
        "Accepted" -> "Processing"
        "Processing" -> "Dispatched"
        "Dispatched" -> "Delivered"
        else -> null
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    order.orderId,
                    fontWeight = FontWeight.Bold,
                    color = ManufacturerTeal
                )

                Text(
                    order.status,
                    fontWeight = FontWeight.Bold,
                    color = if (order.status == "Pending") {
                        Color(0xFFE69B00)
                    } else {
                        ManufacturerTeal
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                order.stockistName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = ManufacturerDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            MedicineDetail("Medicine", order.medicineName)
            MedicineDetail("Quantity", order.quantity.toString())
            MedicineDetail("Order Date", order.orderDate)

            if (nextStatus != null) {
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        onUpdateStatus(nextStatus)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ManufacturerTeal
                    )
                ) {
                    Text(
                        when (nextStatus) {
                            "Accepted" -> "Accept Order"
                            "Processing" -> "Start Processing"
                            "Dispatched" -> "Mark as Dispatched"
                            "Delivered" -> "Mark as Delivered"
                            else -> "Update Status"
                        }
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Order completed",
                    color = ManufacturerTeal,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// --------------------------------------------------
// PROFILE SCREEN
// --------------------------------------------------

@Composable
private fun ManufacturerProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Icon(
            Icons.Default.Business,
            contentDescription = null,
            tint = ManufacturerTeal,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Manufacturer Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = ManufacturerDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Manage your manufacturer account.",
            color = ManufacturerGrey
        )

        Spacer(modifier = Modifier.height(24.dp))

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
                    "Account Type",
                    color = ManufacturerGrey
                )

                Text(
                    "Manufacturer",
                    fontWeight = FontWeight.SemiBold
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Text(
                    "Total Products",
                    color = ManufacturerGrey
                )

                Text(
                    ManufacturerRepository.getTotalProducts().toString(),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
