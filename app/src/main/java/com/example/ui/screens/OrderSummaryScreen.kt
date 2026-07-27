package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import com.example.model.PaymentMethod
import com.example.model.SampleData
import com.example.ui.components.FoodgoBottomNavWithFAB
import com.example.ui.components.PaymentCard
import com.example.ui.theme.*

@Composable
fun OrderSummaryScreen(
    orderAmount: Double = 16.48,
    onBackClick: () -> Unit,
    onNavigateToCustomize: () -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var paymentMethods by remember { mutableStateOf(SampleData.samplePaymentMethods) }
    var saveCardDetails by remember { mutableStateOf(true) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val taxes = 0.30
    val deliveryFees = 1.50
    val total = orderAmount + taxes + deliveryFees

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
                        Text(text = "Total price", style = TextStyle(color = FoodgoTextBody, fontSize = 12.sp))
                        Text(
                            text = "$${String.format("%.2f", total)}",
                            style = TextStyle(color = FoodgoInkDark, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                    }

                    Button(
                        onClick = { showSuccessDialog = true },
                        modifier = Modifier
                            .height(48.dp)
                            .testTag("pay_now_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = FoodgoInkDark),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Pay Now",
                            style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        )
                    }
                }
            }
        },
        containerColor = FoodgoPageBackground,
        modifier = modifier.testTag("order_summary_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
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

            Text(
                text = "Order summary",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoodgoInkDark
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Line items card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = FoodgoSurfaceWhite,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SummaryRow(label = "Order", value = "$${String.format("%.2f", orderAmount)}")
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(label = "Taxes", value = "$${String.format("%.2f", taxes)}")
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(label = "Delivery fees", value = "$${String.format("%.2f", deliveryFees)}")

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = FoodgoChipInactive
                    )

                    SummaryRow(
                        label = "Total:",
                        value = "$${String.format("%.2f", total)}",
                        isBold = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Estimated delivery time: 15 - 30mins",
                        style = TextStyle(color = FoodgoTextBody, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Payment Methods Section
            Text(
                text = "Payment methods",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = FoodgoInkDark
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            paymentMethods.forEach { method ->
                PaymentCard(
                    paymentMethod = method,
                    onSelect = {
                        paymentMethods = paymentMethods.map { m ->
                            m.copy(isSelected = (m.id == method.id))
                        }
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Ghost Button: + Add new card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, FoodgoRedPrimary, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        // Add new card flow
                        val newCard = PaymentMethod(
                            id = "p${paymentMethods.size + 1}",
                            brand = "American Express",
                            cardType = "Credit card",
                            cardNumber = "3782 **** **** 1005",
                            logoType = "visa",
                            isSelected = true
                        )
                        paymentMethods = paymentMethods.map { it.copy(isSelected = false) } + newCard
                    }
                    .testTag("add_new_card_button"),
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Card", tint = FoodgoRedPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add new card",
                        style = TextStyle(color = FoodgoRedPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Save card checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { saveCardDetails = !saveCardDetails }
            ) {
                Checkbox(
                    checked = saveCardDetails,
                    onCheckedChange = { saveCardDetails = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = FoodgoRedPrimary,
                        uncheckedColor = FoodgoTextBody
                    )
                )
                Text(
                    text = "Save card details for future payments",
                    style = TextStyle(color = FoodgoTextBody, fontSize = 13.sp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        if (showSuccessDialog) {
            SuccessDialog(
                onDismiss = {
                    showSuccessDialog = false
                    onNavigate("home")
                }
            )
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    isBold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = TextStyle(
                color = if (isBold) FoodgoInkDark else FoodgoTextBody,
                fontSize = if (isBold) 16.sp else 14.sp,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
            )
        )
        Text(
            text = value,
            style = TextStyle(
                color = FoodgoInkDark,
                fontSize = if (isBold) 16.sp else 14.sp,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.SemiBold
            )
        )
    }
}
