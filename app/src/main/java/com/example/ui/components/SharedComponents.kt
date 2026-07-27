package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import com.example.R
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PaymentMethod
import com.example.model.Product
import com.example.ui.theme.*

/**
 * Script Logo Text component for "Foodgo" branding
 */
@Composable
fun FoodgoScriptLogo(
    modifier: Modifier = Modifier,
    color: Color = FoodgoInkDark,
    fontSize: Float = 32f
) {
    Text(
        text = "Foodgo",
        modifier = modifier.testTag("foodgo_script_logo"),
        style = TextStyle(
            fontFamily = FontFamily.Cursive,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp,
            color = color
        )
    )
}

/**
 * 2.2 ProductCard component
 */
@Composable
fun ProductCard(
    product: Product,
    onCardClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFav by remember(product.isFavorite) { mutableStateOf(product.isFavorite) }
    val heartScale by animateFloatAsState(
        targetValue = if (isFav) 1.25f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "heartScale"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp), spotColor = FoodgoCardShadow)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onCardClick() }
            .testTag("product_card_${product.id}"),
        color = FoodgoSurfaceWhite,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Product photo on white container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.fullName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                // Video Badge if available
                if (product.videoResId != null) {
                    Surface(
                        color = FoodgoRedPrimary,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "▶ VIDEO",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Price Tag Overlay
                Surface(
                    color = FoodgoInkDark,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(6.dp)
                ) {
                    Text(
                        text = "$${String.format("%.2f", product.price)}",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Two line name
            Text(
                text = product.nameLine1,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = FoodgoInkDark
            )
            Text(
                text = product.nameLine2,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = FoodgoTextBody
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Rating + Favorite row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = FoodgoStarYellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = product.rating.toString(),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = FoodgoInkDark
                        )
                    )
                }

                IconButton(
                    onClick = {
                        isFav = !isFav
                        onFavoriteToggle()
                    },
                    modifier = Modifier
                        .size(32.dp)
                        .scale(heartScale)
                        .testTag("favorite_button_${product.id}")
                ) {
                    Icon(
                        imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFav) FoodgoRedPrimary else FoodgoTextBody
                    )
                }
            }
        }
    }
}

/**
 * 2.2 CategoryChip component
 */
@Composable
fun CategoryChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) FoodgoRedPrimary else FoodgoChipInactive,
        label = "chipBg"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else FoodgoInkDark,
        label = "chipText"
    )

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .testTag("category_chip_$label"),
        color = bgColor,
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = textColor,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                )
            )
        }
    }
}

/**
 * 2.3 SpicySlider component
 */
@Composable
fun SpicySlider(
    value: Float, // 0.0 to 1.0
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Spicy",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = FoodgoInkDark
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Slider(
            value = value,
            onValueChange = onValueChange,
            colors = SliderDefaults.colors(
                thumbColor = FoodgoRedPrimary,
                activeTrackColor = FoodgoRedPrimary,
                inactiveTrackColor = FoodgoRedPrimary.copy(alpha = 0.2f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("spicy_slider")
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Mild",
                style = TextStyle(fontSize = 11.sp, color = Color(0xFF888888), fontWeight = FontWeight.Medium)
            )
            Text(
                text = "Hot",
                style = TextStyle(fontSize = 11.sp, color = FoodgoRedPrimary, fontWeight = FontWeight.Bold)
            )
        }
    }
}

/**
 * 2.3 PortionStepper component
 */
@Composable
fun PortionStepper(
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Portion",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = FoodgoInkDark
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Minus button
            val isMinusEnabled = quantity > 1
            IconButton(
                onClick = onDecrease,
                enabled = isMinusEnabled,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(if (isMinusEnabled) FoodgoRedPrimary else FoodgoRedPrimary.copy(alpha = 0.5f))
                    .testTag("portion_minus_button")
            ) {
                Text(
                    text = "−",
                    style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }

            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = FoodgoInkDark
                )
            )

            // Plus button
            IconButton(
                onClick = onIncrease,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(FoodgoRedPrimary)
                    .testTag("portion_plus_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase portion",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

/**
 * Sticky Footer Price + Button Component
 */
@Composable
fun PriceButtonRow(
    price: Double,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
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
            // Red Price Pill
            Surface(
                color = FoodgoRedPrimary,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(48.dp)
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$${String.format("%.2f", price)}",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Order Button
            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("primary_order_button"),
                colors = ButtonDefaults.buttonColors(containerColor = FoodgoInkDark),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = buttonText,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.08.sp
                    )
                )
            }
        }
    }
}

