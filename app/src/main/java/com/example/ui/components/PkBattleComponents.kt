package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audio.SoundEffects
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import kotlin.random.Random

@Composable
fun PkScoreBar(
    hostScore: Long,
    opponentScore: Long,
    timeLeftSeconds: Int,
    hostName: String,
    opponentName: String,
    modifier: Modifier = Modifier
) {
    val total = (hostScore + opponentScore).coerceAtLeast(1L).toFloat()
    val hostRatioRaw = (hostScore.toFloat() / total).coerceIn(0.15f, 0.85f)
    val animatedRatio by animateFloatAsState(
        targetValue = hostRatioRaw,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "pk_ratio"
    )

    val minutes = timeLeftSeconds / 60
    val seconds = timeLeftSeconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        // Names and Scores Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(NeonBlue)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$hostName : $hostScore",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            // PK Timer Pill in Center
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black.copy(alpha = 0.7f))
                    .border(1.dp, Color(0xFFF97316), RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "PK",
                        tint = Color(0xFFF97316),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = timeFormatted,
                        color = Color(0xFFF97316),
                        fontWeight = FontWeight.Black,
                        fontSize = 11.sp
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$opponentScore : $opponentName",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(NeonPink)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Split Battle Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(18.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(Color(0xFF1E1C38))
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                // Host Blue Bar
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(animatedRatio)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF2563EB), Color(0xFF06B6D4))
                            )
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "${(animatedRatio * 100).toInt()}%",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

                // Opponent Red/Pink Bar
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f - animatedRatio)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFE11D48), Color(0xFFEC4899))
                            )
                        ),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "${((1f - animatedRatio) * 100).toInt()}%",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                }
            }

            // PK Emblem in middle
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .border(1.5.dp, Color(0xFFFBBF24), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "VS",
                    color = Color(0xFFFBBF24),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

@Composable
fun PkWinnerOverlay(
    winnerName: String,
    hostScore: Long,
    opponentScore: Long,
    onRematch: () -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val scaleAnim = remember { Animatable(0.6f) }

    LaunchedEffect(Unit) {
        SoundEffects.playVictoryFanfare(context)
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
        )
    }

    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .scale(scaleAnim.value)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF2A1B4E), Color(0xFF140F2D))
                    )
                )
                .border(2.dp, NeonGold, RoundedCornerShape(24.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Trophy Icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(listOf(NeonGold, Color(0xFFD97706)))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Trophy",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "VICTORY!",
                    color = NeonGold,
                    fontWeight = FontWeight.Black,
                    fontSize = 26.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$winnerName WINS THE BATTLE!",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Final Score: $hostScore - $opponentScore",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onClose,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Close", color = Color.White)
                    }

                    Button(
                        onClick = onRematch,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Rematch", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
