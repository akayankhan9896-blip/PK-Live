package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundEffects
import com.example.model.GiftItem
import com.example.ui.theme.BgCardDark
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple
import kotlin.math.cos
import kotlin.math.sin

val AvailableGifts = listOf(
    GiftItem("1", "Rose", 50, "🌹", "Romantic red rose"),
    GiftItem("2", "Heart", 100, "💖", "Sparkling heart"),
    GiftItem("3", "Beer", 200, "🍺", "Party toast cheers"),
    GiftItem("4", "Cake", 500, "🎂", "Sweet celebration"),
    GiftItem("5", "Ring", 1000, "💍", "Diamond proposal ring"),
    GiftItem("6", "Bike", 3000, "🏍️", "Golden chopper speed"),
    GiftItem("7", "Car", 10000, "🏎️", "Cyber sport supercar"),
    GiftItem("8", "Lion", 30000, "🦁", "Golden king of beasts roar"),
    GiftItem("9", "Universe", 80000, "🌌", "Cosmic supernova explosion")
)

@Composable
fun GiftDrawer(
    userCoins: Long,
    onGiftSelected: (GiftItem) -> Unit,
    onRechargeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedGift by remember { mutableStateOf(AvailableGifts[0]) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = BgCardDark,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Send Gifts",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                // User coins wallet
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF262343))
                        .clickable(onClick = onRechargeClicked)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.MonetizationOn,
                        contentDescription = "Coins",
                        tint = NeonGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$userCoins",
                        color = NeonGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(NeonPink),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Recharge",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3x3 Gift Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.height(260.dp)
            ) {
                items(AvailableGifts) { gift ->
                    val isSelected = gift.id == selectedGift.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) Color(0xFF332958) else Color(0xFF1E1C38))
                            .border(
                                width = if (isSelected) 2.dp else 0.5.dp,
                                color = if (isSelected) NeonPink else Color(0xFF2D2A54),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable { selectedGift = gift }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = gift.iconEmoji,
                                fontSize = 32.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = gift.name,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MonetizationOn,
                                    contentDescription = null,
                                    tint = NeonGold,
                                    modifier = Modifier.size(11.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "${gift.coins}",
                                    color = NeonGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = selectedGift.description,
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )
                    Text(
                        text = "Cost: ${selectedGift.coins} Coins",
                        color = NeonGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Button(
                    onClick = { onGiftSelected(selectedGift) },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "SEND",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun GiftCelebrationOverlay(
    gift: GiftItem,
    senderName: String,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val progress = remember { Animatable(0f) }
    val alphaAnim = remember { Animatable(1f) }

    LaunchedEffect(gift) {
        SoundEffects.playGiftSound(context, isHighTier = gift.coins >= 3000)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2600, easing = FastOutSlowInEasing)
        )
        alphaAnim.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 400)
        )
        onFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .alpha(alphaAnim.value)
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        // Particle explosions
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cx = size.width / 2f
            val cy = size.height / 2f
            val p = progress.value

            // Burst rays
            for (i in 0 until 16) {
                val angle = Math.toRadians((i * (360f / 16)).toDouble())
                val dist = p * (size.width * 0.45f)
                val sparkX = cx + cos(angle).toFloat() * dist
                val sparkY = cy + sin(angle).toFloat() * dist

                drawCircle(
                    color = if (i % 2 == 0) NeonGold else NeonPink,
                    radius = (1f - p) * 12f,
                    center = Offset(sparkX, sparkY)
                )
            }

            // Shockwave ring
            drawCircle(
                color = NeonPurple.copy(alpha = (1f - p).coerceIn(0f, 1f)),
                radius = p * size.width * 0.5f,
                center = Offset(cx, cy),
                style = Stroke(width = 8f * (1f - p))
            )
        }

        // Center bouncing gift emoji + glow
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(0.8f + progress.value * 0.4f)
        ) {
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(NeonGold.copy(alpha = 0.6f), Color.Transparent)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gift.iconEmoji,
                    fontSize = 72.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Toast banner
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF7C3AED), Color(0xFFDB2777))
                        )
                    )
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = NeonGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "$senderName sent ${gift.name}!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
