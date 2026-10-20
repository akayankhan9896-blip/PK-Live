package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Gender
import com.example.ui.theme.BadgeFemalePink
import com.example.ui.theme.BadgeHeartRed
import com.example.ui.theme.BadgeMaleBlue
import com.example.ui.theme.BadgeVipGold
import com.example.ui.theme.BadgeVipGreen

@Composable
fun GenderAgeBadge(gender: Gender, age: Int, modifier: Modifier = Modifier) {
    val bgColor = if (gender == Gender.MALE) BadgeMaleBlue else BadgeFemalePink
    val symbol = if (gender == Gender.MALE) "♂" else "♀"

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$symbol $age",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun VipBadge(level: Int, modifier: Modifier = Modifier) {
    val gradient = when {
        level >= 3 -> Brush.horizontalGradient(listOf(Color(0xFF8B5CF6), Color(0xFFD946EF)))
        level == 2 -> Brush.horizontalGradient(listOf(Color(0xFFF59E0B), Color(0xFFEAB308)))
        else -> Brush.horizontalGradient(listOf(BadgeVipGreen, Color(0xFF34D399)))
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(gradient)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "VIP",
            tint = Color.White,
            modifier = Modifier.size(10.dp)
        )
        Text(
            text = " VIP$level",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun LevelBadge(level: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFF10B981), Color(0xFF059669))))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Bolt,
            contentDescription = "Level",
            tint = Color.White,
            modifier = Modifier.size(10.dp)
        )
        Text(
            text = "$level",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HeartBadge(level: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(BadgeHeartRed)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Heart",
            tint = Color.White,
            modifier = Modifier.size(10.dp)
        )
        Text(
            text = " $level",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun OfficialBadge(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFF7C3AED), Color(0xFFA855F7))))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Verified,
            contentDescription = "Official",
            tint = Color(0xFFFBBF24),
            modifier = Modifier.size(11.dp)
        )
        Text(
            text = " Official",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PkBadge(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFFEF4444), Color(0xFF3B82F6))))
            .padding(horizontal = 5.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "PK",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun FireCountBadge(count: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black.copy(alpha = 0.55f))
            .padding(horizontal = 5.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocalFireDepartment,
            contentDescription = "Fire",
            tint = Color(0xFFF97316),
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = "$count",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
