package com.example.pharmaos.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pharmaos.registration.RegistrationActivity

private val PrimaryTeal = Color(0xFF008080)
private val PrimaryPurple = Color(0xFF800080)
private val AppBackground = Color(0xFFF1F5FF)
private val PrimaryText = Color(0xFF333333)
private val SecondaryText = Color(0xFF999999)

// Login Activity
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedRole = intent.getStringExtra("USER_ROLE")
            ?: "Stockist / Distributor"

        setContent {
            MaterialTheme {
                LoginScreen(
                    selectedRole = selectedRole,
                    onRegisterClick = {
                        val intent = Intent(this, RegistrationActivity::class.java)
                        intent.putExtra("USER_ROLE", selectedRole)
                        startActivity(intent)
                    },
                    onLoginClick = { email, password ->

                        if (selectedRole == "Stockist / Distributor") {
                            val intent = Intent(
                                this,
                                com.example.pharmaos.roles.stockist.StockistDashboardActivity::class.java
                            )
                            startActivity(intent)
                            finish()
                        }

                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    selectedRole: String,
    onRegisterClick: () -> Unit,
    onLoginClick: (String, String) -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // App name
        Text(
            text = "PharmaOS",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryTeal
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Smarter Procurement. Healthier Tomorrow.",
            fontSize = 12.sp,
            color = SecondaryText
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Login card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "Welcome Back!",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Login to your PharmaOS account",
                    fontSize = 14.sp,
                    color = SecondaryText
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Selected role
                Surface(
                    color = PrimaryTeal.copy(alpha = 0.10f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Role: $selectedRole",
                        modifier = Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 10.dp
                        ),
                        color = PrimaryTeal,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                // Email field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email Address") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email"
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password"
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        ) {
                            Icon(
                                imageVector = if (passwordVisible)
                                    Icons.Default.VisibilityOff
                                else
                                    Icons.Default.Visibility,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Forgot password
                TextButton(
                    onClick = {
                        // Forgot password screen will be implemented later.
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = "Forgot Password?",
                        color = PrimaryPurple,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Login button
                Button(
                    onClick = {
                        onLoginClick(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryTeal
                    )
                ) {
                    Text(
                        text = "Login",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Registration navigation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account?",
                        color = SecondaryText,
                        fontSize = 13.sp
                    )

                    TextButton(onClick = onRegisterClick) {
                        Text(
                            text = "Register",
                            color = PrimaryPurple,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
