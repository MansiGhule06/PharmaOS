package com.example.pharmaos.roles.stockist


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

// PharmaOS theme colors
private val PharmaTeal = Color(0xFF008080)
private val PharmaPurple = Color(0xFF800080)
private val PharmaBackground = Color(0xFFF1F5FF)
private val PharmaText = Color(0xFF333333)
private val PharmaGrey = Color(0xFF999999)
private val PharmaGreen = Color(0xFF15803D)
private val PharmaAmber = Color(0xFFB45309)

class StockistDashboardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                StockistDashboardScreen(
                    onLogout = {
                        val intent = Intent(
                            this,
                            com.example.pharmaos.roles.RoleSelectionActivity::class.java
                        )
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockistDashboardScreen(
    onLogout: () -> Unit
) {
    val bottomItems = listOf("Home", "Medicines", "Orders", "Basket")
    val drawerItems = listOf(
        "My Profile",
        "Business Information",
        "Change Password",
        "Documents",
        "Verification Status",
        "Notifications",
        "Help & Support"
    )

    var selectedBottomItem by remember { mutableStateOf("Home") }
    var selectedDrawerItem by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White
            ) {
                // Drawer profile header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PharmaTeal)
                        .padding(20.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = Color.White,
                        modifier = Modifier.size(58.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = PharmaTeal,
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Stockist Name",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "stockist@example.com",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Stockist / Distributor",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                drawerItems.forEach { item ->
                    val itemIcon = when (item) {
                        "My Profile" -> Icons.Default.Person
                        "Business Information" -> Icons.Default.Business
                        "Change Password" -> Icons.Default.Lock
                        "Documents" -> Icons.Default.Description
                        "Verification Status" -> Icons.Default.VerifiedUser
                        "Notifications" -> Icons.Default.Notifications
                        else -> Icons.Default.Help
                    }

                    NavigationDrawerItem(
                        label = { Text(item) },
                        selected = selectedDrawerItem == item,
                        onClick = {
                            selectedDrawerItem = item
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(itemIcon, contentDescription = null)
                        },
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 2.dp
                        ),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFFE0F2F1),
                            selectedIconColor = PharmaTeal,
                            selectedTextColor = PharmaTeal
                        )
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    onClick = onLogout,
                    icon = {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = null,
                            tint = Color(0xFFB91C1C)
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color(0xFFB91C1C)
                    )
                )
            }
        }
    ) {
        Scaffold(
            containerColor = PharmaBackground,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Open menu",
                            tint = PharmaTeal
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "PharmaOS",
                            color = PharmaTeal,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Stockist Dashboard",
                            color = PharmaGrey,
                            fontSize = 11.sp
                        )
                    }

                    IconButton(onClick = {
                        selectedDrawerItem = "Notifications"
                    }) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = PharmaTeal
                        )
                    }
                }
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Color.White
                ) {
                    bottomItems.forEach { item ->
                        val icon = when (item) {
                            "Home" -> Icons.Default.Home
                            "Medicines" -> Icons.Default.Search
                            "Orders" -> Icons.Default.ShoppingBag
                            else -> Icons.Default.ShoppingCart
                        }

                        NavigationBarItem(
                            selected = selectedBottomItem == item,
                            onClick = {
                                selectedBottomItem = item
                                selectedDrawerItem = ""
                            },
                            icon = {
                                Icon(icon, contentDescription = item)
                            },
                            label = { Text(item, fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PharmaTeal,
                                selectedTextColor = PharmaTeal,
                                indicatorColor = Color(0xFFE0F2F1)
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->

            val pageTitle = if (selectedDrawerItem.isNotEmpty()) {
                selectedDrawerItem
            } else {
                selectedBottomItem
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    when {
                        selectedDrawerItem.isNotEmpty() -> {
                            DrawerPageContent(
                                pageTitle = selectedDrawerItem
                            )
                        }

                        selectedBottomItem == "Home" -> {
                            StockistHomeContent()
                        }

                        else -> {
                            SimpleStockistPage(
                                pageTitle = pageTitle
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StockistHomeContent() {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome section
        Column {
            Text(
                text = "Welcome back!",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = PharmaText
            )
            Text(
                text = "Here's your business overview.",
                color = PharmaGrey,
                fontSize = 13.sp
            )
        }

        // Verification status
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF7E6)
            )
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Schedule,
                    contentDescription = null,
                    tint = PharmaAmber
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        "Verification Pending",
                        color = PharmaAmber,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Your business documents are awaiting review.",
                        color = PharmaText,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Text(
            text = "Business Overview",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = PharmaText
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DashboardStatCard(
                title = "Total Orders",
                value = "24",
                icon = Icons.Default.ShoppingBag,
                modifier = Modifier.weight(1f)
            )
            DashboardStatCard(
                title = "Pending",
                value = "05",
                icon = Icons.Default.Schedule,
                modifier = Modifier.weight(1f)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DashboardStatCard(
                title = "Medicines",
                value = "128",
                icon = Icons.Default.Medication,
                modifier = Modifier.weight(1f)
            )
            DashboardStatCard(
                title = "Suppliers",
                value = "12",
                icon = Icons.Default.Business,
                modifier = Modifier.weight(1f)
            )
        }

        // Quick actions
        Text(
            text = "Quick Actions",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = PharmaText
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            QuickActionCard(
                title = "Find Medicines",
                icon = Icons.Default.Search,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                title = "My Basket",
                icon = Icons.Default.ShoppingCart,
                modifier = Modifier.weight(1f)
            )
        }

        // Unique feature
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = PharmaTeal
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Best-Buy Basket Optimizer",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Compare supplier offers and find a cost-effective basket.",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp
                    )
                }
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        // Alerts
        Text(
            text = "Inventory Alerts",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = PharmaText
        )

        AlertCard(
            title = "Low Stock Alert",
            description = "3 medicines are running low on stock.",
            icon = Icons.Default.Warning,
            iconColor = PharmaAmber
        )

        AlertCard(
            title = "Expiry Reminder",
            description = "2 medicine batches are nearing expiry.",
            icon = Icons.Default.Event,
            iconColor = Color(0xFFB91C1C)
        )

        // Recent orders
        Text(
            text = "Recent Orders",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = PharmaText
        )

        RecentOrderCard(
            orderId = "#PH-1001",
            supplier = "ABC Pharmaceuticals",
            amount = "₹12,500",
            status = "Delivered"
        )

        RecentOrderCard(
            orderId = "#PH-1002",
            supplier = "MedLife Distributors",
            amount = "₹8,750",
            status = "Pending"
        )
    }
}

@Composable
private fun DashboardStatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = PharmaTeal,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                value,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = PharmaText
            )
            Text(
                title,
                fontSize = 11.sp,
                color = PharmaGrey
            )
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = PharmaPurple,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                title,
                color = PharmaText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun AlertCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    color = PharmaText,
                    fontSize = 13.sp
                )
                Text(
                    description,
                    color = PharmaGrey,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun RecentOrderCard(
    orderId: String,
    supplier: String,
    amount: String,
    status: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    orderId,
                    fontWeight = FontWeight.Bold,
                    color = PharmaText
                )
                Text(
                    supplier,
                    color = PharmaGrey,
                    fontSize = 12.sp
                )
                Text(
                    amount,
                    color = PharmaTeal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Text(
                status,
                color = if (status == "Delivered") {
                    PharmaGreen
                } else {
                    PharmaAmber
                },
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun DrawerPageContent(
    pageTitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = pageTitle,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = PharmaText
        )

        Text(
            text = when (pageTitle) {
                "My Profile" ->
                    "View and update your personal information."
                "Business Information" ->
                    "Manage your business name, address and GST details."
                "Change Password" ->
                    "Update your account password securely."
                "Documents" ->
                    "Upload or replace your business licence documents."
                "Verification Status" ->
                    "Check your business verification progress."
                "Notifications" ->
                    "Your important updates will appear here."
                else ->
                    "Contact PharmaOS support for assistance."
            },
            color = PharmaGrey,
            fontSize = 13.sp
        )

        if (pageTitle == "Verification Status") {
            AlertCard(
                title = "Verification Pending",
                description = "Your documents are waiting for administrator review.",
                icon = Icons.Default.Schedule,
                iconColor = PharmaAmber
            )
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    text = "This section will be implemented next.",
                    modifier = Modifier.padding(18.dp),
                    color = PharmaText,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun SimpleStockistPage(
    pageTitle: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = pageTitle,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = PharmaText
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = when (pageTitle) {
                        "Medicines" -> Icons.Default.Medication
                        "Orders" -> Icons.Default.ShoppingBag
                        else -> Icons.Default.ShoppingCart
                    },
                    contentDescription = null,
                    tint = PharmaTeal,
                    modifier = Modifier.size(35.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "$pageTitle screen",
                    fontWeight = FontWeight.Bold,
                    color = PharmaText,
                    fontSize = 17.sp
                )

                Text(
                    text = "We'll build this section in the next development step.",
                    color = PharmaGrey,
                    fontSize = 12.sp
                )
            }
        }
    }
}