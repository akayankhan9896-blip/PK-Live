package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundEffects
import com.example.model.EntryEffectType
import com.example.model.Gender
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun EntryEffectOverlay(
    effectType: EntryEffectType,
    userName: String,
    userLevel: Int,
    vipLevel: Int,
    gender: Gender,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val progress = remember { Animatable(0f) }
    val fadeAlpha = remember { Animatable(1f) }

    LaunchedEffect(effectType) {
        if (effectType == EntryEffectType.LION) {
            SoundEffects.playLionRoar(context)
        } else {
            SoundEffects.playGiftSound(context, isHighTier = true)
        }

        // Animate from back to front over 4.5s then fade out
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 4500, easing = LinearEasing)
        )
        fadeAlpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
        onFinished()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "entry_effects")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .alpha(fadeAlpha.value)
            .background(Color.Black.copy(alpha = 0.65f))
    ) {
        when (effectType) {
            EntryEffectType.SKATEBOARD -> {
                NeonSkateboardCanvas(progress = progress.value)
            }
            EntryEffectType.LION -> {
                RoaringLionCanvas(progress = progress.value, pulse = pulseScale)
            }
            EntryEffectType.BIKE -> {
                GoldenBikeCanvas(progress = progress.value)
            }
            EntryEffectType.CAR -> {
                CyberSupercarCanvas(progress = progress.value)
            }
            EntryEffectType.TIGER, EntryEffectType.EAGLE, EntryEffectType.DRAGON -> {
                CosmicCreatureCanvas(effectType = effectType, progress = progress.value)
            }
        }

        // Skip button top right
        IconButton(
            onClick = onFinished,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
        }

        // Bottom Join Toast Banner (with Avatar, Name, VIP, Level badge bottom right of container)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 90.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF1E1B4B).copy(alpha = 0.95f),
                                Color(0xFF311042).copy(alpha = 0.95f)
                            )
                        )
                    )
                    .border(
                        1.5.dp,
                        Brush.horizontalGradient(listOf(NeonCyan, NeonPurple, NeonGold)),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User Avatar with glowing ring
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(2.dp, NeonCyan, CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(NeonPurple, NeonPink)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userName.take(2).uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = userName,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        GenderAgeBadge(gender = gender, age = 22)
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = NeonGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = " Arrived with ${effectType.displayName}!",
                            color = NeonGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Badges in bottom right corner of toast container
                Column(horizontalAlignment = Alignment.End) {
                    VipBadge(level = vipLevel)
                    Spacer(modifier = Modifier.height(4.dp))
                    LevelBadge(level = userLevel)
                }
            }
        }
    }
}

