package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Broadcaster
import com.example.model.EntryEffectType
import com.example.model.GiftItem
import com.example.model.LiveChatMessage
import com.example.model.UserProfile
import com.example.model.VoiceSeat
import com.example.ui.components.EntryEffectOverlay
import com.example.ui.components.GiftCelebrationOverlay
import com.example.ui.components.GiftDrawer
import com.example.ui.components.PkBadge
import com.example.ui.components.PkScoreBar
import com.example.ui.components.PkWinnerOverlay
import com.example.ui.components.RechargeDialog
import com.example.ui.components.VipBadge
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

data class FloatingHeart(
    val id: Long,
    val xOffset: Float,
    val color: Color
)

@Composable
fun LiveRoomScreen(
    broadcaster: Broadcaster,
    userProfile: UserProfile,
    isPkActive: Boolean,
    hostPkScore: Long,
    opponentPkScore: Long,
    pkTimeLeftSeconds: Int,
    pkWinner: String?,
    isVoiceMuted: Boolean,
    voiceSeats: List<VoiceSeat>,
    chatMessages: List<LiveChatMessage>,
    activeEntryEffect: EntryEffectType?,
    activeGiftCelebration: Pair<GiftItem, String>?,
    showRechargeDialog: Boolean,
    showGiftDrawer: Boolean,
    onSendMessage: (String) -> Unit,
    onSendGift: (GiftItem) -> Unit,
    onTogglePk: () -> Unit,
    onRematchPk: () -> Unit,
    onToggleVoiceSeat: (Int) -> Unit,
    onToggleMute: () -> Unit,
    onTriggerEntryEffect: (EntryEffectType) -> Unit,
    onClearEntryEffect: () -> Unit,
    onClearGiftCelebration: () -> Unit,
    onSetRechargeDialog: (Boolean) -> Unit,
    onSetGiftDrawer: (Boolean) -> Unit,
    onRechargeAddCoins: (Int) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var inputComment by remember { mutableStateOf("") }
    val floatingHearts = remember { mutableStateListOf<FloatingHeart>() }
    val coroutineScope = rememberCoroutineScope()
    val chatListState = rememberLazyListState()
    var showEffectPicker by remember { mutableStateOf(false) }

    // Auto-scroll chat to bottom
    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            chatListState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    // Trigger initial entry effect on room join
    LaunchedEffect(Unit) {
        onTriggerEntryEffect(EntryEffectType.SKATEBOARD)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0914))
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { offset ->
                        val heart = FloatingHeart(
                            id = System.currentTimeMillis(),
                            xOffset = (offset.x / 10f) % 60f,
                            color = listOf(NeonPink, NeonGold, NeonCyan, Color.Red).random()
                        )
                        floatingHearts.add(heart)
                        coroutineScope.launch {
                            delay(1500)
                            floatingHearts.remove(heart)
                        }
                    }
                )
            }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Room Top Header
            RoomHeader(
                broadcaster = broadcaster,
                onClose = onClose,
                onRecharge = { onSetRechargeDialog(true) }
            )

            // PK Battle Header Bar (if PK is active)
            if (isPkActive) {
                PkScoreBar(
                    hostScore = hostPkScore,
                    opponentScore = opponentPkScore,
                    timeLeftSeconds = pkTimeLeftSeconds,
                    hostName = broadcaster.name,
                    opponentName = broadcaster.pkOpponentName ?: "Opponent"
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Main Video Stream Area (Split 1v1 screen during PK Battle)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (isPkActive) {
                    // Split 1v1 PK Match View
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Left: Host Stream
                        BroadcasterStreamPane(
                            name = broadcaster.name,
                            isHost = true,
                            score = hostPkScore,
                            gender = broadcaster.gender,
                            tag = "BLUE TEAM",
                            modifier = Modifier.weight(1f)
                        )

                        // Right: Opponent Stream
                        BroadcasterStreamPane(
                            name = broadcaster.pkOpponentName ?: "SHAHZAIB",
                            isHost = false,
                            score = opponentPkScore,
                            gender = broadcaster.gender,
                            tag = "RED TEAM",
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else {
                    // Single Broadcaster View
                    BroadcasterStreamPane(
                        name = broadcaster.name,
                        isHost = true,
                        score = hostPkScore,
                        gender = broadcaster.gender,
                        tag = "LIVE HOST",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 6.dp)
                    )
                }

                // 8 Voice Seats Grid (Multi-guest Audio Seats Overlay)
                VoiceSeatsOverlay(
                    seats = voiceSeats,
                    onSeatClick = onToggleVoiceSeat,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                )
            }

            // Live Chat Messages (Floating bottom left)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 12.dp)
            ) {
                LazyColumn(
                    state = chatListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(chatMessages) { message ->
                        ChatMessageItem(message = message)
                    }
                }
            }

            // Bottom Action Bar (Chat input, Mute, PK toggle, Effects, Gift Box)
            RoomBottomBar(
                inputComment = inputComment,
                onInputChange = { inputComment = it },
                onSend = {
                    if (inputComment.isNotBlank()) {
                        onSendMessage(inputComment)
                        inputComment = ""
                    }
                },
                isVoiceMuted = isVoiceMuted,
                onToggleMute = onToggleMute,
                onTogglePk = onTogglePk,
                onOpenGiftDrawer = { onSetGiftDrawer(true) },
                onOpenEffectPicker = { showEffectPicker = true }
            )
        }

        // Floating Hearts Animation overlay
        floatingHearts.forEach { heart ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 120.dp, end = (40 + heart.xOffset).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = heart.color,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // Fullscreen Entry Effect Overlay (3D Skateboard, Lion Roar, Golden Bike, etc.)
        activeEntryEffect?.let { effect ->
            EntryEffectOverlay(
                effectType = effect,
                userName = userProfile.username,
                userLevel = userProfile.userLevel,
                vipLevel = userProfile.vipLevel,
                gender = userProfile.gender,
                onFinished = onClearEntryEffect
            )
        }

        // Fullscreen Gift Celebration Overlay
        activeGiftCelebration?.let { (gift, sender) ->
            GiftCelebrationOverlay(
                gift = gift,
                senderName = sender,
                onFinished = onClearGiftCelebration
            )
        }

        // Winner overlay when PK timer finishes
        pkWinner?.let { winner ->
            PkWinnerOverlay(
                winnerName = winner,
                hostScore = hostPkScore,
                opponentScore = opponentPkScore,
                onRematch = onRematchPk,
                onClose = onTogglePk
            )
        }

        // Gift Drawer Modal
        if (showGiftDrawer) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { onSetGiftDrawer(false) },
                contentAlignment = Alignment.BottomCenter
            ) {
                GiftDrawer(
                    userCoins = userProfile.xcoins,
                    onGiftSelected = onSendGift,
                    onRechargeClicked = {
                        onSetGiftDrawer(false)
                        onSetRechargeDialog(true)
                    },
                    modifier = Modifier.clickable(enabled = false) {}
                )
            }
        }

        // Coin Recharge Dialog
        if (showRechargeDialog) {
            RechargeDialog(
                currentCoins = userProfile.xcoins,
                onRechargeSuccess = onRechargeAddCoins,
                onDismiss = { onSetRechargeDialog(false) }
            )
        }

        // Effect Picker Quick Sheet
        if (showEffectPicker) {
            EffectPickerSheet(
                onSelectEffect = { effect ->
                    onTriggerEntryEffect(effect)
                    showEffectPicker = false
                },
                onDismiss = { showEffectPicker = false }
            )
        }
    }
}

