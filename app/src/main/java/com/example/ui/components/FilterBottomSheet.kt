package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onDismiss: () -> Unit,
    onApply: (selectedCategory: String, spicyLevel: Float, maxPrice: Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var spicyLevel by remember { mutableStateOf(0.5f) }
    var maxPrice by remember { mutableStateOf(30f) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = FoodgoSurfaceWhite,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Filter Options",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = FoodgoInkDark
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Category Filter
            Text(
                text = "Category",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = FoodgoInkDark
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("All", "Combos", "Sliders", "Classic").forEach { cat ->
                    CategoryChip(
                        label = cat,
                        isSelected = selectedCategory == cat,
                        onClick = { selectedCategory = cat }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Spicy Level Slider
            SpicySlider(
                value = spicyLevel,
                onValueChange = { spicyLevel = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Price Range
            Text(
                text = "Max Price ($${maxPrice.toInt()})",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = FoodgoInkDark
                )
            )
            Slider(
                value = maxPrice,
                onValueChange = { maxPrice = it },
                valueRange = 5f..50f,
                colors = SliderDefaults.colors(
                    thumbColor = FoodgoRedPrimary,
                    activeTrackColor = FoodgoRedPrimary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Apply Button
            Button(
                onClick = {
                    onApply(selectedCategory, spicyLevel, maxPrice)
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("apply_filter_button"),
                colors = ButtonDefaults.buttonColors(containerColor = FoodgoRedPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "APPLY FILTERS",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.08.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
