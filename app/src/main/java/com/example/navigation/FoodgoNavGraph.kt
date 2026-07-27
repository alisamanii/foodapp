package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.*

@Composable
fun FoodgoNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onProductClick = { product ->
                    navController.navigate("product_detail/${product.id}")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                },
                onNavigateToCustomize = {
                    navController.navigate("customize")
                },
                onNavigate = { route ->
                    if (route != "home") {
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }

        composable(
            route = "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() },
                onOrderNowClick = { _, _, _ ->
                    navController.navigate("order_summary")
                }
            )
        }

        composable("customize") {
            CustomizeScreen(
                onBackClick = { navController.popBackStack() },
                onOrderNowClick = { _ ->
                    navController.navigate("order_summary")
                }
            )
        }

        composable("order_summary") {
            OrderSummaryScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToCustomize = { navController.navigate("customize") },
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToChat = { navController.navigate("support_chat") },
                onNavigateToCustomize = { navController.navigate("customize") },
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("support_chat") {
            SupportChatScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("favorites") {
            FavoritesScreen(
                onProductClick = { product ->
                    navController.navigate("product_detail/${product.id}")
                },
                onNavigateToCustomize = { navController.navigate("customize") },
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
