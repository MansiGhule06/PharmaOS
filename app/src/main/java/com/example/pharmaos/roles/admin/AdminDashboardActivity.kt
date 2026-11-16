
package com.example.pharmaos.roles.admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.pharmaos.roles.RoleSelectionActivity

class AdminDashboardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AdminDashboardScreen(
                onOpenPage = { page ->

                    when (page) {

                        "User Management" -> {
                            startActivity(
                                Intent(this, AdminUserManagementActivity::class.java)
                            )
                        }

                        "Verification Requests" -> {
                            startActivity(
                                Intent(this, AdminVerificationActivity::class.java)
                            )
                        }

                        "Medicine Catalogue" -> {
                            startActivity(
                                Intent(this, AdminMedicineCatalogueActivity::class.java)
                            )
                        }

                        else -> {
                            val intent = Intent(this, AdminInfoActivity::class.java)
                            intent.putExtra("PAGE_TITLE", page)
                            startActivity(intent)
                        }
                    }
                },

                onLogout = {
                    val intent = Intent(this, RoleSelectionActivity::class.java)

                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onOpenPage: (String) -> Unit,
    onLogout: () -> Unit
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuItems = listOf(
        "Dashboard",
        "User Management",
        "Verification Requests",
        "Medicine Catalogue",
        "Orders",
        "Notifications",
        "Help & Support"
    )

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            ModalDrawerSheet {

                Spacer(Modifier.height(24.dp))

                Text(
                    "PharmaOS Admin",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                HorizontalDivider()

                menuItems.forEach { item ->

                    NavigationDrawerItem(
                        label = { Text(item) },

                        selected = item == "Dashboard",

                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }

                            if (item != "Dashboard") {
                                onOpenPage(item)
                            }
                        },

                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 4.dp
                        )
                    )
                }

                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    onClick = onLogout,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Admin Dashboard")
                    },

                    navigationIcon = {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Text(
                                "☰",
                                color = Color(0xFF008080)
                            )
                        }
                    }
                )
            },

            containerColor = Color(0xFFF1F5FF)

        ) { padding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),

                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                item {
                    Text(
                        "Welcome, Administrator!",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text("Manage users and monitor platform activity.")
                }

                item {
                    AdminStatCard(
                        "Total Users",
                        AdminRepository.getTotalUsers().toString()
                    )
                }

                item {
                    AdminStatCard(
                        "Pending Verifications",
                        AdminRepository.getPendingUsers().toString()
                    )
                }

                item {
                    AdminStatCard(
                        "Verified Users",
                        AdminRepository.getVerifiedUsers().toString()
                    )
                }

                item {
                    AdminStatCard(
                        "Rejected Users",
                        AdminRepository.getRejectedUsers().toString()
                    )
                }

                item {
                    Text(
                        "Quick Actions",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                item {
                    Button(
                        onClick = {
                            onOpenPage("User Management")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF008080)
                        )
                    ) {
                        Text("Manage Users")
                    }
                }

                item {
                    Button(
                        onClick = {
                            onOpenPage("Verification Requests")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF008080)
                        )
                    ) {
                        Text("Review Verification Requests")
                    }
                }

                item {
                    Button(
                        onClick = {
                            onOpenPage("Medicine Catalogue")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF008080)
                        )
                    ) {
                        Text("View Medicine Catalogue")
                    }
                }

                item {
                    Text(
                        "Pending Verification Requests",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                items(
                    items = AdminRepository.users.filter {
                        it.status == "Pending"
                    },
                    key = { user -> user.id }
                ) { user ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),

                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {

                        Column(
                            Modifier.padding(16.dp)
                        ) {

                            Text(
                                user.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(user.role)
                            Text(user.businessName)
                            Text("Status: ${user.status}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminStatCard(
    title: String,
    value: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            Modifier.padding(16.dp)
        ) {

            Text(
                title,
                color = Color.Gray
            )

            Text(
                value,
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF008080)
            )
        }
    }
}
