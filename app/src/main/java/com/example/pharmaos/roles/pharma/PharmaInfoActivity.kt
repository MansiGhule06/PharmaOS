
package com.example.pharmaos.roles.pharma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PharmaInfoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pageTitle = intent.getStringExtra("PAGE_TITLE")
            ?: "My Profile"

        setContent {
            MaterialTheme {
                PharmaInfoScreen(
                    pageTitle = pageTitle,
                    onBack = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PharmaInfoScreen(
    pageTitle: String,
    onBack: () -> Unit
) {

    val teal = Color(0xFF008080)

    val details = when (pageTitle) {

        "My Profile" -> listOf(
            "Company Name" to "Your Company Name",
            "Email Address" to "company@example.com",
            "Contact Number" to "Not added",
            "Account Role" to "Pharmaceutical Company / Brand"
        )

        "Business Information" -> listOf(
            "Business Type" to "Pharmaceutical Company",
            "Business Address" to "Not added",
            "GST Number" to "Not added",
            "Drug Licence Number" to "Not added"
        )

        "Documents" -> listOf(
            "GST Certificate" to "Pending Upload",
            "Drug Licence" to "Pending Upload",
            "Company Registration Certificate" to "Pending Upload",
            "Other Documents" to "Pending Upload"
        )

        "Verification Status" -> listOf(
            "Account Status" to "Pending Verification",
            "Company Details" to "Awaiting Review",
            "Documents" to "Pending Submission",
            "Verification Result" to "Not Verified"
        )

        "Notifications" -> listOf(
            "Order Updates" to "Receive updates about stockist orders",
            "Batch Alerts" to "Receive batch expiry and recall alerts",
            "Verification Updates" to "Receive account verification updates",
            "System Notifications" to "Receive important PharmaOS announcements"
        )

        "Help & Support" -> listOf(
            "Support" to "Contact the PharmaOS support team",
            "Account Help" to "Get help with your company account",
            "Order Help" to "Get help with order management",
            "Batch Help" to "Get help with batch tracking"
        )

        else -> listOf(
            "Information" to "Details will be available here."
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pageTitle) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(
                            "Back",
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = teal,
                    titleContentColor = Color.White
                )
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
                    text = pageTitle,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Text(
                    text = "Manage your pharmaceutical company information.",
                    color = Color.Gray
                )
            }

            items(details.size) { index ->

                val item = details[index]

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
                            text = item.first,
                            fontWeight = FontWeight.Bold,
                            color = teal,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = item.second,
                            color = Color.DarkGray
                        )
                    }
                }
            }

            if (
                pageTitle == "My Profile" ||
                pageTitle == "Business Information"
            ) {
                item {
                    Text(
                        text = "Profile editing will be connected with the backend later.",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            if (pageTitle == "Documents") {
                item {
                    Text(
                        text = "Document upload will be connected in a later backend phase.",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
