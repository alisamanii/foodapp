package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.model.SampleData
import com.example.ui.components.PortionStepper
import com.example.ui.components.PriceButtonRow
import com.example.ui.components.SpicySlider
import com.example.ui.theme.*

@Composable
fun ProductDetailScreen(
    productId: String?,
    onBackClick: () -> Unit,
    onOrderNowClick: (Product, Int, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val initialProduct = SampleData.sampleProducts.find { it.id == productId }
        ?: SampleData.sampleProducts.first()

    var currentProductIndex by remember {
        mutableIntStateOf(SampleData.sampleProducts.indexOf(initialProduct).coerceAtLeast(0))
    }
    val product = SampleData.sampleProducts[currentProductIndex]

    var spicyValue by remember { mutableFloatStateOf(0.4f) }
    var portionCount by remember { mutableIntStateOf(1) }

    val calculatedPrice = product.price * portionCount

    Scaffold(
        bottomBar = {
            PriceButtonRow(
                price = calculatedPrice,
                buttonText = "ORDER NOW",
                onButtonClick = { onOrderNowClick(product, portionCount, spicyValue) }
            )
        },
        containerColor = FoodgoSurfaceWhite,
        modifier = modifier.testTag("product_detail_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // 1. Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("detail_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = FoodgoInkDark
                    )
                }

                IconButton(
                    onClick = { /* Search */ },
                    modifier = Modifier.testTag("detail_search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = FoodgoInkDark
                    )
                }
            }

            // 2. Hero Media Section (Photo or Video Preview)
            var showVideo by remember(product.id) { mutableStateOf(product.videoResId != null) }

            if (product.videoResId != null) {
                // Media Switcher Row (Photo / Video)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FilterChip(
                        selected = !showVideo,
                        onClick = { showVideo = false },
                        label = { Text("Photo") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Photo",
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = FoodgoInkDark,
                            selectedLabelColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    FilterChip(
                        selected = showVideo,
                        onClick = { showVideo = true },
                        label = { Text("Video Preview") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = FoodgoRedPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                if (showVideo && product.videoResId != null) {
                    com.example.ui.components.FastFoodVideoPlayer(
                        videoResId = product.videoResId!!,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.fullName,
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow(
                                elevation = 16.dp,
                                shape = CircleShape,
                                spotColor = Color.Black.copy(alpha = 0.15f)
                            ),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                // 3. Title 20px semibold
                Text(
                    text = product.fullName,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FoodgoInkDark
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 4. Meta row: rating, prep time
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = FoodgoStarYellow,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${product.rating}",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FoodgoInkDark)
                    )
                    Text(
                        text = " — ${product.prepTime}",
                        style = TextStyle(fontSize = 13.sp, color = FoodgoTextBody)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 5. Description
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = FoodgoTextBody
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 6. Spicy & Portion Controls Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // Spicy Slider
                    Box(modifier = Modifier.weight(1.2f)) {
                        SpicySlider(
                            value = spicyValue,
                            onValueChange = { spicyValue = it }
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    // Portion Stepper
                    Box(modifier = Modifier.weight(1f)) {
                        PortionStepper(
                            quantity = portionCount,
                            onDecrease = { if (portionCount > 1) portionCount-- },
                            onIncrease = { portionCount++ }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}
