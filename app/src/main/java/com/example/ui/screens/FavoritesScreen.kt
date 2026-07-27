package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.model.SampleData
import com.example.ui.components.FoodgoBottomNavWithFAB
import com.example.ui.components.ProductCard
import com.example.ui.theme.FoodgoInkDark
import com.example.ui.theme.FoodgoPageBackground

@Composable
fun FavoritesScreen(
    onProductClick: (Product) -> Unit,
    onNavigateToCustomize: () -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var products by remember { mutableStateOf(SampleData.sampleProducts) }
    val favoriteProducts = remember(products) { products.filter { it.isFavorite } }

    Scaffold(
        bottomBar = {
            FoodgoBottomNavWithFAB(
                currentRoute = "favorites",
                onNavigate = onNavigate,
                onFabClick = onNavigateToCustomize
            )
        },
        containerColor = FoodgoPageBackground,
        modifier = modifier.testTag("favorites_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Your Favorites",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoodgoInkDark
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (favoriteProducts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(text = "No favorite items yet!", color = com.example.ui.theme.FoodgoTextBody)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favoriteProducts, key = { it.id }) { product ->
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
        }
    }
}