/**
 * 2.5 PaymentCard component
 */
@Composable
fun PaymentCard(
    paymentMethod: PaymentMethod,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = paymentMethod.isSelected
    val cardBg by animateColorAsState(
        targetValue = if (isSelected) FoodgoInkDark else FoodgoSurfaceWhite,
        label = "paymentCardBg"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else FoodgoInkDark,
        label = "paymentCardText"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onSelect() }
            .testTag("payment_card_${paymentMethod.id}"),
        color = cardBg,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = if (isSelected) 8.dp else 2.dp,
        border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, FoodgoChipInactive) else null
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Card brand icon container
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = if (paymentMethod.logoType == "mastercard") "💳" else "🌐",
                            fontSize = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = paymentMethod.cardType,
                        style = TextStyle(
                            color = textColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = paymentMethod.cardNumber,
                        style = TextStyle(
                            color = if (isSelected) Color.LightGray else FoodgoTextBody,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            // Radio button indicator
            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.White,
                    unselectedColor = FoodgoTextBody
                )
            )
        }
    }
}

/**
 * 2.8 ChatBubble component
 */
@Composable
fun ChatBubble(
    message: String,
    isUser: Boolean,
    avatarRes: Int = R.drawable.img_user_avatar_1785072958204,
    timestamp: String = "",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            if (!isUser) {
                // Agent avatar
                Surface(
                    shape = CircleShape,
                    color = FoodgoChipInactive,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "🎧", fontSize = 18.sp)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Surface(
                color = if (isUser) FoodgoRedPrimary else FoodgoChipInactive,
                shape = if (isUser) {
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 2.dp)
                } else {
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 2.dp, bottomEnd = 16.dp)
                },
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                    Text(
                        text = message,
                        style = TextStyle(
                            color = if (isUser) Color.White else FoodgoInkDark,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    )
                }
            }

            if (isUser) {
                Spacer(modifier = Modifier.width(8.dp))
                // User photo avatar
                Image(
                    painter = painterResource(id = avatarRes),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        if (timestamp.isNotEmpty()) {
            Text(
                text = timestamp,
                style = TextStyle(color = FoodgoTextBody, fontSize = 11.sp),
                modifier = Modifier.padding(top = 4.dp, start = 44.dp, end = 44.dp)
            )
        }
    }
}

/**
 * 2.2 Bottom Navigation Bar with centered floating FAB
 */
@Composable
fun FoodgoBottomNavWithFAB(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(88.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Red navigation bar background
        Surface(
            color = FoodgoRedPrimary,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left 2 items
                NavItem(
                    iconSelected = Icons.Default.Home,
                    iconUnselected = Icons.Outlined.Home,
                    label = "Home",
                    isSelected = currentRoute == "home",
                    onClick = { onNavigate("home") },
                    testTag = "nav_home"
                )

                NavItem(
                    iconSelected = Icons.Default.Person,
                    iconUnselected = Icons.Outlined.Person,
                    label = "Profile",
                    isSelected = currentRoute == "profile",
                    onClick = { onNavigate("profile") },
                    testTag = "nav_profile"
                )

                Spacer(modifier = Modifier.width(48.dp)) // Center gap for FAB

                // Right 2 items
                NavItem(
                    iconSelected = Icons.Default.Receipt,
                    iconUnselected = Icons.Outlined.Receipt,
                    label = "Orders",
                    isSelected = currentRoute == "order_summary",
                    onClick = { onNavigate("order_summary") },
                    testTag = "nav_orders"
                )

                NavItem(
                    iconSelected = Icons.Default.Favorite,
                    iconUnselected = Icons.Outlined.FavoriteBorder,
                    label = "Favorites",
                    isSelected = currentRoute == "favorites",
                    onClick = { onNavigate("favorites") },
                    testTag = "nav_favorites"
                )
            }
        }

        // Circular Red FAB with + floating centered, overlapping top edge
        Surface(
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopCenter)
                .shadow(elevation = 12.dp, shape = CircleShape)
                .border(3.dp, Color.White, CircleShape)
                .clip(CircleShape)
                .clickable { onFabClick() }
                .testTag("bottom_nav_fab"),
            color = FoodgoRedPrimary,
            shape = CircleShape
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Customize & Order",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    iconSelected: ImageVector,
    iconUnselected: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
            .testTag(testTag)
    ) {
        Icon(
            imageVector = if (isSelected) iconSelected else iconUnselected,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        if (isSelected) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
    }
}