@Composable
private fun NeonSkateboardCanvas(progress: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Vanishing point for 3D road
        val vpX = w / 2f
        val vpY = h * 0.28f

        // 3D Perspective Road
        val roadBottomLeft = Offset(w * 0.05f, h * 0.85f)
        val roadBottomRight = Offset(w * 0.95f, h * 0.85f)
        val roadTopLeft = Offset(vpX - 40f, vpY)
        val roadTopRight = Offset(vpX + 40f, vpY)

        val roadPath = Path().apply {
            moveTo(roadTopLeft.x, roadTopLeft.y)
            lineTo(roadTopRight.x, roadTopRight.y)
            lineTo(roadBottomRight.x, roadBottomRight.y)
            lineTo(roadBottomLeft.x, roadBottomLeft.y)
            close()
        }

        drawPath(
            path = roadPath,
            brush = Brush.verticalGradient(
                listOf(Color(0xFF0F172A), Color(0xFF1E1B4B), Color(0xFF0F0F19)),
                startY = vpY,
                endY = h * 0.85f
            )
        )

        // Perspective grid lines
        for (i in 1..7) {
            val ratio = i / 8f
            val start = Offset(
                roadTopLeft.x + (roadTopRight.x - roadTopLeft.x) * ratio,
                vpY
            )
            val end = Offset(
                roadBottomLeft.x + (roadBottomRight.x - roadBottomLeft.x) * ratio,
                h * 0.85f
            )
            drawLine(
                color = NeonCyan.copy(alpha = 0.45f),
                start = start,
                end = end,
                strokeWidth = 2f
            )
        }

        // Horizontal moving speed lines on road
        val animOffset = (progress * 12f) % 1f
        for (j in 0..10) {
            val yFactor = ((j + animOffset) / 11f).coerceIn(0.01f, 1f)
            val lineY = vpY + (h * 0.85f - vpY) * (yFactor * yFactor)
            val leftX = roadTopLeft.x + (roadBottomLeft.x - roadTopLeft.x) * yFactor
            val rightX = roadTopRight.x + (roadBottomRight.x - roadTopRight.x) * yFactor
            drawLine(
                color = NeonCyan.copy(alpha = yFactor * 0.8f),
                start = Offset(leftX, lineY),
                end = Offset(rightX, lineY),
                strokeWidth = (1f + yFactor * 4f)
            )
        }

        // Starfield
        for (k in 0..35) {
            val starX = ((k * 89 + (progress * 50)) % w)
            val starY = ((k * 137) % (h * 0.6f))
            drawCircle(
                color = Color.White.copy(alpha = 0.7f),
                radius = 2.5f,
                center = Offset(starX, starY)
            )
        }

        // Skateboard moving from back (small, high) to front (large, low)
        // Cyclic motion loop
        val loopProgress = (progress * 1.5f) % 1f
        val currentScale = 0.2f + loopProgress * 1.6f
        val skateY = vpY + (h * 0.62f - vpY) * loopProgress
        val wobble = sin(loopProgress * 20f) * 35f * (1f - loopProgress * 0.3f)
        val skateX = vpX + wobble

        // Blue Neon light trail behind skateboard
        for (trail in 1..6) {
            val trailY = skateY - (trail * 25f * currentScale)
            val trailX = skateX - (wobble * 0.15f * trail)
            val trailAlpha = (1f - trail / 7f) * 0.6f
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(NeonCyan.copy(alpha = trailAlpha), Color.Transparent),
                    center = Offset(trailX, trailY),
                    radius = 45f * currentScale
                ),
                radius = 45f * currentScale,
                center = Offset(trailX, trailY)
            )
        }

        // Draw Skateboard Body
        val boardWidth = 140f * currentScale
        val boardHeight = 44f * currentScale

        rotate(degrees = wobble * 0.3f, pivot = Offset(skateX, skateY)) {
            // Neon Glow aura
            drawOval(
                brush = Brush.radialGradient(
                    listOf(NeonCyan, NeonPurple, Color.Transparent),
                    center = Offset(skateX, skateY),
                    radius = boardWidth * 0.8f
                ),
                topLeft = Offset(skateX - boardWidth * 0.8f, skateY - boardHeight),
                size = Size(boardWidth * 1.6f, boardHeight * 2f)
            )

            // Board deck
            drawRoundRect(
                brush = Brush.horizontalGradient(listOf(NeonCyan, NeonPurple, NeonPink)),
                topLeft = Offset(skateX - boardWidth / 2f, skateY - boardHeight / 2f),
                size = Size(boardWidth, boardHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(boardHeight / 2f)
            )

            // Neon Deck border
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(skateX - boardWidth / 2f, skateY - boardHeight / 2f),
                size = Size(boardWidth, boardHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(boardHeight / 2f),
                style = Stroke(width = 3f * currentScale)
            )

            // Glowing Wheels
            val wheelRadius = 14f * currentScale
            val wheelOffsets = listOf(
                Offset(skateX - boardWidth * 0.35f, skateY - boardHeight * 0.5f),
                Offset(skateX + boardWidth * 0.35f, skateY - boardHeight * 0.5f),
                Offset(skateX - boardWidth * 0.35f, skateY + boardHeight * 0.5f),
                Offset(skateX + boardWidth * 0.35f, skateY + boardHeight * 0.5f)
            )

            wheelOffsets.forEach { pos ->
                drawCircle(color = NeonCyan, radius = wheelRadius, center = pos)
                drawCircle(color = Color.White, radius = wheelRadius * 0.5f, center = pos)
            }
        }
    }
}

@Composable
private fun RoaringLionCanvas(progress: Float, pulse: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val centerX = w / 2f
        val centerY = h * 0.45f

        // Golden shockwaves
        for (i in 1..4) {
            val waveRadius = ((progress * 1.5f + i * 0.25f) % 1f) * (w * 0.7f)
            val alpha = (1f - (waveRadius / (w * 0.7f))).coerceIn(0f, 1f)
            drawCircle(
                color = NeonGold.copy(alpha = alpha * 0.6f),
                radius = waveRadius,
                center = Offset(centerX, centerY),
                style = Stroke(width = 6f)
            )
        }

        // Golden Lion Roar Silhouette / Crest
        val lionScale = (0.5f + progress * 0.7f) * pulse
        val headRadius = 90f * lionScale

        // Mane Glow
        drawCircle(
            brush = Brush.radialGradient(
                listOf(NeonGold, Color(0xFFD97706), Color.Transparent),
                center = Offset(centerX, centerY),
                radius = headRadius * 1.8f
            ),
            radius = headRadius * 1.8f,
            center = Offset(centerX, centerY)
        )

        // Mane Spikes
        for (angle in 0..360 step 20) {
            val rad = Math.toRadians(angle.toDouble())
            val spikeX = centerX + cos(rad).toFloat() * headRadius * 1.4f
            val spikeY = centerY + sin(rad).toFloat() * headRadius * 1.4f
            drawLine(
                brush = Brush.linearGradient(listOf(Color.White, NeonGold)),
                start = Offset(centerX, centerY),
                end = Offset(spikeX, spikeY),
                strokeWidth = 6f * lionScale
            )
        }

        // Lion Head Core
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFFFEF3C7), NeonGold, Color(0xFF78350F)),
                center = Offset(centerX, centerY),
                radius = headRadius
            ),
            radius = headRadius,
            center = Offset(centerX, centerY)
        )

        // Glowing Eyes
        val eyeOffsetY = centerY - headRadius * 0.2f
        drawCircle(color = Color.White, radius = 10f * lionScale, center = Offset(centerX - headRadius * 0.35f, eyeOffsetY))
        drawCircle(color = Color.White, radius = 10f * lionScale, center = Offset(centerX + headRadius * 0.35f, eyeOffsetY))

        // Crown on Lion
        val crownPath = Path().apply {
            moveTo(centerX - headRadius * 0.5f, centerY - headRadius * 0.9f)
            lineTo(centerX - headRadius * 0.6f, centerY - headRadius * 1.4f)
            lineTo(centerX - headRadius * 0.25f, centerY - headRadius * 1.1f)
            lineTo(centerX, centerY - headRadius * 1.55f)
            lineTo(centerX + headRadius * 0.25f, centerY - headRadius * 1.1f)
            lineTo(centerX + headRadius * 0.6f, centerY - headRadius * 1.4f)
            lineTo(centerX + headRadius * 0.5f, centerY - headRadius * 0.9f)
            close()
        }
        drawPath(crownPath, color = Color(0xFFFDE047))
    }
}

