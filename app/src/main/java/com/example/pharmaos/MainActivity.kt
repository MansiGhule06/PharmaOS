
package com.example.pharmaos

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pharmaos.roles.RoleSelectionActivity
import kotlinx.coroutines.delay

// PharmaOS color palette
private val PrimaryTeal = Color(0xFF008080)
private val PrimaryPurple = Color(0xFF800080)
private val PrimaryText = Color(0xFF333333)
private val SecondaryText = Color(0xFF999999)
private val AppBackground = Color(0xFFF1F5FF)
private val CardWhite = Color(0xFFFFFFFF)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                PharmaOSApp()
            }
        }
    }
}

@Composable
fun PharmaOSApp() {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(2500)

        context.startActivity(
            Intent(context, RoleSelectionActivity::class.java)
        )

        // Close the splash Activity so Back doesn't return to it.
        (context as? ComponentActivity)?.finish()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBackground
    ) {
        SplashScreen()
    }
}

@Composable
fun SplashScreen() {

    val transition = rememberInfiniteTransition(label = "splash")

    // Logo breathing animation
    val logoScale = transition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1400,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logoScale"
    ).value

    // Loading indicator pulse animation
    val pulseAlpha = transition.animateFloat(
        initialValue = 0.45f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    ).value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {

        // Decorative background circles
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCircle(
                color = PrimaryTeal.copy(alpha = 0.06f),
                radius = size.width * 0.42f,
                center = androidx.compose.ui.geometry.Offset(
                    x = size.width * 0.95f,
                    y = size.height * 0.12f
                )
            )

            drawCircle(
                color = PrimaryPurple.copy(alpha = 0.05f),
                radius = size.width * 0.35f,
                center = androidx.compose.ui.geometry.Offset(
                    x = size.width * 0.02f,
                    y = size.height * 0.87f
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .graphicsLayer {
                    alpha = 1f
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // PharmaOS logo
            Box(
                modifier = Modifier
                    .size(132.dp)
                    .scale(logoScale)
                    .background(
                        color = CardWhite,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.pharmaos_logo
                    ),
                    contentDescription = "PharmaOS logo",
                    modifier = Modifier
                        .size(108.dp)
                        .padding(4.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Application name
            Text(
                text = "PharmaOS",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryText,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Slogan
            Text(
                text = "Smarter Procurement.",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryTeal,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Healthier Tomorrow.",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryPurple,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(42.dp))

            // Animated loading indicator
            CircularProgressIndicator(
                modifier = Modifier
                    .size(34.dp)
                    .alpha(pulseAlpha),
                color = PrimaryTeal,
                trackColor = PrimaryTeal.copy(alpha = 0.15f),
                strokeWidth = 3.dp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Connecting the pharmaceutical supply chain",
                fontSize = 13.sp,
                color = SecondaryText,
                textAlign = TextAlign.Center,
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(56.dp))

            // Brand accent
            Box(
                modifier = Modifier
                    .size(width = 52.dp, height = 4.dp)
                    .background(
                        color = PrimaryTeal,
                        shape = CircleShape
                    )
            )
        }

        // Bottom branding
        Text(
            text = "SMARTER HEALTHCARE. STRONGER CONNECTIONS.",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 28.dp
                ),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = SecondaryText,
            letterSpacing = 0.8.sp,
            textAlign = TextAlign.Center
        )
    }
}
