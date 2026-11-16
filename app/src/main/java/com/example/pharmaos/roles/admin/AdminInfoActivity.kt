
package com.example.pharmaos.roles.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class AdminInfoActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pageTitle = intent.getStringExtra("PAGE_TITLE") ?: "Admin"

        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(pageTitle) }
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(20.dp)
                ) {
                    Text(
                        pageTitle,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "This section is available for administrator access. " +
                                "Detailed management functionality will be connected " +
                                "with the backend."
                    )
                }
            }
        }
    }
}
