package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.R
import com.example.ui.components.FoodgoScriptLogo
import com.example.ui.theme.FoodgoRedGradientEnd
import com.example.ui.theme.FoodgoRedPrimary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 3 seconds initial loading time requirement
    LaunchedEffect(Unit) {
        delay(3000)
        onNavigateToHome()
    }

    // Load Lottie composition from raw resource loading.json
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val lottieProgress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )

    val infiniteTransition = rememberInfiniteTransition(label = "splashPulse")
    val logoScale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logoPulse"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(FoodgoRedPrimary, FoodgoRedGradientEnd)
                )
            )
            .clickable { onNavigateToHome() }
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Foodgo Script Logo with pulsing animation
            Box(
                modifier = Modifier
                    .scale(logoScale)
                    .padding(bottom = 16.dp)
            ) {
                FoodgoScriptLogo(
                    color = Color.White,
                    fontSize = 58f
                )
            }

            Text(
                text = "Delicious Fast Food at Your Doorstep",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Lottie Animation view (size 220.dp)
            if (composition != null) {
                LottieAnimation(
                    composition = composition,
                    progress = { lottieProgress },
                    modifier = Modifier
                        .size(200.dp)
                        .testTag("lottie_loading_animation")
                )
            } else {
                // Fallback circular indicator while lottie loads
                androidx.compose.material3.CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Loading fast food menu...",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
        }

        // Bottom Decorative Burger image
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-30).dp, y = 30.dp)
                .size(220.dp)
                .alpha(0.85f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_burger_cheeseburger_1785072915476),
                contentDescription = "Splash Burger",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

