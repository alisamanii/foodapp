package com.example.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.FoodgoRedPrimary
import com.example.ui.theme.FoodgoTextBody
import com.example.ui.theme.FoodgoSurfaceWhite

@Composable
fun SuccessDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var checkAnim by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        checkAnim = true
    }

    val scale by animateFloatAsState(
        targetValue = if (checkAnim) 1.0f else 0.4f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "checkScale"
    )

    Dialog(
        onDismissRequest = { /* Non-dismissible by outside tap as specified */ },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(24.dp))
                .testTag("success_dialog"),
            color = FoodgoSurfaceWhite,
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Red circle with white checkmark
                Surface(
                    modifier = Modifier
                        .size(72.dp)
                        .scale(scale)
                        .clip(CircleShape),
                    color = FoodgoRedPrimary,
                    shape = CircleShape
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Success Check",
                            tint = Color.White,
                            modifier = Modifier.size(38.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Success !",
                    style = TextStyle(
                        color = FoodgoRedPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Your payment was successful.\nA receipt for this purchase has\nbeen sent to your email.",
                    style = TextStyle(
                        color = FoodgoTextBody,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("success_go_back_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = FoodgoRedPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Go Back",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
