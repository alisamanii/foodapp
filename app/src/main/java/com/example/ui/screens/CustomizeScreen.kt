package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SampleData
import com.example.model.SideOption
import com.example.model.Topping
import com.example.ui.components.ExplodedBurgerStack
import com.example.ui.components.PortionStepper
import com.example.ui.components.SpicySlider
import com.example.ui.theme.*

@Composable
fun CustomizeScreen(
    onBackClick: () -> Unit,
    onOrderNowClick: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    var spicyValue by remember { mutableFloatStateOf(0.5f) }
    var portionCount by remember { mutableIntStateOf(2) }

    var toppingsState by remember { mutableStateOf(SampleData.defaultToppings) }
    var sideOptionsState by remember { mutableStateOf(SampleData.defaultSideOptions) }

    val baseBurgerPrice = 8.24
    val toppingsPrice = toppingsState.filter { it.isSelected }.sumOf { it.price }
    val sidesPrice = sideOptionsState.filter { it.isSelected }.sumOf { it.price }
    val totalPrice = (baseBurgerPrice + toppingsPrice + sidesPrice) * portionCount

    val selectedToppingNames = remember(toppingsState) {
        toppingsState.filter { it.isSelected }.map { it.name }.toSet()
    }

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 16.dp, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(FoodgoSurfaceWhite),
                color = FoodgoSurfaceWhite,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Total", style = TextStyle(color = FoodgoTextBody, fontSize = 12.sp))
                        Text(
                            text = "$${String.format("%.2f", totalPrice)}",
                            style = TextStyle(color = FoodgoInkDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        )
                    }

                    Button(
                        onClick = { onOrderNowClick(totalPrice) },
                        modifier = Modifier
                            .height(48.dp)
                            .testTag("customize_order_now_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = FoodgoRedPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "ORDER NOW",
                            style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        )
                    }
                }
            }
        },
        containerColor = FoodgoPageBackground,
        modifier = modifier.testTag("customize_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }

            // Split Layout Header: Left Exploded Stack, Right Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Column: Exploded Burger Stack
                Box(
                    modifier = Modifier.weight(1.1f),
                    contentAlignment = Alignment.Center
                ) {
                    ExplodedBurgerStack(selectedToppings = selectedToppingNames)
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Right Column: Title + Controls
                Column(
                    modifier = Modifier.weight(1.2f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Customize Your Burger to Your Tastes. Ultimate Experience",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            color = FoodgoInkDark
                        )
                    )

                    SpicySlider(
                        value = spicyValue,
                        onValueChange = { spicyValue = it }
                    )

                    PortionStepper(
                        quantity = portionCount,
                        onDecrease = { if (portionCount > 1) portionCount-- },
                        onIncrease = { portionCount++ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Toppings Section
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "Toppings",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = FoodgoInkDark
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    toppingsState.forEach { topping ->
                        Option72Card(
                            name = topping.name,
                            iconEmoji = topping.iconEmoji,
                            isSelected = topping.isSelected,
                            onToggle = {
                                toppingsState = toppingsState.map { t ->
                                    if (t.id == topping.id) t.copy(isSelected = !t.isSelected) else t
                                }
                            },
                            testTag = "topping_${topping.id}"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Side Options Section
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "Side options",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = FoodgoInkDark
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    sideOptionsState.forEach { side ->
                        Option72Card(
                            name = side.name,
                            iconEmoji = side.iconEmoji,
                            isSelected = side.isSelected,
                            onToggle = {
                                sideOptionsState = sideOptionsState.map { s ->
                                    if (s.id == side.id) s.copy(isSelected = !s.isSelected) else s
                                }
                            },
                            testTag = "side_${side.id}"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

/**
 * 72x72 rounded option card with badge
 */
@Composable
private fun Option72Card(
    name: String,
    iconEmoji: String,
    isSelected: Boolean,
    onToggle: () -> Unit,
    testTag: String
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) FoodgoRedPrimary else Color.Transparent,
        label = "optionBorder"
    )

    Surface(
        modifier = Modifier
            .size(72.dp)
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { onToggle() }
            .testTag(testTag),
        color = FoodgoSurfaceWhite,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Icon Emoji
            Text(
                text = iconEmoji,
                fontSize = 28.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            // Label chip bottom-left
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(4.dp),
                color = FoodgoChipInactive,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoodgoInkDark,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }

            // Red badge bottom-right (+ or checkmark)
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .size(18.dp),
                color = FoodgoRedPrimary,
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isSelected) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = if (isSelected) "Selected" else "Add",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}
