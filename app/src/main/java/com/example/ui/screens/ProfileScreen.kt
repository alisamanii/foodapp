package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.FoodgoBottomNavWithFAB
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onNavigateToChat: () -> Unit,
    onNavigateToCustomize: () -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditing by remember { mutableStateOf(false) }
    var showLogoutSheet by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("Sophia Patel") }
    var email by remember { mutableStateOf("sophiapatel@gmail.com") }
    var address by remember { mutableStateOf("123 Main St Apartment 4A,New York, NY") }
    var password by remember { mutableStateOf("12345678") }

    Scaffold(
        bottomBar = {
            FoodgoBottomNavWithFAB(
                currentRoute = "profile",
                onNavigate = onNavigate,
                onFabClick = onNavigateToCustomize
            )
        },
        containerColor = FoodgoSurfaceWhite,
        modifier = modifier.testTag("profile_screen")
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Red Gradient Header Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(FoodgoRedPrimary, FoodgoRedGradientEnd)
                            )
                        )
                ) {
                    // Header top controls
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }

                        IconButton(onClick = onNavigateToChat) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings / Support Chat",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Main Form Content Sheet
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    // Avatar overlapping header (-40dp offset)
                    Box(
                        modifier = Modifier
                            .offset(y = (-40).dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(90.dp)
                                .shadow(8.dp, RoundedCornerShape(20.dp))
                                .border(4.dp, Color.White, RoundedCornerShape(20.dp))
                                .clip(RoundedCornerShape(20.dp)),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_user_avatar_1785072958204),
                                contentDescription = "Profile Photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(-20.dp))

                    // Outlined Form Fields
                    ProfileOutlinedField(
                        label = "Name",
                        value = name,
                        onValueChange = { name = it },
                        readOnly = !isEditing
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    ProfileOutlinedField(
                        label = "Email",
                        value = email,
                        onValueChange = { email = it },
                        readOnly = !isEditing
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    ProfileOutlinedField(
                        label = "Delivery address",
                        value = address,
                        onValueChange = { address = it },
                        readOnly = !isEditing
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    ProfileOutlinedField(
                        label = "Password",
                        value = password,
                        onValueChange = { password = it },
                        readOnly = !isEditing,
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    HorizontalDivider(color = FoodgoChipInactive)

                    Spacer(modifier = Modifier.height(12.dp))

                    // List Rows: Payment Details & Order History
                    ProfileRowItem(
                        label = "Payment Details",
                        onClick = { onNavigate("order_summary") }
                    )

                    ProfileRowItem(
                        label = "Order history",
                        onClick = { onNavigate("orders") }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons Row: Edit Profile + Log out
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { isEditing = !isEditing },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("edit_profile_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = FoodgoInkDark),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isEditing) "Save" else "Edit Profile",
                                style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            )
                        }

                        OutlinedButton(
                            onClick = { showLogoutSheet = true },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("logout_button"),
                            border = androidx.compose.foundation.BorderStroke(1.dp, FoodgoRedPrimary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = FoodgoRedPrimary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Log out",
                                style = TextStyle(color = FoodgoRedPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            if (showLogoutSheet) {
                AlertDialog(
                    onDismissRequest = { showLogoutSheet = false },
                    title = { Text("Log out", fontWeight = FontWeight.Bold) },
                    text = { Text("Are you sure you want to log out of Foodgo?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutSheet = false
                            onNavigate("splash")
                        }) {
                            Text("Log out", color = FoodgoRedPrimary, fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutSheet = false }) {
                            Text("Cancel", color = FoodgoTextBody)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun ProfileOutlinedField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = FoodgoTextBody, fontSize = 12.sp) },
        readOnly = readOnly,
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        trailingIcon = if (isPassword) {
            { Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = FoodgoTextBody) }
        } else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FoodgoRedPrimary,
            unfocusedBorderColor = FoodgoBorderLight,
            focusedLabelColor = FoodgoRedPrimary,
            unfocusedLabelColor = FoodgoTextBody
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ProfileRowItem(
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = TextStyle(color = FoodgoInkDark, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = FoodgoTextBody
        )
    }
}
