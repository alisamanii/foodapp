package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Product
import com.example.model.SampleData
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    onProductClick: (Product) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToCustomize: () -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var products by remember { mutableStateOf(SampleData.sampleProducts) }
    var showFilterSheet by remember { mutableStateOf(false) }

    val filteredProducts = remember(searchQuery, selectedCategory, products) {
        products.filter { product ->
            val matchesCategory = (selectedCategory == "All" || product.category == selectedCategory)
            val matchesSearch = product.fullName.contains(searchQuery, ignoreCase = true) ||
                    product.description.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    Scaffold(
        bottomBar = {
            FoodgoBottomNavWithFAB(
                currentRoute = "home",
                onNavigate = onNavigate,
                onFabClick = onNavigateToCustomize
            )
        },
        containerColor = FoodgoPageBackground,
        modifier = modifier.testTag("home_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    FoodgoScriptLogo(fontSize = 32f)
                    Text(
                        text = "Order your favourite food!",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = FoodgoTextBody,
                            fontSize = 13.sp
                        )
                    )
                }

                // Profile Avatar (tap -> Profile)
                Surface(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickable { onNavigateToProfile() }
                        .testTag("home_avatar_button"),
                    shape = CircleShape
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_user_avatar_1785072958204),
                        contentDescription = "Profile Avatar",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Animated Promo Banner Carousel
            PromoBannerCarousel()

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Search Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Search Input Field
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = FoodgoSurfaceWhite,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = FoodgoTextBody,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search", color = FoodgoTextBody, fontSize = 14.sp) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("search_input")
                        )
                    }
                }

                // Red Filter Button (48x48 rounded square with sliders icon)
                Surface(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { showFilterSheet = true }
                        .testTag("filter_button"),
                    color = FoodgoRedPrimary,
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Filter",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Horizontally Scrollable Category Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SampleData.sampleCategories.forEach { category ->
                    CategoryChip(
                        label = category,
                        isSelected = selectedCategory == category,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Product Grid (2 columns, 12dp gutter)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTag("product_grid")
            ) {
                items(filteredProducts, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        onCardClick = { onProductClick(product) },
                        onFavoriteToggle = {
                            products = products.map { p ->
                                if (p.id == product.id) p.copy(isFavorite = !p.isFavorite) else p
                            }
                        }
                    )
                }
            }
        }

        if (showFilterSheet) {
            FilterBottomSheet(
                onDismiss = { showFilterSheet = false },
                onApply = { cat, spicy, price ->
                    selectedCategory = cat
                }
            )
        }
    }
}

@Composable
fun PromoBannerCarousel() {
    var promoIndex by remember { mutableIntStateOf(0) }
    val promos = listOf(
        Triple("🔥 HOT DEAL", "50% OFF All Burgers", "Use code: FOODGO50"),
        Triple("🛵 FREE DELIVERY", "Zero Delivery Fee", "On orders above $15.00"),
        Triple("🍕 PIZZA WEEK", "Buy 1 Get 1 Free", "Valid for all Large Pizzas"),
        Triple("🍗 CRISPY CHICKEN", "Free Dipping Sauce", "With any Fried Chicken Bucket")
    )

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(3500)
            promoIndex = (promoIndex + 1) % promos.size
        }
    }

    val currentPromo = promos[promoIndex]

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
            .testTag("promo_banner_carousel")
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(FoodgoRedPrimary, FoodgoRedGradientEnd)
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterStart),
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    color = Color.White.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = currentPromo.first,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = currentPromo.second,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = currentPromo.third,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Dot indicators
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                promos.indices.forEach { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == promoIndex) 16.dp else 6.dp, 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == promoIndex) Color.White else Color.White.copy(alpha = 0.4f)
                            )
                    )
                }
            }
        }
    }
}

