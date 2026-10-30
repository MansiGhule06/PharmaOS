
package com.example.pharmaos.registration

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimaryTeal = Color(0xFF008080)
private val PrimaryPurple = Color(0xFF800080)
private val AppBackground = Color(0xFFF1F5FF)
private val PrimaryText = Color(0xFF333333)
private val SecondaryText = Color(0xFF999999)

class RegistrationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedRole = intent.getStringExtra("USER_ROLE")
            ?: "Stockist / Distributor"

        setContent {
            MaterialTheme {
                RegistrationScreen(selectedRole = selectedRole)
            }
        }
    }
}

@Composable
fun RegistrationScreen(selectedRole: String) {

    var currentStep by remember { mutableIntStateOf(1) }
    var submitted by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Step 1: Basic information
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Step 2: Business information
    var businessName by remember { mutableStateOf("") }
    var businessAddress by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }

    // Step 3: Verification information
    var gstin by remember { mutableStateOf("") }
    var licenceNumber by remember { mutableStateOf("") }
    var selectedDocument by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // Opens PDF or image picker
    val documentPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        selectedDocument = uri
    }

    val documentLabel = when (selectedRole) {
        "Manufacturer" -> "Manufacturing Licence"
        "Pharmaceutical Company / Brand" -> "Applicable Drug Licence / Authorization"
        else -> "Wholesale Drug Licence"
    }

    val needsLicence = selectedRole != "Administrator"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        if (submitted) {

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Registration Submitted!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryTeal
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your PharmaOS registration has been submitted for administrator review.",
                color = PrimaryText,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Account Status: Verification Pending",
                color = PrimaryPurple,
                fontWeight = FontWeight.Bold
            )

            return@Column
        }

        Text(
            text = "Create Your Account",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Register as $selectedRole",
            color = SecondaryText,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Progress indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .background(
                            if (index < currentStep) PrimaryTeal
                            else Color.LightGray,
                            RoundedCornerShape(10.dp)
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Step $currentStep of 4",
            color = PrimaryPurple,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(25.dp))

        when (currentStep) {

            // STEP 1: BASIC INFORMATION
            1 -> {

                StepHeading(
                    "Basic Information",
                    "Enter your personal and login details."
                )

                RegistrationField(
                    "Full Name", fullName, { fullName = it }
                )

                RegistrationField(
                    "Email Address", email, { email = it }
                )

                RegistrationField(
                    "Mobile Number", mobile, { mobile = it }
                )

                RegistrationField(
                    "Password", password, { password = it }
                )

                RegistrationField(
                    "Confirm Password",
                    confirmPassword,
                    { confirmPassword = it }
                )
            }

            // STEP 2: BUSINESS INFORMATION
            2 -> {

                StepHeading(
                    "Business Information",
                    "Enter your company or business details."
                )

                RegistrationField(
                    "Business / Company Name",
                    businessName,
                    { businessName = it }
                )

                RegistrationField(
                    "Business Address",
                    businessAddress,
                    { businessAddress = it }
                )

                RegistrationField(
                    "City", city, { city = it }
                )

                RegistrationField(
                    "State", state, { state = it }
                )

                RegistrationField(
                    "PIN Code", pincode, { pincode = it }
                )
            }

            // STEP 3: VERIFICATION
            3 -> {

                StepHeading(
                    "Business Verification",
                    "Provide your business registration information."
                )

                RegistrationField(
                    "GSTIN",
                    gstin,
                    { gstin = it }
                )

                if (needsLicence) {

                    RegistrationField(
                        "Licence Number",
                        licenceNumber,
                        { licenceNumber = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Upload $documentLabel",
                        color = PrimaryText,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = {
                            documentPicker.launch(
                                arrayOf("application/pdf", "image/*")
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Choose PDF or Image")
                    }

                    selectedDocument?.let {
                        Text(
                            text = "Document selected successfully",
                            color = PrimaryTeal,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Your submitted information and documents will be reviewed by the PharmaOS administrator.",
                    color = SecondaryText,
                    fontSize = 13.sp
                )
            }

            // STEP 4: REVIEW AND SUBMIT
            4 -> {

                StepHeading(
                    "Review Your Details",
                    "Please check your information before submitting."
                )

                ReviewItem("Selected Role", selectedRole)
                ReviewItem("Full Name", fullName)
                ReviewItem("Email", email)
                ReviewItem("Mobile", mobile)
                ReviewItem("Business Name", businessName)
                ReviewItem("Address", businessAddress)
                ReviewItem("City", city)
                ReviewItem("State", state)
                ReviewItem("PIN Code", pincode)
                ReviewItem("GSTIN", gstin)

                if (needsLicence) {
                    ReviewItem("Licence Number", licenceNumber)
                    ReviewItem(
                        "Uploaded Document",
                        if (selectedDocument != null) "Selected"
                        else "Not uploaded"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "By submitting, you confirm that the information provided is correct.",
                    color = SecondaryText,
                    fontSize = 13.sp
                )
            }
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = errorMessage,
                color = Color(0xFFB91C1C),
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // NEXT / SUBMIT BUTTON
        Button(
            onClick = {

                errorMessage = ""

                when (currentStep) {

                    1 -> {
                        when {
                            fullName.isBlank() ||
                                    email.isBlank() ||
                                    mobile.isBlank() ||
                                    password.isBlank() ||
                                    confirmPassword.isBlank() -> {
                                errorMessage = "Please fill in all fields."
                            }

                            !android.util.Patterns.EMAIL_ADDRESS
                                .matcher(email).matches() -> {
                                errorMessage = "Enter a valid email address."
                            }

                            mobile.length != 10 ||
                                    !mobile.all { it.isDigit() } -> {
                                errorMessage = "Enter a valid 10-digit mobile number."
                            }

                            password.length < 8 -> {
                                errorMessage = "Password must contain at least 8 characters."
                            }

                            password != confirmPassword -> {
                                errorMessage = "Passwords do not match."
                            }

                            else -> currentStep = 2
                        }
                    }

                    2 -> {
                        if (
                            businessName.isBlank() ||
                            businessAddress.isBlank() ||
                            city.isBlank() ||
                            state.isBlank() ||
                            pincode.isBlank()
                        ) {
                            errorMessage = "Please complete your business details."
                        } else {
                            currentStep = 3
                        }
                    }

                    3 -> {
                        if (gstin.isBlank()) {
                            errorMessage = "Please enter your GSTIN."
                        } else if (
                            needsLicence && licenceNumber.isBlank()
                        ) {
                            errorMessage = "Please enter your licence number."
                        } else if (
                            needsLicence && selectedDocument == null
                        ) {
                            errorMessage = "Please upload the required licence document."
                        } else {
                            currentStep = 4
                        }
                    }

                    4 -> {
                        submitted = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryTeal
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (currentStep == 4)
                    "Submit Registration"
                else
                    "Continue",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (currentStep > 1) {

            TextButton(
                onClick = {
                    currentStep--
                    errorMessage = ""
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Go Back",
                    color = PrimaryPurple
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun StepHeading(title: String, description: String) {

    Text(
        text = title,
        fontSize = 21.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryText
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = description,
        color = SecondaryText,
        fontSize = 13.sp
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun RegistrationField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
    ) {

        Text(
            text = label,
            color = PrimaryText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryTeal,
                unfocusedBorderColor = Color.LightGray
            )
        )
    }
}

@Composable
fun ReviewItem(label: String, value: String) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
    ) {

        Text(
            text = label,
            color = SecondaryText,
            fontSize = 12.sp
        )

        Text(
            text = value.ifBlank { "Not provided" },
            color = PrimaryText,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )

        HorizontalDivider(
            modifier = Modifier.padding(top = 7.dp),
            color = Color.LightGray
        )
    }
}