@Composable
private fun RoomHeader(
    broadcaster: Broadcaster,
    onClose: () -> Unit,
    onRecharge: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Broadcaster Profile Pill
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Black.copy(alpha = 0.55f))
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(listOf(NeonPurple, NeonPink))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = broadcaster.name.take(2).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = broadcaster.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        tint = Color(0xFFF97316),
                        modifier = Modifier.size(11.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${broadcaster.viewerCount}",
                        color = Color.LightGray,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Follow Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(NeonPink)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+ Follow",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }

        // Right side controls: Close button
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Leave Room",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun BroadcasterStreamPane(
    name: String,
    isHost: Boolean,
    score: Long,
    gender: com.example.model.Gender,
    tag: String,
    modifier: Modifier = Modifier
) {
    val themeColor = if (isHost) NeonBlue else NeonPink

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF1E1B4B),
                        Color(0xFF0F0F19)
                    )
                )
            )
            .border(1.5.dp, themeColor.copy(alpha = 0.6f), RoundedCornerShape(18.dp))
    ) {
        // Stream Background / Camera Simulation
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .border(2.dp, themeColor, CircleShape)
                    .background(
                        Brush.radialGradient(listOf(themeColor, Color(0xFF140F2D)))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.take(2).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            // Audio Waveform pulse simulation
            Row(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..5) {
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height((8 + (i * 3 % 12)).dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(themeColor)
                    )
                }
            }
        }

        // Top team tag
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(themeColor.copy(alpha = 0.8f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = tag,
                color = Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Black
            )
        }

        // Score Badge bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = "$score pts",
                color = NeonGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun VoiceSeatsOverlay(
    seats: List<VoiceSeat>,
    onSeatClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black.copy(alpha = 0.6f))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items(seats) { seat ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onSeatClick(seat.seatIndex) }
                    .padding(horizontal = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(
                            if (seat.userName != null) NeonPurple else Color(0xFF262142)
                        )
                        .border(
                            width = 1.dp,
                            color = if (seat.isSpeaking) NeonCyan else Color.Transparent,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (seat.userName != null) {
                        Text(
                            text = seat.userName.take(1).uppercase(),
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Take Seat",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = seat.userName ?: "Mic ${seat.seatIndex + 1}",
                    color = Color.LightGray,
                    fontSize = 9.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun ChatMessageItem(message: LiveChatMessage) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (message.isGiftNotice) Color(0xFF4C1D95).copy(alpha = 0.85f)
                else Color.Black.copy(alpha = 0.55f)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!message.isSystem && message.vipLevel > 0) {
            VipBadge(level = message.vipLevel)
            Spacer(modifier = Modifier.width(4.dp))
        }

        Text(
            text = "${message.senderName}: ",
            color = if (message.isGiftNotice) NeonGold else Color(0xFF67E8F9),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )

        Text(
            text = message.message,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun RoomBottomBar(
    inputComment: String,
    onInputChange: (String) -> Unit,
    onSend: () -> Unit,
    isVoiceMuted: Boolean,
    onToggleMute: () -> Unit,
    onTogglePk: () -> Unit,
    onOpenGiftDrawer: () -> Unit,
    onOpenEffectPicker: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Comment Input field
        OutlinedTextField(
            value = inputComment,
            onValueChange = onInputChange,
            placeholder = { Text("Say something...", color = Color.Gray, fontSize = 12.sp) },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .height(44.dp),
            shape = RoundedCornerShape(22.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonPurple,
                unfocusedBorderColor = Color(0xFF2E2B4E),
                focusedContainerColor = Color(0xFF1E1B38).copy(alpha = 0.8f),
                unfocusedContainerColor = Color(0xFF1E1B38).copy(alpha = 0.8f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        // Send Comment button
        IconButton(
            onClick = onSend,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(NeonPurple)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }

        // Voice Mute Button
        IconButton(
            onClick = onToggleMute,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (isVoiceMuted) Color(0xFFDC2626) else Color(0xFF1E1B38))
        ) {
            Icon(
                imageVector = if (isVoiceMuted) Icons.Default.MicOff else Icons.Default.Mic,
                contentDescription = "Mic",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        // PK Match Button
        IconButton(
            onClick = onTogglePk,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(listOf(Color(0xFFEF4444), Color(0xFF3B82F6)))
                )
        ) {
            Text(
                text = "PK",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp
            )
        }

        // Entry Effect Trigger button
        IconButton(
            onClick = onOpenEffectPicker,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF8B5CF6))
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Entry Effects",
                tint = NeonGold,
                modifier = Modifier.size(20.dp)
            )
        }

        // Gifting Button (Pink Gift Box)
        IconButton(
            onClick = onOpenGiftDrawer,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(listOf(NeonPink, Color(0xFFBE185D)))
                )
        ) {
            Icon(
                imageVector = Icons.Default.CardGiftcard,
                contentDescription = "Send Gift",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun EffectPickerSheet(
    onSelectEffect: (EntryEffectType) -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color(0xFF161329))
                .clickable(enabled = false) {}
                .padding(16.dp)
        ) {
            Text(
                text = "Test Room Entry Effects",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(EntryEffectType.entries) { effect ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF221F3D))
                            .clickable { onSelectEffect(effect) }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = when (effect) {
                                EntryEffectType.SKATEBOARD -> "🛹"
                                EntryEffectType.LION -> "🦁"
                                EntryEffectType.BIKE -> "🏍️"
                                EntryEffectType.CAR -> "🏎️"
                                EntryEffectType.TIGER -> "🐯"
                                EntryEffectType.EAGLE -> "🦅"
                                EntryEffectType.DRAGON -> "🐉"
                            },
                            fontSize = 32.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = effect.displayName,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${effect.coinPrice} Coins",
                            color = NeonGold,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}
