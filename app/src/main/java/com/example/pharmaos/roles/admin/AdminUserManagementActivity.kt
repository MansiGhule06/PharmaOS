
package com.example.pharmaos.roles.admin

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

class AdminUserManagementActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdminUserManagementScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUserManagementScreen() {
    var selectedUser by remember { mutableStateOf<AdminUser?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Management") }
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
                    "Registered Users",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(AdminRepository.users.toList(), key = { it.id }) { user ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(user.name, style = MaterialTheme.typography.titleMedium)
                        Text("ID: ${user.id}")
                        Text("Email: ${user.email}")
                        Text("Role: ${user.role}")
                        Text("Business: ${user.businessName}")
                        Text("Status: ${user.status}")

                        Spacer(Modifier.height(10.dp))

                        Button(
                            onClick = { selectedUser = user },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB3261E)
                            )
                        ) {
                            Text("Delete User")
                        }
                    }
                }
            }
        }
    }

    selectedUser?.let { user ->
        AlertDialog(
            onDismissRequest = { selectedUser = null },
            title = { Text("Delete User?") },
            text = { Text("Are you sure you want to delete ${user.name}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        AdminRepository.deleteUser(user.id)
                        selectedUser = null
                    }
                ) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedUser = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}
