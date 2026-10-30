package com.example.pharmaos.roles

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pharmaos.auth.LoginActivity

private val Teal = Color(0xFF008080)
private val Purple = Color(0xFF800080)
private val AppBackground = Color(0xFFF1F5FF)
private val PrimaryText = Color(0xFF333333)
private val SecondaryText = Color(0xFF999999)

class RoleSelectionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                RoleSelectionScreen { selectedRole ->
                    val intent = Intent(
                        this,
                        LoginActivity::class.java
                    )
                    intent.putExtra("USER_ROLE", selectedRole)
                    startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun RoleSelectionScreen(
    onRoleSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(36.dp))

        Text(
            text = "Welcome to PharmaOS",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Choose your role to get started",
            fontSize = 15.sp,
            color = SecondaryText
        )

        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            RoleCard(
                "Manufacturer", "🏭", Teal,
                Modifier.weight(1f), onRoleSelected
            )
            RoleCard(
                "Pharmaceutical Company / Brand", "💊", Purple,
                Modifier.weight(1f), onRoleSelected
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            RoleCard(
                "Stockist / Distributor", "🚚", Purple,
                Modifier.weight(1f), onRoleSelected
            )
            RoleCard(
                "Administrator", "🛡️", Teal,
                Modifier.weight(1f), onRoleSelected
            )
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = "Smarter Procurement. Healthier Tomorrow.",
            fontSize = 12.sp,
            color = SecondaryText,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RoleCard(
    title: String,
    emoji: String,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .height(190.dp)
            .clickable { onClick(title) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .background(
                        accent.copy(alpha = 0.10f),
                        RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 34.sp)
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText,
                textAlign = TextAlign.Center
            )
        }
    }
}