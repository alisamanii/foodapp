package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Menu
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
import com.example.model.ChatMessage
import com.example.model.SampleData
import com.example.ui.components.ChatBubble
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SupportChatScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var messages by remember { mutableStateOf(SampleData.initialChatMessages) }
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            Surface(
                color = FoodgoSurfaceWhite,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = FoodgoInkDark)
                    }

                    Text(
                        text = "Foodgo Support",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, color = FoodgoInkDark)
                    )

                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = FoodgoInkDark)
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = FoodgoSurfaceWhite,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 44.dp, max = 100.dp),
                        shape = RoundedCornerShape(22.dp),
                        color = FoodgoChipInactive
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            TextField(
                                value = inputText,
                                onValueChange = { inputText = it },
                                placeholder = { Text("Type here...", color = FoodgoTextBody, fontSize = 14.sp) },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                singleLine = false,
                                maxLines = 3,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("chat_input_field")
                            )
                        }
                    }

                    // Red Circular Send Button
                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                val userMsg = ChatMessage(
                                    id = "msg_${System.currentTimeMillis()}",
                                    text = inputText.trim(),
                                    isUser = true,
                                    timestamp = "Just now"
                                )
                                messages = messages + userMsg
                                val currentText = inputText
                                inputText = ""

                                // Bot simulated response
                                coroutineScope.launch {
                                    delay(1000)
                                    val botReply = ChatMessage(
                                        id = "bot_${System.currentTimeMillis()}",
                                        text = "Thank you! Our support team is looking into '${currentText.take(20)}...'. We'll update you shortly!",
                                        isUser = false,
                                        timestamp = "Just now"
                                    )
                                    messages = messages + botReply
                                }
                            }
                        },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(FoodgoRedPrimary)
                            .testTag("chat_send_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        },
        containerColor = FoodgoPageBackground,
        modifier = modifier.testTag("support_chat_screen")
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatBubble(
                    message = msg.text,
                    isUser = msg.isUser,
                    timestamp = msg.timestamp
                )
            }
        }
    }
}