@Composable
private fun GoldenBikeCanvas(progress: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val bikeX = -w * 0.3f + progress * (w * 1.6f)
        val bikeY = h * 0.45f

        // Speed exhaust lines
        for (i in 1..6) {
            val lineY = bikeY + (i - 3) * 18f
            drawLine(
                brush = Brush.horizontalGradient(listOf(Color.Transparent, NeonGold, Color.White)),
                start = Offset(bikeX - 180f, lineY),
                end = Offset(bikeX - 20f, lineY),
                strokeWidth = 4f
            )
        }

        // Golden Bike Frame & Wheels
        val wheelRadius = 40f
        // Back wheel
        drawCircle(color = NeonGold, radius = wheelRadius, center = Offset(bikeX - 60f, bikeY + 20f), style = Stroke(6f))
        drawCircle(color = Color.White, radius = 14f, center = Offset(bikeX - 60f, bikeY + 20f))

        // Front wheel
        drawCircle(color = NeonGold, radius = wheelRadius, center = Offset(bikeX + 70f, bikeY + 20f), style = Stroke(6f))
        drawCircle(color = Color.White, radius = 14f, center = Offset(bikeX + 70f, bikeY + 20f))

        // Golden body frame
        val framePath = Path().apply {
            moveTo(bikeX - 60f, bikeY + 20f)
            lineTo(bikeX - 20f, bikeY - 25f)
            lineTo(bikeX + 30f, bikeY - 20f)
            lineTo(bikeX + 70f, bikeY + 20f)
            lineTo(bikeX + 20f, bikeY)
            close()
        }
        drawPath(framePath, brush = Brush.linearGradient(listOf(Color.White, NeonGold, Color(0xFFD97706))))
    }
}

@Composable
private fun CyberSupercarCanvas(progress: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val carScale = 0.3f + progress * 1.3f
        val carX = w / 2f
        val carY = h * 0.25f + (h * 0.35f) * progress

        // Headlight beams
        val beamPathLeft = Path().apply {
            moveTo(carX - 50f * carScale, carY)
            lineTo(carX - 160f * carScale, carY + 300f * carScale)
            lineTo(carX - 80f * carScale, carY + 300f * carScale)
            close()
        }
        drawPath(beamPathLeft, brush = Brush.verticalGradient(listOf(NeonCyan.copy(alpha = 0.7f), Color.Transparent)))

        val beamPathRight = Path().apply {
            moveTo(carX + 50f * carScale, carY)
            lineTo(carX + 80f * carScale, carY + 300f * carScale)
            lineTo(carX + 160f * carScale, carY + 300f * carScale)
            close()
        }
        drawPath(beamPathRight, brush = Brush.verticalGradient(listOf(NeonCyan.copy(alpha = 0.7f), Color.Transparent)))

        // Car body
        drawRoundRect(
            brush = Brush.linearGradient(listOf(NeonPurple, NeonPink, Color.White)),
            topLeft = Offset(carX - 90f * carScale, carY - 30f * carScale),
            size = Size(180f * carScale, 70f * carScale),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(20f * carScale)
        )
    }
}

@Composable
private fun CosmicCreatureCanvas(effectType: EntryEffectType, progress: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val angle = progress * 360f
        val cx = w / 2f + cos(Math.toRadians(angle.toDouble())).toFloat() * 120f
        val cy = h * 0.45f + sin(Math.toRadians(angle.toDouble() * 2)).toFloat() * 60f

        val auraColor = when (effectType) {
            EntryEffectType.DRAGON -> NeonPurple
            EntryEffectType.TIGER -> Color(0xFFF97316)
            else -> NeonCyan
        }

        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color.White, auraColor, Color.Transparent),
                center = Offset(cx, cy),
                radius = 160f
            ),
            radius = 160f,
            center = Offset(cx, cy)
        )
    }
}
