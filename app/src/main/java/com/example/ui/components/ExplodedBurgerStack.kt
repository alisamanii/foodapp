package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FoodgoRedPrimary

/**
 * Custom Exploded Burger Stack showing separated layers with gaps
 */
@Composable
fun ExplodedBurgerStack(
    selectedToppings: Set<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // 1. Top Bun Layer
        BurgerLayerItem(
            emoji = "🍔",
            label = "Top Bun",
            color = Color(0xFFE5A65D),
            height = 24.dp,
            widthPercent = 0.9f
        )

        // 2. Sauce Layer
        BurgerLayerItem(
            emoji = "🥫",
            label = "Spicy Sauce",
            color = FoodgoRedPrimary,
            height = 8.dp,
            widthPercent = 0.8f
        )

        // 3. Tomato Layer (Conditional)
        AnimatedVisibility(
            visible = selectedToppings.contains("Tomato") || selectedToppings.contains("t1"),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            BurgerLayerItem(
                emoji = "🍅",
                label = "Fresh Tomato",
                color = Color(0xFFE53935),
                height = 10.dp,
                widthPercent = 0.85f
            )
        }

        // 4. Onions Layer (Conditional)
        AnimatedVisibility(
            visible = selectedToppings.contains("Onions") || selectedToppings.contains("t2"),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            BurgerLayerItem(
                emoji = "🧅",
                label = "Crisp Onions",
                color = Color(0xFFBA68C8),
                height = 8.dp,
                widthPercent = 0.8f
            )
        }

        // 5. Cheese Layer (Conditional)
        AnimatedVisibility(
            visible = selectedToppings.contains("Cheese") || selectedToppings.contains("t5"),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            BurgerLayerItem(
                emoji = "🧀",
                label = "Melted Cheese",
                color = Color(0xFFFFB300),
                height = 12.dp,
                widthPercent = 0.88f
            )
        }

        // 6. Beef Patty / Chicken Patty
        BurgerLayerItem(
            emoji = "🥩",
            label = "Juicy Patty",
            color = Color(0xFF5D4037),
            height = 22.dp,
            widthPercent = 0.85f
        )

        // 7. Bacons Layer (Conditional)
        AnimatedVisibility(
            visible = selectedToppings.contains("Bacons") || selectedToppings.contains("t4"),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            BurgerLayerItem(
                emoji = "🥓",
                label = "Crispy Bacon",
                color = Color(0xFF8D6E63),
                height = 8.dp,
                widthPercent = 0.82f
            )
        }

        // 8. Lettuce / Pickles Layer (Conditional)
        AnimatedVisibility(
            visible = selectedToppings.contains("Pickles") || selectedToppings.contains("t3"),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            BurgerLayerItem(
                emoji = "🥒",
                label = "Tangy Pickles",
                color = Color(0xFF43A047),
                height = 10.dp,
                widthPercent = 0.83f
            )
        }

        BurgerLayerItem(
            emoji = "🥬",
            label = "Crisp Lettuce",
            color = Color(0xFF66BB6A),
            height = 10.dp,
            widthPercent = 0.88f
        )

        // 9. Base Bun Layer
        BurgerLayerItem(
            emoji = "🍞",
            label = "Base Bun",
            color = Color(0xFFD7CCC8),
            height = 18.dp,
            widthPercent = 0.9f
        )
    }
}

@Composable
private fun BurgerLayerItem(
    emoji: String,
    label: String,
    color: Color,
    height: androidx.compose.ui.unit.Dp,
    widthPercent: Float
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(widthPercent)
            .height(height)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp)),
        color = color,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$emoji $label",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
