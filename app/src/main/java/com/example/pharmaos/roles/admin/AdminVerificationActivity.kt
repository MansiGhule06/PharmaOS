
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

class AdminVerificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdminVerificationScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminVerificationScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verification Requests") }
            )
        },
        containerColor = Color(0xFFF1F5FF)
    ) { padding ->

        val pendingUsers = AdminRepository.users.filter {
            it.status == "Pending"
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Pending Requests: ${pendingUsers.size}",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            if (pendingUsers.isEmpty()) {
                item {
                    Text("No pending verification requests.")
                }
            }

            items(pendingUsers, key = { it.id }) { user ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(user.name, style = MaterialTheme.typography.titleMedium)
                        Text("Role: ${user.role}")
                        Text("Business: ${user.businessName}")
                        Text("Email: ${user.email}")

                        Spacer(Modifier.height(12.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    AdminRepository.approveUser(user.id)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF008080)
                                )
                            ) {
                                Text("Approve")
                            }

                            OutlinedButton(
                                onClick = {
                                    AdminRepository.rejectUser(user.id)
                                }
                            ) {
                                Text("Reject")
                            }
                        }
                    }
                }
            }
        }
    }
}
